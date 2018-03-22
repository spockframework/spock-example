package flow.addline

import flow.common.CheckoutPage
import org.jsoup.nodes.Document

/**
 * This page object represents the checkout page for AddLine flow.
 */
class AddLineCheckoutPage extends CheckoutPage {
    AddLineCheckoutPage(Document page) {
        super(page)
    }
}
