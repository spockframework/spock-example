package flow.common

import org.jsoup.nodes.Document
import org.jsoup.nodes.FormElement

/**
 * This object represents iframe on Web secure page
 */
class WebSecurePageFrame extends Page {

    WebSecurePageFrame(Document page) {
        super(page)
    }

    CleanActionForm getForm() {
        def form = find('#init3ds')
        return new CleanActionForm(form as FormElement)
    }

    def checkDataNotDull() {
        return find('input[name=PaReq]').attr('value').length() > 0 &&
                find('input[name=TermUrl]').attr('value').length() > 0 &&
                find('input[name=MD]').attr('value').length() > 0 &&
                find('input[name=CSRFToken]').attr('value').length() > 0
    }
}
