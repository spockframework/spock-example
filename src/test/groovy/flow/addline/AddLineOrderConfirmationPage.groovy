package flow.addline

import flow.common.OrderConfirmationPage
import org.jsoup.nodes.Document

/**
 * This object represents final order confirmation page for AddLine flow
 */
class AddLineOrderConfirmationPage extends OrderConfirmationPage {
    AddLineOrderConfirmationPage(Document page) {
        super(page)
    }
}
