package flow.upgrade

import flow.common.WebSecurePage
import org.jsoup.nodes.Document

/**
 * This object represents 3DSecure page for Upgrade flow
 */
class UpgradeWebSecurePage extends WebSecurePage {
    UpgradeWebSecurePage(Document page) {
        super(page)
    }
}
