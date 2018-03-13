package flow.acquisition

import groovy.text.SimpleTemplateEngine
import groovyx.net.http.ApacheHttpBuilder
import groovyx.net.http.FromServer
import groovyx.net.http.HttpBuilder
import groovyx.net.http.NativeHandlers
import org.apache.http.impl.client.HttpClientBuilder
import org.jsoup.nodes.Document

import static flow.acquisition.SslUtils2.ignoreSslIssues


/**
 * Browses page by use {@link HttpBuilder}
 */
class Browser {
    private static final pathsMap = [
            (HomePage.class)            : '/',
            (PayMonthlyPhonesPage.class): '/mobile-phones/pay-monthly/gallery?search=:best-sellers',
            (PhoneDetailsPage.class)    : '/$categoryCode/$seoBundleType/$prettyId/details',
            (CartPage.class)            : '/cart',
            (DeliveryPage.class)        : '/delivery'
    ]
    private static final String BASE_URL = 'http://ee.local:9001'
    private static final String BASE_URL_SECURE = 'https://ee.local:9002/delivery'

    /**
     * Returns path which corresponds to specified {@code pageClass}
     * @param pageClass
     * @return
     */
    static String getPagePath(Class pageClass) {
        return pathsMap.get(pageClass)
    }

    HttpBuilder httpBuilder

    Browser() {
        this.httpBuilder = ApacheHttpBuilder.configure {
            request.uri = BASE_URL
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
    Class submit(FormWrapper formWrapper) {
        def location = postForm(formWrapper.getAction(), formWrapper.getFormData())
        return pathsMap.find { it -> (it.value == location) }.key
    }

    private String postForm(String path, Map form) {
        return httpBuilder.post {
            request.uri.path = path
            request.body = form
            request.contentType = 'application/x-www-form-urlencoded'
            request.encoder 'application/x-www-form-urlencoded', NativeHandlers.Encoders.&form
            response.success { FromServer fs ->
                String location = FromServer.Header.find(
                        fs.headers, 'Location'
                )?.value
                if (fs.getStatusCode() != 303) {
                    throw new RuntimeException("Received [${fs.getStatusCode()}] status code instead of 303 in response of [${fs.getUri()}]")
                }
                return location
            }

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
}
