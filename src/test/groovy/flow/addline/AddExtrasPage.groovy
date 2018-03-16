package flow.addline

import org.jsoup.nodes.Document

/**
 * This page object represents the addExtras (Vouchers) page.
 */
class AddExtrasPage extends PageWithBasket{

    private final static String ADDON_SELECTOR = ".addon--item"


    AddExtrasPage(Document page) {
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
