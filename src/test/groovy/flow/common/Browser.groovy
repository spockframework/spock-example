package flow.common

import flow.acquisition.CartPage
import flow.acquisition.DeliveryPage
import flow.acquisition.FormWrapper
import flow.acquisition.HomePage
import flow.acquisition.IForm
import flow.acquisition.PayMonthlyPhonesPage
import flow.acquisition.PhoneDetailsPage
import flow.addline.AddExtrasPage
import flow.addline.AddPayMonthlyPhonesPage
import flow.addline.CheckoutPage
import flow.addline.CheckoutPage.JsonPayload
import flow.addline.InitializePage
import flow.addline.PaymentFrame
import flow.addline.PaymentPage
import flow.addline.PersonalizedHomePage
import flow.addline.WebSecurePage
import groovy.json.JsonSlurper
import groovyx.net.http.ChainedHttpConfig
import org.jsoup.Jsoup
import org.jsoup.nodes.FormElement

import static groovyx.net.http.ContentTypes.JSON
import groovy.text.SimpleTemplateEngine
import groovyx.net.http.ApacheHttpBuilder
import groovyx.net.http.FromServer
import groovyx.net.http.HttpBuilder
import groovyx.net.http.NativeHandlers
import org.apache.http.impl.client.HttpClientBuilder
import org.jsoup.nodes.Document

import static SslUtils2.ignoreSslIssues

/**
 * Browses page by use {@link HttpBuilder}
 */
class Browser {
    // once get big enough, please consider to extract it to something like Pages class
    private static final pathsMap = [
            (HomePage.class)               : '/',
            (PayMonthlyPhonesPage.class)   : '/mobile-phones/pay-monthly/gallery?search=:best-sellers',
            (PhoneDetailsPage.class)       : '/$categoryCode/$seoBundleType/$prettyId/details',
            (CartPage.class)               : '/cart',
            (DeliveryPage.class)           : '/delivery',
            (PersonalizedHomePage.class)   : '/auth/my-shop',
            (AddPayMonthlyPhonesPage.class): '/auth/mobile-phones/add-pay-monthly/gallery',
            (AddExtrasPage.class)          : '/addExtras',
            (CheckoutPage.class)           : '/addCheckout',
            (InitializePage.class)         : '/addCheckout/payment',
            (PaymentPage.class)            : '/addCheckout/payment',
            (WebSecurePage.class)          : '/addCheckout/tcc3ds'
    ]

    private static final String BASE_URL_SECURE = 'https://ee.local:9002'
    private static final String BASE_URL_TCC = 'http://127.0.0.1:8080'


    /**
     * Returns path which corresponds to specified {@code pageClass}
     * @param pageClass
     * @return
     */
    static String getPagePath(Class pageClass) {
        return pathsMap.get(pageClass)
    }

    HttpBuilder httpBuilder

    /**
     * Browser constructor with ssl connection choice parameter
     * @param secure
     */
    Browser() {
        this.httpBuilder = ApacheHttpBuilder.configure {

            request.uri = BASE_URL_SECURE

            // ignore any SSL certificate-related issues
            ignoreSslIssues execution
            client.clientCustomizer { HttpClientBuilder builder ->
                // The server responses with '303 See Other' status instead of common '302 Moved Temporarily' on form submission.
                // This causes the client to automatically issue GET request to new location received in response
                // setting below disable automatic redirect handling
                // see org.apache.http.impl.client.DefaultRedirectStrategy for more details
                builder.disableRedirectHandling()
            }
        }
    }

    /**
     * Opens a page of specified {@code pageClass}.
     * @return The page object that symbolizes the opened page.
     */
    def <T> T open(Class<T> pageClass, Map vars = [:]) {
        assert pathsMap.containsKey(pageClass)

        def path = getPagePath(pageClass)
        def template = new SimpleTemplateEngine().createTemplate(path).make(vars)
        def document = get(template.toString())
        return pageClass.newInstance(document) as T
    }

    /**
     * Submits data by action path taken from {@code formWrapper}
     * @param formWrapper
     * @return page class which url corresponds to new location received in response
     */
    Class submit(IForm form) {
        def redirectHandler = { FromServer fs ->
            String location = FromServer.Header.find(
                    fs.headers, 'Location'
            )?.value
            if (fs.getStatusCode() != 303) {
                throw new RuntimeException("Received [${fs.getStatusCode()}] status code instead of 303 in response of [${fs.getUri()}]")
            }
            return location
        }
        def location = postForm(form.getAction(), form.getFormData(), redirectHandler)
        return pathsMap.find { it -> (it.value == location) }.key
    }

