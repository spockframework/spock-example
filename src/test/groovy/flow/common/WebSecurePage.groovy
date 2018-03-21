package flow.common

import org.jsoup.nodes.Document

/**
 * This object represents 3DSecure page
 */
class WebSecurePage extends Page {

    WebSecurePage(Document page) {
        super(page)
    }

    def checkCSRFTokenNotNull() {
        def token = getToken()
        return token != null &&
                token.getName().length() > 0 &&
                token.getValue().length() > 0
    }
}
