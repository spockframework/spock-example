package flow.common

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

    private static final pathsMap = PagesPathsMap.pathsMap

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
    def <T> T open(Class<T> pageClass, boolean isMock, Map vars = [:]) {
        assert pathsMap.containsKey(pageClass)

        def path = getPagePath(pageClass)
        def uri = (isMock ? BASE_URL_TCC : BASE_URL_SECURE) + path
        def template = new SimpleTemplateEngine().createTemplate(uri).make(vars)
        def document = get(template.toString())
        return pageClass.newInstance(document) as T
    }

    private Document get(String uri) {
        return httpBuilder.get() {
            request.uri = uri
            response.failure { FromServer fs ->
                throw new RuntimeException("Couldn't reach [${fs.getUri()}]. Received [${fs.getStatusCode()}] status.")
            }
        } as Document
    }

    /**
     * Submits data by action path taken from {@code formWrapper}
     * @param form object
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
        def location = postForm(BASE_URL_SECURE, form.getAction(), form.getFormData(), redirectHandler)
        return pathsMap.find { it -> (it.value == location) }.key
    }

    private postForm(String uri, String path, Map form, Closure successHandler) {
        return httpBuilder.post {
            request.uri = uri
            request.uri.path = path
            request.body = form
            request.contentType = 'application/x-www-form-urlencoded'
            request.encoder 'application/x-www-form-urlencoded', NativeHandlers.Encoders.&form
            response.success successHandler
        }
    }

    /**
     * Method emulates intermediate request while payment page is loading
     * @param form object
     * @return redirect location string
     */
    String submitMockForRedirect(IForm form) {
        def redirectHandler = { FromServer fs ->
            String location = FromServer.Header.find(
                    fs.headers, 'Location'
            )?.value
            if (fs.getStatusCode() != 302) {
                throw new RuntimeException("Received [${fs.getStatusCode()}] status code instead of 302 in response of [${fs.getUri()}]")
            }
            return location
        }
        String location = postForm(BASE_URL_TCC, form.getAction(), form.getFormData(), redirectHandler)
        return location.replace('https://ee.local:9002', '')
    }

    /**
     * Method emulates intermediate request after payment details submitting
     * @param form object
     * @return secure page object
     */
    def <T> T submitHybrisForDocument(Class<T> documentClass, IForm form) {
        def documentHandler = { FromServer fs, Object body ->
            if (fs.getStatusCode() != 200) {
                throw new RuntimeException("Received [${fs.getStatusCode()}] status code instead of 200 in response of [${fs.getUri()}]")
            }
            return body
        }

        Document response = postForm(BASE_URL_SECURE, form.getAction(), form.getFormData(), documentHandler) as Document
        return documentClass.newInstance(response)
    }

    /**
     * Method emulates submit of payment details from iframe
     * @param tokenHolder
     * @param user
     * @return
     */
    def <T> T submitMockForDocument(Class<T> documentClass, IForm form) {
        def documentHandler = { FromServer fs, Object body ->
            if (fs.getStatusCode() != 200) {
                throw new RuntimeException("Received [${fs.getStatusCode()}] status code instead of 200 in response of [${fs.getUri()}]")
            }
            return body
        }
        Document response = postForm(BASE_URL_TCC, form.getAction(), form.getFormData(), documentHandler) as Document
        return documentClass.newInstance(response)
    }

    /**
     * Do request with hardcoded credentials of test user
     * set sessionID to class field
     * @return session id as String
     */
    def startSession(String accountNumber, String msisdn) {
        return httpBuilder.get {
            request.uri.path = '/authorize'
            request.uri.query = [
                    'code'         : '123',
                    'state'        : '123',
                    'accountNumber': accountNumber,
                    'msisdn'       : msisdn]
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

    /**
     * Method emulates ajax request from page with JSON payload
     * @param path
     * @param tokenHolder
     * @param payload
     * @return response body object
     */
    def ajaxJson(String path, CSRFTokenHolder tokenHolder, JsonPayload payload) {
        return httpBuilder.post {
            request.uri.path = path
            request.contentType = JSON[0]
            def token = tokenHolder.getToken()
            Map<String, CharSequence> headers = new HashMap<>(request.getHeaders())
            headers.put('X-Requested-With', 'XMLHttpRequest')
            headers.put(token.getName(), token.getValue())
            request.setHeaders(headers)
            request.body = FlowUtils.asMap(payload)
            response.success { FromServer fs, Object body ->
                if (fs.getStatusCode() != 200) {
                    throw new RuntimeException("Received [${fs.getStatusCode()}] status code instead of 200 in response of [${fs.getUri()}]")
                }
                return body
            }
            response.failure { FromServer fs ->
                throw new RuntimeException("Couldn't reach [${fs.getUri()}]. Received [${fs.getStatusCode()}] status.")
            }
        }
    }

    /**
     * Method emulates ajax request from page with FORM payload
     * @param tokenHolder
     * @return response body object
     */
    def ajaxForm(String path, CSRFTokenHolder tokenHolder, Map payload) {
        return httpBuilder.post {
            request.uri.path = path
            request.contentType = 'application/x-www-form-urlencoded'
            def token = tokenHolder.getToken()
            Map<String, CharSequence> headers = new HashMap<>(request.getHeaders())
            headers.put('X-Requested-With', 'XMLHttpRequest')
            headers.put(token.getName(), token.getValue())
            request.setHeaders(headers)
            request.body = payload
            request.encoder 'application/x-www-form-urlencoded', NativeHandlers.Encoders.&form
            response.success { FromServer fs, Object body ->
                if (fs.getStatusCode() != 200) {
                    throw new RuntimeException("Received [${fs.getStatusCode()}] status code instead of 200 in response of [${fs.getUri()}]")
                }
                return body
            }
            response.failure { FromServer fs ->
                throw new RuntimeException("Couldn't reach [${fs.getUri()}]. Received [${fs.getStatusCode()}] status.")
            }
        }
    }
}
