package flow.addline

import flow.common.AddonItem
import flow.common.BasketTestData
import flow.common.Browser
import flow.common.CarouselItem
import flow.common.CommonNavigationComponent
import flow.common.E2ETestPhone
import flow.common.E2ETestUser
import flow.common.EndToEndTest
import flow.common.Gallery
import flow.common.GalleryItem
import flow.common.InitializeForm
import flow.common.PaymentDetailsForm
import flow.common.PaymentDetailsResponseDocument
import flow.common.PaymentFrame
import flow.common.PhoneDetailsPage
import flow.common.ServicePlanCarousel
import flow.common.ThreeDSBreakoutPage
import flow.common.WebSecurePageFrame
import flow.common.WebSecurePageSubmitFrame
import org.junit.experimental.categories.Category
import spock.lang.Shared
import spock.lang.Specification

/**
 * Multi step test which covers "Add Line" scenario
 */
@Category(EndToEndTest.class)
class AddLineFlowSpec extends Specification{

    @Shared
    Browser browser = new Browser()

    def setupSpec() {
        browser.startSession(E2ETestUser.AddLineFlowUser.USER_ACCAUNT_NUMBER, E2ETestUser.AddLineFlowUser.MSISDN_NUMBER)
    }

    def 'user lands at shop home personalized page'() {

        when: 'The logged user sees Personalized Home Page page'
        AddLinePersonalizedHomePage personalizedHomePage = browser.open(AddLinePersonalizedHomePage.class, false)
        CommonNavigationComponent navBar = personalizedHomePage.getBanner()

        then: 'the navigation bar should contain link to Pay monthly phones page'
        navBar.containsLink(Browser.getPagePath(AddLinePayMonthlyPhonesPage.class))
    }

    def 'then user goes to add line gallery page'() {
        when: 'The user opens Pay monthly phones page'
        AddLinePayMonthlyPhonesPage page = browser.open(AddLinePayMonthlyPhonesPage.class, false)
        Gallery gallery = page.getGallery()

        then: 'a gallery should contain product tile with specific SEO Id'
        GalleryItem galleryItem = gallery.getItemByPrettyId(E2ETestPhone.AddLineFlowPhone.PRETTY_ID)

        and: 'tile should display correct information'
        galleryItem.title == E2ETestPhone.AddLineFlowPhone.TITLE
        galleryItem.monthlyCost == E2ETestPhone.AddLineFlowPhone.MONTHLY_COST
        galleryItem.upfrontCost == E2ETestPhone.AddLineFlowPhone.UPFRONT_COST
    }

    def 'then goes to Phone details page and adds bundle to cart'() {
        when: 'The user opens Phone details page'
        PhoneDetailsPage page = browser.open(PhoneDetailsPage.class, false,
                [categoryCode : E2ETestPhone.AddLineFlowPhone.CATEGORY,
                 seoBundleType: E2ETestPhone.AddLineFlowPhone.SEO_BUNDLE_TYPE,
                 prettyId     : E2ETestPhone.AddLineFlowPhone.PRETTY_ID
                ])
        then: 'the page should display correct phone information'
        page.phoneTitle == E2ETestPhone.AddLineFlowPhone.TITLE
        page.phoneCapacity == E2ETestPhone.AddLineFlowPhone.CAPACITY
        page.phoneColour == E2ETestPhone.AddLineFlowPhone.COLOUR

        and: 'the page should display list of available plans'
        ServicePlanCarousel planCarousel = page.getServicePlanCarousel()
        List<CarouselItem> carouselItems = planCarousel.getListItems()
        carouselItems.size() == 6

        and: 'the correct service plan displayed as first in carousel'
        def servicePlan = carouselItems.first()
        servicePlan.data == E2ETestPhone.AddLineFlowPhone.AddLineServicePlan.DATA
        servicePlan.monthlyCost == E2ETestPhone.AddLineFlowPhone.AddLineServicePlan.MONTHLY_COST
        servicePlan.handsetCost == E2ETestPhone.AddLineFlowPhone.AddLineServicePlan.HANDSET_COST
        servicePlan.minutes == E2ETestPhone.AddLineFlowPhone.AddLineServicePlan.MINUTES
        servicePlan.texts == E2ETestPhone.AddLineFlowPhone.AddLineServicePlan.TEXTS

        and: 'the user able to add phone & service plan bundle to cart'
        def form = servicePlan.getAddBundleToCartForm()
        browser.submit(form) == AddLineExtrasPage.class
    }

