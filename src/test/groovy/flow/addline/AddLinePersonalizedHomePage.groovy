package flow.addline

import flow.common.CommonNavigationComponent
import flow.common.Page
import org.jsoup.nodes.Document

/**
 * This page object represents the Personalized Home page for AddLine eligible user.
 */
class AddLinePersonalizedHomePage extends Page {
    private static final String BANNER_ID = "#CQ5LoginBannerComponent"
    private AddLinePersonalizedHomePage(Document page) {
        super(page)
    }

    def getBanner() {
        def element = find(BANNER_ID)
        return new CommonNavigationComponent(element)
    }
}
