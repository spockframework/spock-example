package flow.common

import flow.common.FormWrapper
import flow.common.Browser
import org.jsoup.nodes.FormElement

/**
 * This object represents form element on intermediate Initialize page
 */
class InitializeForm extends FormWrapper{
    InitializeForm(FormElement element) {
        super(element)
    }

    @Override
    String getAction() {
        return element.attr("action").replace(Browser.BASE_URL_TCC,'')
    }
}
