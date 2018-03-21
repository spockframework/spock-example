package flow.upgrade

import flow.common.CheckoutPage
import org.jsoup.nodes.Document

/**
 * This page object represents the checkout page for Upgrade flow.
 */
class UpgradeCheckoutPage extends CheckoutPage {
    UpgradeCheckoutPage(Document page) {
        super(page)
    }
}