    def 'then user lands on extras page'() {
        when: 'The user sees Extras page'
        AddLineExtrasPage page = browser.open(AddLineExtrasPage.class, false)

        then: 'page should contain valid addon data'
        AddonItem item = page.getAddon()
        item.addonTitle == E2ETestPhone.DefaultAddon.DEFAULT_ADDON_TITLE
        item.addonCostValue == E2ETestPhone.DefaultAddon.DEFAULT_ADDON_COST

        and: 'and correct basket information'
        def basketTestData = BasketTestData.getBuilder()
                .title(E2ETestPhone.AddLineFlowPhone.TITLE)
                .phoneCapacity(E2ETestPhone.AddLineFlowPhone.CAPACITY)
                .phoneColour(E2ETestPhone.AddLineFlowPhone.COLOUR)
                .payToday(E2ETestPhone.AddLineFlowPhone.AddLineServicePlan.HANDSET_COST)
                .monthlyCost(E2ETestPhone.AddLineFlowPhone.AddLineServicePlan.MONTHLY_COST)
                .build()
        page.basket.testData == basketTestData
    }

    def 'then user goes to checkout page'() {
        when: 'Checkout page opens'
        AddLineCheckoutPage page = browser.open(AddLineCheckoutPage.class, false)

        then: 'page should contain correct personal information'
        page.userName == E2ETestUser.AddLineFlowUser.NAME
        page.userEmail == E2ETestUser.AddLineFlowUser.EMAIL
        page.userPhone == E2ETestUser.AddLineFlowUser.PHONE
        page.userAddress == E2ETestUser.AddLineFlowUser.ADDRESS

        and: 'and correct basket information'
        def basketTestData = BasketTestData.getBuilder()
                .title(E2ETestPhone.AddLineFlowPhone.TITLE)
                .phoneCapacity(E2ETestPhone.AddLineFlowPhone.CAPACITY)
                .phoneColour(E2ETestPhone.AddLineFlowPhone.COLOUR)
                .payToday(E2ETestPhone.AddLineFlowPhone.AddLineServicePlan.HANDSET_COST)
                .monthlyCost(E2ETestPhone.AddLineFlowPhone.AddLineServicePlan.MONTHLY_COST)
                .build()
        page.basket.testData == basketTestData

        when: 'the user submit pin validation form'
        Object json = page.requestPin(browser, E2ETestUser.AddLineFlowUser.PHONE)

        then: 'PIN sent successfully'
        json.status == 'SendingPinSuccess'

        and: 'user sent Pin for validation'
        page.validatePin(browser)

        when: 'user proceeds to payment'
        Object personalInfoJson = page.personalInfoRequest(browser, E2ETestUser.AddLineFlowUser.AddLineCreditCard.CARD_ACCOUNT_NUMBER,
                E2ETestUser.AddLineFlowUser.HOLDER, E2ETestUser.AddLineFlowUser.AddLineCreditCard.SORT_CODE)

        then: 'personal information request return success'
        personalInfoJson.successful
    }

