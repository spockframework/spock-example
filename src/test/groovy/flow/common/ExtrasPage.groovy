package flow.common

import org.jsoup.nodes.Document

/**
 * This object represents the addExtras (Vouchers) page.
 */
class ExtrasPage extends PageWithBasket{

    private final static String ADDON_SELECTOR = ".addon--item"


    protected ExtrasPage(Document page) {
        super(page)
    }

    /**
    * Returns first addon title
    * @return
    */
    AddonItem getAddon() {
        return new AddonItem(find(ADDON_SELECTOR))
    }
}
