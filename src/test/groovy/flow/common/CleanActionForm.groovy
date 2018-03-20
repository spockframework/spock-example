package flow.common

import org.jsoup.nodes.FormElement

/**
 * This object represents form with action cleaned from domain part
 */
class CleanActionForm extends FormWrapper {

    protected static final String BASE_URL_SECURE = 'https://ee.local:9002'
    protected static final String BASE_URL_TCC = 'http://127.0.0.1:8080'

    CleanActionForm(FormElement element) {
        super(element)
    }

    @Override
    String getAction() {
        return element.attr("action")
                .replace(BASE_URL_SECURE, '')
                .replace(BASE_URL_TCC, '')
    }
}
