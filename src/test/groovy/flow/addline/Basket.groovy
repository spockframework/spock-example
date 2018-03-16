package flow.addline

import flow.acquisition.ElementWrapper
import flow.common.BasketTestData
import flow.common.Money
import flow.common.MoneyBuilder
import org.jsoup.nodes.Element

/**
 * This object represents Your basket section
 */
class Basket extends ElementWrapper {

    private final static String UPGRADE_TITLE_SELECTOR = ".ee-upgrade-title"
    private final static String UPGRADE_CAPACITY_SELECTOR = ".ee-upgrade-spec-capacity"
    private final static String UPGRADE_COLOUR_SELECTOR = ".ee-upgrade-spec-colour"
    private final static String PAY_TODAY_SELECTOR = ".pay-today-total"
    private final static String MONTHLY_COST_SELECTOR = ".pay-monthly-total-value"

    Basket(Element element) {
        super(element)
    }

    /**
     * Returns the title portion of the name of shown phone.
     * @return
     */
    String getPhoneTitle() {
        return element.select(UPGRADE_TITLE_SELECTOR).first().text()
    }

    /**
     * Returns the capacity portion of the name of shown phone.
     * @return
     */
    String getPhoneCapacity() {
        def capacityElem = element.select(UPGRADE_CAPACITY_SELECTOR).first()
        return capacityElem.text()
    }

    /**
     * Returns the colour portion of the name of shown phone.
     * @return
     */
    String getPhoneColour() {
        def colourElem = element.select(UPGRADE_COLOUR_SELECTOR).first()
        return colourElem.text()
    }

    /**
     * Returns pay today total value from basket section
     * @return
     */
    Money getPayToday() {
        Element priceElem = find(PAY_TODAY_SELECTOR)
        return MoneyBuilder.fromElement(priceElem).build()
    }

    /**
     * Returns monthly cost total value from basket section
     * @return
     */
    Money getMonthlyCost() {
        Element priceElem = find(MONTHLY_COST_SELECTOR)
        return MoneyBuilder.fromElement(priceElem).build()
    }

    BasketTestData getTestData() {
        return BasketTestData.getBuilder()
                .phoneCapacity(getPhoneCapacity())
                .phoneColour(getPhoneColour())
                .payToday(getPayToday())
                .monthlyCost(getMonthlyCost())
                .build()
    }
}
