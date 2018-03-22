package flow.common

import org.jsoup.nodes.Element

/**
 * This page object is used to interact with the phone gallery.
 */
final class Gallery extends ElementWrapper {

    Gallery(Element element) {
        super(element)
    }

    /**
     * Returns gallery item by looking for specific {@code prettyId} in link url
     * @param prettyId aka SEO Id
     * @return
     */
    GalleryItem getItemByPrettyId(String prettyId) {
        assert element.select(".tile a[href*=/${prettyId}/]")
        def galleryItemElem = element
                .select(".tile")
                .find {it -> !it.select("a[href*=/${prettyId}/]").isEmpty()}
        return new GalleryItem(galleryItemElem)
    }
}

