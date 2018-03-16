package flow.common

import flow.acquisition.ElementWrapper
import org.jsoup.nodes.Document

/**
 * Represents abstract page. Functionality common for all pages should be placed here
 */
class Page extends ElementWrapper<Document> implements CSRFTokenHolder {

    Page(Document page) {
        super(page)
    }

    @Override
    CSRFToken getToken() {
        String value = element.select('head > meta[name=csrf-token]').attr('content')
        String name = element.select('head > meta[name=csrf-param]').attr('content')
        return new CSRFToken(name, value)
    }

    @Override
    CSRFToken getTokenFromForm() {
        String name = 'csrfToken'
        String value = find('#csrfToken').attr('value')
        return new CSRFToken(name, value)
    }
}
