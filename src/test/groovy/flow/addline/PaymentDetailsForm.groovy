package flow.addline

import flow.acquisition.FormWrapper
import org.jsoup.nodes.Element
import org.jsoup.nodes.FormElement

/**
 * This object represents form from payment details page
 */
class PaymentDetailsForm extends FormWrapper {

    PaymentDetailsForm(FormElement element) {
        super(element)
    }

    /**
     * Returns element of card type selector
     * @return
     */
    Element getCardTypeField() {
        return find('')
    }

    /**
     * Returns card holder name field
     * @return
     */
    Element getNameOnCardField() {
        return find('')
    }

    /**
     * Returns card number field
     * @return
     */
    Element getCardNumberField() {
        return find('')
    }

    /**
     * Returns card expiration month selector
     * @return
     */
    Element getExpireMonthField() {
        return find('')
    }

    /**
     * Returns card expiration year selector
     * @return
     */
    Element getExpireYearField() {
        return find('')
    }

    /**
     * Returns card security code field
     * @return
     */
    Element getSecurityCodeField() {
        return find('')
    }
}
