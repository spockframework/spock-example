package flow.addline

import flow.common.CSRFToken
import flow.common.Page
import org.jsoup.nodes.Document

/**
 * This object represents iframe of payment form
 */
class PaymentFrame extends Page {

    private static String PAYMENT_FORM_SELECTOR = '#payment'

    PaymentFrame(Document page) {
        super(page)
    }

    boolean checkPaymentForm() {
        assert !element.select(PAYMENT_FORM_SELECTOR).isEmpty()
        //ToDo enhance check quality
        return true
    }

    @Override
    CSRFToken getToken() {
        String name = 'csrfToken'
        String value = find('#csrfToken').attr('value')
        return new CSRFToken(name, value)
    }
}
