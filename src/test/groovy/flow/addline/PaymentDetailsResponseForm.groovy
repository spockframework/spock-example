package flow.addline

import flow.common.CleanActionForm
import org.jsoup.nodes.FormElement

/**
 * Class represents form which is loaded as response to submitting payment details
 */
class PaymentDetailsResponseForm extends CleanActionForm{

    PaymentDetailsResponseForm(FormElement element) {
        super(element)
    }

    boolean checkFormAction() {
        return find('form').attr('action').contains('tcc3ds')
    }
}
