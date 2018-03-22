package flow.common

import org.jsoup.nodes.Element
import org.jsoup.select.Elements

/**
 * Provides {@link ElementWrapper#toString()} implementation for better assertion statements
 */
class ElementWrapper<T extends Element> {
    protected final T element

    ElementWrapper(T element) {
        this.element = element
    }


    @Override
    String toString() {
        return element.toString()
    }

    protected Element find(String selector) {
        assert !element.select(selector).isEmpty()

        return element.select(selector).first()
    }

    protected Elements findAll(String selector) {
        assert !element.select(selector).isEmpty()

        return element.select(selector)
    }
}
