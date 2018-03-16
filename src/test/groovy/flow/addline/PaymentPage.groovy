package flow.addline

import flow.common.Money
import flow.common.MoneyBuilder
import flow.common.Page
import org.jsoup.nodes.Document

/**
 * This object represents payment page
 */
class PaymentPage extends Page{

    private static String PAY_TOTAL_SELECTOR = '.total-pay-container > h4.subheading.step3-subheading'

    PaymentPage(Document page) {
        super(page)
    }

    Money getPayTotalValue() {
        def moneyElement = find(PAY_TOTAL_SELECTOR)
        return MoneyBuilder.fromElement(moneyElement).build()
    }


}
