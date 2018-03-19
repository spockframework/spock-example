package flow.common

import org.jsoup.nodes.FormElement

/**
 * This object represents form with action cleaned from domain part
 */
class CleanActionForm extends FormWrapper {

    CleanActionForm(FormElement element) {
        super(element)
    }

    @Override
    String getAction() {
        return element.attr("action").replace(Browser.BASE_URL_SECURE, '')
    }
}
