package flow.addline

import flow.common.PaymentPage
import org.jsoup.nodes.Document

/**
 * This object represents payment page for AddLine flow
 */
class AddLinePaymentPage extends PaymentPage {
    AddLinePaymentPage(Document page) {
        super(page)
    }
}
