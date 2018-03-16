package flow.addline

import flow.acquisition.PayMonthlyPhonesPage
import org.jsoup.nodes.Document

/**
 * This page object represents the Pay Monthly Phones page for Add Line flow.
 */
class AddPayMonthlyPhonesPage extends PayMonthlyPhonesPage{

    AddPayMonthlyPhonesPage(Document page) {
        super(page)
    }
}
