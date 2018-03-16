package flow.acquisition

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
}
