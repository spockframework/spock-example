package flow.acquisition

import flow.common.Page
import org.jsoup.nodes.Document

/**
 * This page object represents the cart page.
 */
class CartPage extends Page {

    private CartPage(Document page) {
        super(page)
    }

    CartTotal getCartTotal() {
        def selector = '#your-basket-total'
        return new CartTotal(find(selector))
    }
}
