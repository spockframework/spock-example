package flow.upgrade

import flow.common.PaymentPage
import org.jsoup.nodes.Document

/**
 * This object represents payment page for Upgrade flow
 */
class UpgradePaymentPage extends PaymentPage {
    UpgradePaymentPage(Document page) {
        super(page)
    }
}
