package flow.acquisition

import flow.common.Money
import flow.common.MoneyBuilder
import org.jsoup.nodes.Element

/**
 * todo[iim] to leave meaningful comment here
 */
class CartTotal extends ElementWrapper {

    public static final String PRICE_SELECTOR = '.prod-cost .price'

    CartTotal(Element element) {
        super(element)
    }

    Money getPayToday() {
        def priceElem = findAll(PRICE_SELECTOR).first()
        return MoneyBuilder.fromElement(priceElem).build()
    }

    Money getPayMonthly() {
        def priceElem = findAll(PRICE_SELECTOR).get(1)
        return MoneyBuilder.fromElement(priceElem).build()
    }
}
