package flow.acquisition

import flow.common.PageWithPhoneGallery
import org.jsoup.nodes.Document

/**
 * This object represents page with phone gallery on Acquisition flow
 */
class AquisitionPayMonthlyPhonesPage  extends PageWithPhoneGallery {
    AquisitionPayMonthlyPhonesPage(Document page) {
        super(page)
    }
}
