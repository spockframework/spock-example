package flow.acquisition

import org.jsoup.nodes.Element


/**
 * This page object is used to interact with the navigation bar.
 */
final class NavigationBar extends ElementWrapper {

    NavigationBar(Element element) {
        super(element)
    }

    boolean containsLink(String path) {
        return !element.select("a[href\$=${path}]").isEmpty()
    }
}

