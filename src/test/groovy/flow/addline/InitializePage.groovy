package flow.addline

import flow.common.Page
import org.jsoup.nodes.Document

/**
 * This object represents hidden initialize page
 */
class InitializePage extends Page{

    private static final PAYLOAD_FORM_SELECTOR = '#initcardcapture'

    InitializePage(Document page) {
        super(page)
    }

    TCCForm getPayload() {
        def payloadForm = find(PAYLOAD_FORM_SELECTOR)
        return new TCCForm(payloadForm)
    }

}
