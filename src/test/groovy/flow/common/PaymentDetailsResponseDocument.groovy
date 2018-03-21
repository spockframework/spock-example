package flow.common

import org.jsoup.nodes.Document
import org.jsoup.nodes.FormElement

/**
 * This object represents response document of payment details submit request
 */
class PaymentDetailsResponseDocument extends Page {
    PaymentDetailsResponseDocument(Document page) {
        super(page)
    }

    def getForm() {
        return new CleanActionForm(element.select('form').first() as FormElement)
    }

    boolean checkFormAction() {
        return find('form').attr('action').contains('tcc3ds')
    }
}
