package flow.common

import org.jsoup.nodes.Element

/**
 * Represents navigation component with common functionality
 */
class CommonNavigationComponent  extends ElementWrapper {
    CommonNavigationComponent(Element element) {
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
