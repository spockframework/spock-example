package flow.addline

import flow.common.Page
import org.jsoup.nodes.Document

/**
 * This object represents intermediate secure processing page
 */
class ThreeDSBreakoutPage extends Page {
    ThreeDSBreakoutPage(Document page) {
        super(page)
    }

    def checkForm() {
        return find('#breakout').attr('action') == '/addConfirmation'
    }
}
