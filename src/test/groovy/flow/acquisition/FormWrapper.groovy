package flow.acquisition

import org.jsoup.Connection
import org.jsoup.nodes.FormElement

/**
 * Provides easy access to form data
 */
class FormWrapper extends ElementWrapper<FormElement> {

    FormWrapper(FormElement element) {
        super(element)
    }

    Map getFormData() {
        return element.formData().collectEntries { Connection.KeyVal it -> [(it.key()): it.value()]}
    }

    String getAction() {
        return element.attr("action")
    }
}
