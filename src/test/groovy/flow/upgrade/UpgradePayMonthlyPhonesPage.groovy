package flow.upgrade

import flow.common.PageWithPhoneGallery
import org.jsoup.nodes.Document

/**
 * This object represents page with phone gallery on Upgrade flow
 */
class UpgradePayMonthlyPhonesPage extends PageWithPhoneGallery {
    UpgradePayMonthlyPhonesPage(Document page) {
        super(page)
    }
}
