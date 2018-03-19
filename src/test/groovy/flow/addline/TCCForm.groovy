package flow.addline

import flow.common.FormWrapper
import flow.common.Browser
import org.jsoup.nodes.FormElement

/**
 * Created by Artsiom_Janchuryn on 3/19/2018.
 */
class TCCForm extends FormWrapper{
    TCCForm(FormElement element) {
        super(element)
    }

    @Override
    String getAction() {
        return element.attr("action").replace(Browser.BASE_URL_TCC,'')
    }
}
