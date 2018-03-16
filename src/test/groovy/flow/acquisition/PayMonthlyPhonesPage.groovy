package flow.acquisition

import flow.common.Page
import org.jsoup.nodes.Document

/**
 * This page object represents the Pay Monthly Phones page.
 */
class PayMonthlyPhonesPage extends Page {
    private static final String GALLERY_ID = "#show"

    PayMonthlyPhonesPage(Document page) {
        super(page)
    }

    Gallery getGallery() {
        // duplication accepted in order to leverage power assertion statements
        assert !element.select(GALLERY_ID).isEmpty()
        def navBaElem = element.select(GALLERY_ID)
        return new Gallery(navBaElem.first())
    }
}
