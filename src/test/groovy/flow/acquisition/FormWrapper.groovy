package flow.acquisition

import org.jsoup.Connection
import org.jsoup.nodes.FormElement

/**
 * Provides easy access to form data
 */
class FormWrapper extends ElementWrapper<FormElement> implements IForm {

    FormWrapper(FormElement element) {
        super(element)
    }

    @Override
    Map getFormData() {
        return element.formData().collectEntries { Connection.KeyVal it -> [(it.key()): it.value()]}
    }

    @Override
    String getAction() {
        return element.attr("action")
    }
}
