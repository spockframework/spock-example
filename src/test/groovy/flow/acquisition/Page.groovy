package flow.acquisition

import org.jsoup.nodes.Document

/**
 * Represents abstract page. Functionality common for all pages should be placed here
 */
class Page extends ElementWrapper<Document> {

    Page(Document page) {
        super(page)
    }
}
