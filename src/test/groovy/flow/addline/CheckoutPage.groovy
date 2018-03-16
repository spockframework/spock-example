package flow.addline

import flow.acquisition.FormWrapper
import flow.common.Browser
import org.jsoup.nodes.Document
import org.jsoup.nodes.FormElement

/**
 * This page object represents the checkout page.
 */
class CheckoutPage extends PageWithBasket{

    private final static String REQUEST_PIN_PATH = '/upgradeCheckout/sendPin'

    private final static String USER_INFO_SELECTOR_ID = '#CQ5AddlineCheckout_m17_1_auth_journey'

    CheckoutPage(Document page) {
        super(page)
    }

    /**
     * Returns user name from personal information section
     * @return
     */
    String getUserName() {
        return element.select("${USER_INFO_SELECTOR_ID} .name-placeholder").first().text()
    }

    /**
     * Returns user email from personal information section
     * @return
     */
    String getUserEmail() {
        return element.select("${USER_INFO_SELECTOR_ID} .name-placeholder").get(1).text()
    }

    /**
     * Returns user phone from personal information section
     * @return
     */
    String getUserPhone() {
        return element.select("${USER_INFO_SELECTOR_ID} .name-placeholder").get(2).text()
    }

    /**
     * Returns user address from personal information section
     * @return
     */
    String getUserAddress() {
        return element.select("${USER_INFO_SELECTOR_ID} .name-placeholder").get(3).text()
    }

    def requestPin(Browser browser, String ctn) {
        return browser.ajaxJson(REQUEST_PIN_PATH, this, new RequestPinJsonPayload(ctn))
    }

    static class JsonPayload {

    }

    static class RequestPinJsonPayload extends JsonPayload {
        private String ctn

        RequestPinJsonPayload(String ctn) {
            this.ctn = ctn
        }
    }
}
