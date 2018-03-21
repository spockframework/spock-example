package flow.upgrade

import flow.common.CommonNavigationComponent
import flow.common.Page
import org.jsoup.nodes.Document

/**
 * This page object represents the Personalized Home page for Upgrade eligible user.
 */
class UpgradePersonalizedHomePage extends Page {

    private static final String NAVIGATION_ID = ".two-column-shout-container"

    UpgradePersonalizedHomePage(Document page) {
        super(page)
    }

    def getNavigationPanel() {
        def element = find(NAVIGATION_ID)
        return new CommonNavigationComponent(element)
    }
}
