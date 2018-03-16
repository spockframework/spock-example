package flow.addline

import flow.acquisition.Page
import org.jsoup.nodes.Document

/**
 * Represents abstract page with basket. Functionality common for all those pages should be placed here
 */
class PageWithBasket extends Page{

    private final static String BASKET_SELECTOR = ".persistent-cart--movable"

    PageWithBasket(Document page) {
        super(page)
    }

    Basket getBasket() {
        return new Basket(find(BASKET_SELECTOR))
    }
}
