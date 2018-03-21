package flow.upgrade

import flow.common.OrderConfirmationPage
import org.jsoup.nodes.Document

/**
 * This object represents final order confirmation page for Upgrade flow
 */
class UpgradeOrderConfirmationPage extends OrderConfirmationPage {
    UpgradeOrderConfirmationPage(Document page) {
        super(page)
    }
}
