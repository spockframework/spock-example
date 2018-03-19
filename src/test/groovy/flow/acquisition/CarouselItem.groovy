package flow.acquisition

import flow.common.ElementWrapper
import flow.common.FormWrapper
import flow.common.Money
import flow.common.MoneyBuilder
import org.jsoup.nodes.Element
import org.jsoup.nodes.FormElement

/**
 * This class represents a service plan item in carousel that is shown
 * on the Phone details page.
 */
class CarouselItem extends ElementWrapper<Element> {

    public static final String CONTENT_DATA_SELECTOR = '.plan-content .content-text[data-init=%s]'
    public static final String CONTENT_DATA_SELECTOR_STRONG = "${CONTENT_DATA_SELECTOR} strong"

    CarouselItem(Element element) {
        super(element)
    }

    Money getMonthlyCost() {
        def selector = String.format(CONTENT_DATA_SELECTOR_STRONG, 'Monthly Cost')
        def priceElem = find(selector)
        return MoneyBuilder.fromElement(priceElem).build()
    }

    Money getHandsetCost() {
        def selector = String.format(CONTENT_DATA_SELECTOR_STRONG, 'Handset Cost')
        Element priceElem = find(selector)
        return MoneyBuilder.fromElement(priceElem).build()
    }

    String getData() {
        def selector = String.format(CONTENT_DATA_SELECTOR_STRONG, 'Data')
        return find(selector).text()
    }

    void hasMinutes(String minutes) {
        // Despite is possible to get a value by use a selector specified below,
        // we should avoid using selector like this because tests become fragile otherwise.
        // Even minor changes in markup will cause test to fail
        // element.select('.plan-content .content-text[data-init=Minutes]').first().childNodes[2].text()
        def selector = String.format(CONTENT_DATA_SELECTOR, 'Minutes')
        assert find(selector).text().contains(minutes)
    }

    /**
     * {@link flow.acquisition.CarouselItem#hasMinutes(java.lang.String)}
     * @param texts
     */
    void hasTexts(String texts) {
        def selector = String.format(CONTENT_DATA_SELECTOR, 'Texts')
        assert find(selector).text().contains(texts)
    }

    FormWrapper getAddBundleToCartForm() {
        def formElem = find('#command')
        return new FormWrapper(formElem as FormElement)
    }
}
