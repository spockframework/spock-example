package flow.addline

import flow.acquisition.Page
import org.jsoup.nodes.Document

/**
 * This page object represents the Personalized Home page.
 */
class PersonalizedHomePage extends Page {
    private static final String BANNER_ID = "#CQ5LoginBannerComponent"
    private PersonalizedHomePage(Document page) {
        super(page)
    }

    def getBanner() {
        def element = find(BANNER_ID)
        return new AddLineNavigationBar(element)
    }
}
