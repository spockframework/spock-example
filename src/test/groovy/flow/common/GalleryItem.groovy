package flow.common

import org.jsoup.nodes.Element

/**
 * This class represents a gallery item that is shown
 * on the Pay monthly phones page.
 */
class GalleryItem extends ElementWrapper<Element> {

    GalleryItem(Element element) {
        super(element)
    }

    /**
     * Returns the title of the shown gallery item.
     * @return
     */
    String getTitle() {
        return element.select(".product-title").text()
    }

    /**
     * Returns the monthly cost of the shown gallery item.
     * @return
     */
    Money getMonthlyCost() {
        def priceElem = element.select(".monthly-cost").first()
        return MoneyBuilder.fromText(priceElem.text()).build()
    }

    /**
     * Returns the upfront cost of the shown gallery item.
     * @return
     */
    Money getUpfrontCost() {
        def priceElem = element.select(".device-cost").first()
        return MoneyBuilder.fromText(priceElem.text()).build()
    }
}
