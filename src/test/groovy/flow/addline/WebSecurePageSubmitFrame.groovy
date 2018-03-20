package flow.addline

import flow.common.Page
import org.jsoup.nodes.Document

/**
 * This object represents frame on secure page where user should submit processing
 */
class WebSecurePageSubmitFrame  extends Page {
    WebSecurePageSubmitFrame(Document page) {
        super(page)
    }

    def checkDataNotDull() {
        return find('input[name=PaRes]').attr('value').length() > 0 &&
                find('input[name=MD]').attr('value').length() > 0
    }
}