    private String postForm(String path, Map form, Closure successHandler) {
        return httpBuilder.post {
            request.uri.path = path
            request.body = form
            request.contentType = 'application/x-www-form-urlencoded'
            request.encoder 'application/x-www-form-urlencoded', NativeHandlers.Encoders.&form
            response.success successHandler
        }
    }

    private Document get(String path) {
        return httpBuilder.get() {
            request.uri = path
            response.failure { FromServer fs ->
                throw new RuntimeException("Couldn't reach [${fs.getUri()}]. Received [${fs.getStatusCode()}] status.")
            }
        } as Document
    }

    /**
     * Do request with hardcoded credentials of test user
     * set sessionID to class field
     * @return session id as String
     */
    def startSession(User user) {
        return httpBuilder.get {
            request.uri.path = '/authorize'

            request.uri.query = [
                    'code': '123',
                    'state':'123',
                    'accountNumber': user.accountNumber,
                    'msisdn': user.msisdn]
            response.success { FromServer fs ->
                if (fs.getStatusCode() != 303) {
                    throw new RuntimeException("Received [${fs.getStatusCode()}] status code instead of 303 in response of [${fs.getUri()}]")
                }
            }
            response.failure { FromServer fs ->
                throw new RuntimeException("Couldn't reach [${fs.getUri()}]. Received [${fs.getStatusCode()}] status.")
            }
        }
    }

   //ajaxForm
    //ajaxGet

    def ajaxJson(String path, CSRFTokenHolder tokenHolder, JsonPayload payload) {
        return httpBuilder.post {
            request.uri.path = path
            request.contentType = JSON[0]
            def token = tokenHolder.getToken()
            Map<String, CharSequence> headers = new HashMap<>(request.getHeaders())
            headers.put('X-Requested-With','XMLHttpRequest')
            headers.put(token.getName(), token.getValue())
            request.setHeaders(headers)
            request.body = payload
            response.success { FromServer fs ->
                if (fs.getStatusCode() != 200) {
                    throw new RuntimeException("Received [${fs.getStatusCode()}] status code instead of 200 in response of [${fs.getUri()}]")
                }
                return true
            }
            response.failure { FromServer fs ->
                throw new RuntimeException("Couldn't reach [${fs.getUri()}]. Received [${fs.getStatusCode()}] status.")
            }
        }
    }

    /**
     * Method emulates sending pin from page to server
     * @param tokenHolder
     * @return
     */
    def doValidatePinRequest(CSRFTokenHolder tokenHolder) {
        return httpBuilder.post {
            request.uri.path = '/upgradeCheckout/validatePin'
            request.contentType = 'application/x-www-form-urlencoded'
            def token = tokenHolder.getToken()
            Map<String, CharSequence> headers = new HashMap<>(request.getHeaders())
            headers.put('X-Requested-With','XMLHttpRequest')
            headers.put(token.getName(), token.getValue())
            request.setHeaders(headers)
            request.body = ['pin' : '999999']
            request.encoder 'application/x-www-form-urlencoded', NativeHandlers.Encoders.&form
            response.parser('application/json') { ChainedHttpConfig cfg, FromServer fs ->
                def jsonSlurper = new JsonSlurper()
                def json = jsonSlurper.parseText(fs.inputStream.text)
                return json
            }
            response.success { FromServer fs ->
                if (fs.getStatusCode() != 200) {
                    throw new RuntimeException("Received [${fs.getStatusCode()}] status code instead of 200 in response of [${fs.getUri()}]")
                }
                return true
            }
            response.failure { FromServer fs ->
                throw new RuntimeException("Couldn't reach [${fs.getUri()}]. Received [${fs.getStatusCode()}] status.")
            }
        }
    }

