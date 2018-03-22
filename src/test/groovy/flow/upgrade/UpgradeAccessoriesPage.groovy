package flow.upgrade

import flow.common.AccessoryItem
import flow.common.PageWithBasket
import org.jsoup.nodes.Document

/**
 * This object represents the Accessories page for Upgrade flow.
 */
class UpgradeAccessoriesPage extends PageWithBasket {

    private static final String ACCESSORY_SELECTOR = '.accessories__cell'
    UpgradeAccessoriesPage(Document page) {
        super(page)
    }

    /**
     * Returns first addon title
     * @return
     */
    AccessoryItem getAccessory() {
        return new AccessoryItem(find(ACCESSORY_SELECTOR))
    }
}
