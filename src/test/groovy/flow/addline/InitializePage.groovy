package flow.addline

import flow.acquisition.FormWrapper
import flow.acquisition.Page
import org.jsoup.nodes.Document

/**
 * This object represents hidden initialize page
 */
class InitializePage extends Page{

    private static final PAYLOAD_FORM_SELECTOR = '#initcardcapture'

    InitializePage(Document page) {
        super(page)
    }

    FormWrapper getPayload() {
        def payloadForm = find(PAYLOAD_FORM_SELECTOR)
        return new FormWrapper(payloadForm)
    }

}
