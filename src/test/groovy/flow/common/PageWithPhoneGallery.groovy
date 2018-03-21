package flow.common

import org.jsoup.nodes.Document

/**
 * This object represents common page with phone gallery
 */
class PageWithPhoneGallery extends Page {

    private static final String GALLERY_ID = "#show"

    PageWithPhoneGallery(Document page) {
        super(page)
    }

    Gallery getGallery() {
        // duplication accepted in order to leverage power assertion statements
        assert !element.select(GALLERY_ID).isEmpty()
        def navBaElem = element.select(GALLERY_ID)
        return new Gallery(navBaElem.first())
    }
}
