package flow.upgrade

import flow.common.PhoneDetailsPage
import org.jsoup.nodes.Document

/**
 * This object Represents page with personalized recommendations
 */
class RecommendationsPage extends PhoneDetailsPage {

    private static final String CURRENT_PLAN_SELECTOR = '#recommendOverview > div > p.plan-benefits-container'

    RecommendationsPage(Document page) {
        super(page)
    }

    String getCurrentPlan() {
        return find(CURRENT_PLAN_SELECTOR).text()
    }
}
