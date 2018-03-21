package flow.common

import org.jsoup.nodes.Element

/**
 * This object represents addon item
 */
class AddonItem extends ElementWrapper{

    private static final ADDON_COST_SELECTOR = '.addon--price'
    private static final ADDON_TITLE_SELECTOR = '.addon--title'
    static final String DEFAULT_ADDLINE_ADDON_TITLE = 'Get Apple Music free for six months'
    static final String DEFAULT_ADDLINE_ADDON_COST = '£ 0.00 a month'

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
        String priceElem = find(ADDON_COST_SELECTOR).text()
        return priceElem
    }
}