    def 'then user lands at add line payment page'() {
        when: 'Payment page opens'
        AddLineInitPage page = browser.open(AddLineInitPage.class, false)

        then: 'hidden initialize page loading'
        InitializeForm payloadForm = page.getPayload()

        when: 'hidden initialize page processed successfully'
        String response = browser.submitMockForRedirect(payloadForm)

        then: 'server redirects to correct page'
        response.contains("/payment")

        when: 'user finally lands on payment page'
        AddLinePaymentPage paymentPage = browser.open(AddLinePaymentPage.class, false)

        then: 'page contains correct information'
        paymentPage.getPayTotalValue() == E2ETestPhone.AddLineFlowPhone.AddLineServicePlan.HANDSET_COST

        when: 'iframe loads'
        PaymentFrame frame = browser.open(PaymentFrame.class, true)

        then: 'iframe loaded correctly'
        frame.checkPaymentForm()

        when: 'the user submits payment details form'
        PaymentDetailsForm form = PaymentDetailsForm.getBuilder()
            .cardSecurityCode(E2ETestUser.AddLineFlowUser.AddLineCreditCard.SECURITY_CODE)
            .creditCardNumber(E2ETestUser.AddLineFlowUser.AddLineCreditCard.CARD_NUMBER)
            .creditCardType(E2ETestUser.AddLineFlowUser.AddLineCreditCard.CARD_TYPE)
            .csrfToken(frame.getToken().getValue())
            .expirationMonth(E2ETestUser.AddLineFlowUser.AddLineCreditCard.CARD_EXPIRE_MONTH)
            .expirationYear(E2ETestUser.AddLineFlowUser.AddLineCreditCard.CARD_EXPIRE_YEAR)
            .nameOnCard(E2ETestUser.AddLineFlowUser.AddLineCreditCard.NAME_ON_CARD)
            .build()
        PaymentDetailsResponseDocument cardDetailsResponse = browser.submitMockForDocument(PaymentDetailsResponseDocument.class, form)

        then: 'response contains success form'
        cardDetailsResponse.checkFormAction()

        when: 'browser sent intermediate request with secure payload'
        AddLineWebSecurePage securePage = browser.submitHybrisForDocument(AddLineWebSecurePage.class, cardDetailsResponse.getForm())

        then: 'intermediate response is valid'
        securePage.checkCSRFTokenNotNull()
    }

    def 'then user lands on secure payment confirmation page'() {
        when: 'page loads with mocked iframe'
        WebSecurePageFrame secureFrame = browser.open(WebSecurePageFrame.class, false)

        then: 'frame loads with intermediate form'
        secureFrame.checkDataNotDull()

        when: 'frame does intermediate form submit'
        WebSecurePageSubmitFrame submitFrame = browser.submitMockForDocument(WebSecurePageSubmitFrame.class, secureFrame.getForm())

        then: 'user sees request for submitting'
        submitFrame.checkForm()

        when: 'user submits processing'
        ThreeDSBreakoutPage breakoutPage = browser.submitHybrisForDocument(ThreeDSBreakoutPage.class, submitFrame.getForm())

        then: 'breakout form leads to confirmation page'
        breakoutPage.checkForm()
    }

    def 'then user lands on Order confirmation page'() {
        when: 'page loads'
        AddLineOrderConfirmationPage confirmationPage = browser.open(AddLineOrderConfirmationPage.class, false)

        then: 'page should contain correct personal information'
        confirmationPage.getUserName() == E2ETestUser.AddLineFlowUser.NAME
        confirmationPage.getUserBillingAddress() == E2ETestUser.AddLineFlowUser.BILLING_ADDRESS
        confirmationPage.getUserDeliveryAddress() == E2ETestUser.AddLineFlowUser.BILLING_ADDRESS
        confirmationPage.getUserPhone() == E2ETestUser.AddLineFlowUser.PHONE
        confirmationPage.getUserAccountName() == E2ETestUser.AddLineFlowUser.HOLDER
        confirmationPage.getPaymentMethod() == E2ETestUser.AddLineFlowUser.AddLineCreditCard.PAYMENT_METHOD
        confirmationPage.getAccountNumberEnding() == E2ETestUser.AddLineFlowUser.AddLineCreditCard.CARD_ACCOUNT_NUMBER.substring(4)
        confirmationPage.getCardType() == E2ETestUser.AddLineFlowUser.AddLineCreditCard.CARD_TYPE_FULL
        confirmationPage.getCardExpiryDate() == E2ETestUser.AddLineFlowUser.AddLineCreditCard.CARD_EXPIRE_MONTH.substring(1) + '/' +
                E2ETestUser.AddLineFlowUser.AddLineCreditCard.CARD_EXPIRE_YEAR
        confirmationPage.getCardNumEnding() == E2ETestUser.AddLineFlowUser.AddLineCreditCard.CARD_NUMBER.substring(12)

        and: 'and correct order information'
        confirmationPage.getPhoneTitle() == E2ETestPhone.AddLineFlowPhone.TITLE
        confirmationPage.getNewPlan() == E2ETestPhone.AddLineFlowPhone.AddLineServicePlan.PLAN_NAME
        confirmationPage.getPayToday() == E2ETestPhone.AddLineFlowPhone.AddLineServicePlan.HANDSET_COST
        confirmationPage.getMonthlyCost() == E2ETestPhone.AddLineFlowPhone.AddLineServicePlan.MONTHLY_COST
    }
}
