package flow.common

import org.jsoup.nodes.Element

/**
 * This object represents addon item
 */
class AddonItem extends ElementWrapper {

    private static final ADDON_COST_SELECTOR = '.addon--price'
    private static final ADDON_TITLE_SELECTOR = '.addon--title'
    private final static ADDON_CODE_SELECTOR = 'a.modal-link'

    AddonItem (Element element) {
        super(element)
    }

    /**
     * Returns title from addon item
     * @return
     */
    String getAddonTitle() {
        return find(ADDON_TITLE_SELECTOR).text()
    }

    /**
     * Returns cost value from addon item
     * @return
     */
    String getAddonCostValue() {
        return find(ADDON_COST_SELECTOR).text()
    }

    /**
     * Returns addon code
     * @return
     */
    String getAddonCode() {
        return find(ADDON_CODE_SELECTOR).attr('data-add-on-code')
    }
}
