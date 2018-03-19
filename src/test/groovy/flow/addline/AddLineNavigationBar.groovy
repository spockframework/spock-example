package flow.addline

import flow.common.ElementWrapper
import org.jsoup.nodes.Element

/**
 * This object represents Add line navigation panel on personalized home page
 */
class AddLineNavigationBar extends ElementWrapper {

    AddLineNavigationBar(Element element) {
        super(element)
    }

    /**
     * Method checks if given link is presented inside navigation bar
     * @param path (link)
     * @return
     */
    boolean containsLink(String path) {
        return !element.select("a[href\$=${path}]").isEmpty()
    }
}
