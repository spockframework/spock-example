package flow.common

import org.jsoup.nodes.Document

/**
 * This object represents final order confirmation page
 */
class OrderConfirmationPage  extends Page {

    private final static String USER_INFO_SELECTOR = '.your-details'
    private final static String BILLING_INFORMATION_DIV_SELECTOR = "${USER_INFO_SELECTOR} > div:contains(Billing information)"
    private final static String PAYMENT_DETAILS_DIV_SELECTOR = "${USER_INFO_SELECTOR} > div:contains(Payment details)"

    OrderConfirmationPage(Document page) {
        super(page)
    }

    /**
     * Returns user name from "Your details" section
     * @return
     */
    String getUserName() {
        return element.select("${USER_INFO_SELECTOR} > div.col > p").first().text().replace('\u00A0\u00A0', ' ')
    }

    /**
     * Returns user billing address from "Your details" section
     * @return
     */
    String getUserBillingAddress() {
        return element.select("${USER_INFO_SELECTOR} > div.billing-address-details > address").first().text()
    }

    /**
     * Returns user delivery address from "Your details" section
     * @return
     */
    String getUserDeliveryAddress() {
        return element.select("${USER_INFO_SELECTOR} > div:contains(Delivery address) > address").first().text()
    }

    /**
     * Returns user phone from "Your details" section
     * @return
     */
    String getUserPhone() {
        return element.select("${USER_INFO_SELECTOR} > div:contains(Contact number) > p").first().text()
    }

    /**
     * Returns user account name from "Your details" section
     * @return
     */
    String getUserAccountName() {
        return element.select("${BILLING_INFORMATION_DIV_SELECTOR} > p:contains(Name on account) > span").first().text()
    }

    /**
     * Returns payment method from "Your details" section
     * @return
     */
    String getPaymentMethod() {
        def paragraph = element.select("${BILLING_INFORMATION_DIV_SELECTOR} > p:contains(Payment method) > span")
        return paragraph.first().text() + ' ' + paragraph.get(1).text()
    }

    /**
     * Returns user account number ending from "Your details" section
     * @return
     */
    String getAccountNumberEnding() {
        return element.select("${BILLING_INFORMATION_DIV_SELECTOR} > p#billing-account-number > span").first().text()
    }

    /**
     * Returns user card type from "Your details" section
     * @return
     */
    String getCardType() {
        return element.select("${PAYMENT_DETAILS_DIV_SELECTOR} > p:contains(Type of card) > span").first().text()
    }

    /**
     * Returns user card expiry date from "Your details" section
     * @return
     */
    String getCardExpiryDate() {
        return element.select("${PAYMENT_DETAILS_DIV_SELECTOR} > p:contains(Expiry date) > span").first().text()
    }

    /**
     * Returns user card ending from "Your details" section
     * @return
     */
    String getCardNumEnding() {
        return element.select("${PAYMENT_DETAILS_DIV_SELECTOR} > p#card-number > span").first().text()
    }

    /**
     * Returns ordered phone title from "Review your order" section
     * @return
     */
    String getPhoneTitle() {
        return element.select("p.ee-upgrade-title").first().text()
    }

    /**
     * Returns ordered new plan from "Review your order" section
     * @return
     */
    String getNewPlan() {
        return element.select("a[href=#yourPlan]").first().text()
    }

    /**
     * Returns pay today value from "Review your order" section
     * @return
     */
    Money getPayToday() {
        return MoneyBuilder.fromElement(element.select("table.pay-today-table > tbody > tr > td").first()).build()
    }

    /**
     * Returns monthly cost from "Review your order" section
     * @return
     */
    Money getMonthlyCost() {
        return MoneyBuilder.fromElement(element.select("table.pay-monthly-table > tbody > tr > td").first()).build()
    }

}
