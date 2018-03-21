package flow.common

import org.jsoup.nodes.Document
import org.jsoup.nodes.FormElement

/**
 * This object represents frame on secure page where user should submit processing
 */
class WebSecurePageSubmitFrame  extends Page {
    WebSecurePageSubmitFrame(Document page) {
        super(page)
    }

    def checkForm() {
        return find('input[name=PaRes]').attr('value').length() > 0 &&
                find('input[name=MD]').attr('value').length() > 0 &&
                find('input[type=submit]').attr('value') == 'Submit'
    }

    CleanActionForm getForm() {
        def form = find('#redirform')
        return new CleanActionForm(form as FormElement)
    }
}
