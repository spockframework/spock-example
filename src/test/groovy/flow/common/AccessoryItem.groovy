package flow.common

import org.jsoup.nodes.Element

/**
 * This object represents item on choosing Accessory page
 */
class AccessoryItem extends ElementWrapper {

    private static final String ACCESSORY_TITLE_SELECTOR = '.accessory__title'
    private static final String ACCESSORY_MONTHLY_COST_SELECTOR = '.accessory__price-pay-monthly--wrapper'
    private static final String ACCESSORY_UPFRONT_COST_SELECTOR = '.accessory__price-s-size'

    AccessoryItem(Element element) {
        super(element)
    }

    /**
     * Returns title from addon item
     * @return
     */
    String getAccessoryTitle() {
        return find(ACCESSORY_TITLE_SELECTOR).text()
    }

    /**
     * Returns cost value from addon item
     * @return
     */
    String getAccessoryMonthlyCost() {
        return find(ACCESSORY_MONTHLY_COST_SELECTOR).text()
    }

    /**
     * Returns cost value from addon item
     * @return
     */
    String getAccessoryUpfrontCost() {
        return find(ACCESSORY_UPFRONT_COST_SELECTOR).text()
    }
}
