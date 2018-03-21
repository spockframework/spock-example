package flow.common

import org.jsoup.nodes.Document

/**
 * This page object represents the checkout page.
 */
class CheckoutPage extends PageWithBasket {

    private final static String PIN = '999999'
    private final static String ACTION_PATH = '/upgradeCheckout/validatePin'
    private final static String REQUEST_PIN_PATH = '/upgradeCheckout/sendPin'
    private final static String REQUEST_RESONAL_INFO_PATH = '/upgradeCheckout/personalDetails'

    private final static String USER_INFO_SELECTOR_ID = '.billingContainer'

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
    def validatePin(Browser browser) {
        PinForm form = new PinForm()
        return browser.ajaxForm(form.getAction(), this, form.getFormData())
    }

    def personalInfoRequest(Browser browser, String creditCardAccountNumber, String holderName, String sortCode) {
        return browser.ajaxJson(REQUEST_RESONAL_INFO_PATH, this, new PersonalInfoJsonPayload('true', 'false',
                creditCardAccountNumber, holderName, sortCode))

    }

    class RequestPinJsonPayload extends JsonPayload {
        private String ctn

        RequestPinJsonPayload(String ctn) {
            this.ctn = ctn
        }
    }

    static class PersonalInfoJsonPayload extends JsonPayload {
        private String acceptedTermsAndConditions
        private String storeLocatorSelected
        def bankDetails

        PersonalInfoJsonPayload(String acceptedTermsAndConditions, String storeLocatorSelected, String accountNumber, String holderName, String sortCode) {
            this.acceptedTermsAndConditions = acceptedTermsAndConditions
            this.storeLocatorSelected = storeLocatorSelected
            this.bankDetails = [
                    accountNumber: accountNumber,
                    holderName   : holderName,
                    sortCode     : sortCode
            ]
        }
    }

    class PinForm  implements IForm {

        @Override
        Map getFormData() {
            return ['pin': PIN]
        }

        @Override
        String getAction() {
            return ACTION_PATH
        }
    }
}