    /**
     * Method do request from checkout page to update customer checkout details
     * @param tokenHolder
     * @param user - logged in user data
     * @return
     */
    def doPersonalInfoRequest(CSRFTokenHolder tokenHolder, User user) {
        return httpBuilder.post {
            request.uri.path = '/upgradeCheckout/personalDetails'
            request.contentType = JSON[0]
            def token = tokenHolder.getToken()
            Map<String, CharSequence> headers = new HashMap<>(request.getHeaders())
            headers.put('X-Requested-With','XMLHttpRequest')
            headers.put(token.getName(), token.getValue())
            request.setHeaders(headers)
            request.body = [ acceptedTermsAndConditions : true,
                             storeLocatorSelected : false,
                             bankDetails : [
                                   accountNumber : user.creditCard.accountNumber,
                                   holderName : user.name,
                                   sortCode : user.creditCard.sortCode
                             ]
            ]
            response.parser('application/json') { ChainedHttpConfig cfg, FromServer fs ->
                def jsonSlurper = new JsonSlurper()
                def json = jsonSlurper.parseText(fs.inputStream.text)
                return json
            }
            response.success { FromServer fs ->
                if (fs.getStatusCode() != 200) {
                    throw new RuntimeException("Received [${fs.getStatusCode()}] status code instead of 200 in response of [${fs.getUri()}]")
                }
                return true
            }
            response.failure { FromServer fs ->
                throw new RuntimeException("Couldn't reach [${fs.getUri()}]. Received [${fs.getStatusCode()}] status.")
            }
        }
    }

    /**
     * Method emulates intermediate request while payment page is loading
     * @param formWrapper
     * @return
     */
    String submitTCC(FormWrapper formWrapper) {
        String location = httpBuilder.post {
            request.uri = BASE_URL_TCC
            request.uri.path = formWrapper.getAction().replace(BASE_URL_TCC,'')
            request.body = formWrapper.getFormData()
            request.contentType = 'application/x-www-form-urlencoded'
            request.encoder 'application/x-www-form-urlencoded', NativeHandlers.Encoders.&form
            response.success { FromServer fs ->
                String location = FromServer.Header.find(
                        fs.headers, 'Location'
                )?.value
                if (fs.getStatusCode() != 302) {
                    throw new RuntimeException("Received [${fs.getStatusCode()}] status code instead of 303 in response of [${fs.getUri()}]")
                }
                return location
            }
        }
        return location.replace('https://ee.local:9002','')
    }

    /**
     * Method loads iframe part of payment page
     * @return
     */
    def doIframeRequest() {
        Document document =  httpBuilder.get {
            request.uri = BASE_URL_TCC
            request.uri.path = '/TCCDTP/showcardform'
            response.failure { FromServer fs ->
                throw new RuntimeException("Couldn't reach [${fs.getUri()}]. Received [${fs.getStatusCode()}] status.")
            }
        } as Document
        return PaymentFrame.class.newInstance(document)
    }

    /**
     * Method emulates submit of payment details from iframe
     * @param tokenHolder
     * @param user
     * @return
     */
    def doCardDetailsRequest(CSRFTokenHolder tokenHolder, User user) {

        Document responseDocument = httpBuilder.post {
            request.uri = BASE_URL_TCC
            request.uri.path = '/TCCDTP/carddetails'
            request.contentType = 'application/x-www-form-urlencoded'
            request.encoder 'application/x-www-form-urlencoded', NativeHandlers.Encoders.&form
            def token = tokenHolder.getTokenFromForm()
            request.body = [ 'cardSecurityCode' : user.creditCard.securityCode,
                             'Continue'         : 'Continue',
                             'creditCardNumber' : user.creditCard.cardNumber,
                             'creditCardType'   : user.creditCard.cardType,
                             (token.getName())  : token.getValue(),
                             'expirationMonth'  : user.creditCard.cardExpireMonth,
                             'expirationYear'   : user.creditCard.cardExpireYear,
                             'nameOnCard'       : user.creditCard.nameOnCard
            ]

            response.parser('text/html') { ChainedHttpConfig cfg, FromServer fs ->
                Document page = Jsoup.parse(fs.inputStream.text)
                return page
            }
            response.success { FromServer fs ->
                if (fs.getStatusCode() != 200) {
                    throw new RuntimeException("Received [${fs.getStatusCode()}] status code instead of 303 in response of [${fs.getUri()}]")
                }
            }
            response.failure { FromServer fs ->
                throw new RuntimeException("Couldn't reach [${fs.getUri()}]. Received [${fs.getStatusCode()}] status.")
            }
        } as Document
        return new FormWrapper(responseDocument.select('form').first() as FormElement) //temporary
    }
}
