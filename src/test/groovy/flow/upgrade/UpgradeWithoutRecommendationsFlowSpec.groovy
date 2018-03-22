package flow.upgrade

import flow.common.AccessoryItem
import flow.common.AddonItem
import flow.common.BasketTestData
import flow.common.Browser
import flow.common.CarouselItem
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
 * Multi step test which covers "Upgrade" scenario through upgrade gallery page
 */
@Category(EndToEndTest.class)
class UpgradeWithoutRecommendationsFlowSpec extends Specification {
    @Shared
    Browser browser = new Browser()

    def setupSpec() {
        browser.startSession(E2ETestUser.UpgradeFlowUser.USER_ACCAUNT_NUMBER, E2ETestUser.UpgradeFlowUser.MSISDN_NUMBER)
    }

//    def 'user lands at shop home personalized page'() {
//        when: 'The logged user sees Personalized Home Page page'
//        UpgradePersonalizedHomePage homePage = browser.open(UpgradePersonalizedHomePage.class, false)
//
//        then: 'the navigation panel should contain link to recommendations page'
//        CommonNavigationComponent navigationComponent = homePage.getNavigationPanel()
//        navigationComponent.containsLink(Browser.getPagePath(RecommendationsPage.class))
//    }

    def 'user goes to add line gallery page'() {
        when: 'The user opens Pay monthly phones page'
        UpgradePayMonthlyPhonesPage page = browser.open(UpgradePayMonthlyPhonesPage.class, false)
        Gallery gallery = page.getGallery()

        then: 'a gallery should contain product tile with specific SEO Id'
        GalleryItem galleryItem = gallery.getItemByPrettyId(E2ETestPhone.UpgradeFlowNonRecommendedPhone.PRETTY_ID)

        and: 'tile should display correct information'
        galleryItem.title == E2ETestPhone.UpgradeFlowNonRecommendedPhone.TITLE
        galleryItem.monthlyCost == E2ETestPhone.UpgradeFlowNonRecommendedPhone.MONTHLY_COST
        galleryItem.upfrontCost == E2ETestPhone.UpgradeFlowNonRecommendedPhone.UPFRONT_COST
    }

    def 'then user goes to selected phone details page'() {
        when: 'The user opens Phone details page'
        PhoneDetailsPage page = browser.open(PhoneDetailsPage.class, false,
                [categoryCode : E2ETestPhone.UpgradeFlowNonRecommendedPhone.CATEGORY,
                 seoBundleType: E2ETestPhone.UpgradeFlowNonRecommendedPhone.SEO_BUNDLE_TYPE,
                 prettyId     : E2ETestPhone.UpgradeFlowNonRecommendedPhone.PRETTY_ID
                ])

        then: 'the page should display correct phone information'
        page.phoneTitle == E2ETestPhone.UpgradeFlowNonRecommendedPhone.TITLE
        page.phoneCapacity == E2ETestPhone.UpgradeFlowNonRecommendedPhone.CAPACITY
        page.phoneColour == E2ETestPhone.UpgradeFlowNonRecommendedPhone.COLOUR

        and: 'the page should display list of available plans'
        ServicePlanCarousel planCarousel = page.getServicePlanCarousel()
        List<CarouselItem> carouselItems = planCarousel.getListItems()
        carouselItems.size() == 5

        and: 'the correct service plan displayed as first in carousel'
        def servicePlan = carouselItems.get(1)
        servicePlan.data == E2ETestPhone.UpgradeFlowNonRecommendedPhone.UpgradeNonRecommendedServicePlan.DATA
        servicePlan.monthlyCost == E2ETestPhone.UpgradeFlowNonRecommendedPhone.UpgradeNonRecommendedServicePlan.MONTHLY_COST
        servicePlan.handsetCost == E2ETestPhone.UpgradeFlowNonRecommendedPhone.UpgradeNonRecommendedServicePlan.HANDSET_COST
        servicePlan.minutes == E2ETestPhone.UpgradeFlowNonRecommendedPhone.UpgradeNonRecommendedServicePlan.MINUTES
        servicePlan.texts == E2ETestPhone.UpgradeFlowNonRecommendedPhone.UpgradeNonRecommendedServicePlan.TEXTS

        and: 'the user able to add phone & service plan bundle to cart'
        def form = servicePlan.getAddBundleToCartForm()
        browser.submit(form) == UpgradeExtrasPage.class
    }

    def 'then user lands on extras page'() {
        when: 'The user sees Extras page'
        UpgradeExtrasPage page = browser.open(UpgradeExtrasPage.class, false)

        then: 'page should contain valid addon data'
        AddonItem item = page.getAddon()
        item.addonCode == E2ETestPhone.DefaultAddon.DEFAULT_ADDON_CODE
        item.addonCostValue == E2ETestPhone.DefaultAddon.DEFAULT_ADDON_COST

        and: 'and correct basket information'
        def basketTestData = BasketTestData.getBuilder()
                .title(E2ETestPhone.UpgradeFlowNonRecommendedPhone.TITLE)
                .phoneCapacity(E2ETestPhone.UpgradeFlowNonRecommendedPhone.CAPACITY)
                .phoneColour(E2ETestPhone.UpgradeFlowNonRecommendedPhone.COLOUR)
                .payToday(E2ETestPhone.UpgradeFlowNonRecommendedPhone.UpgradeNonRecommendedServicePlan.HANDSET_COST)
                .monthlyCost(E2ETestPhone.UpgradeFlowNonRecommendedPhone.UpgradeNonRecommendedServicePlan.MONTHLY_COST)
                .build()
        page.basket.testData == basketTestData
    }

    def 'then user lands on accessories page'() {
        when: 'The user sees Accessories page'
        UpgradeAccessoriesPage page = browser.open(UpgradeAccessoriesPage.class, false)

        then: 'page should contain valid accessory data'
        AccessoryItem item = page.getAccessory()
        item.accessoryTitle == E2ETestPhone.DefaultAccessory.DEFAULT_ACCESSORY_TITLE
        item.accessoryMonthlyCost == E2ETestPhone.DefaultAccessory.DEFAULT_ACCESSORY_MONTHLY_COST
        item.accessoryUpfrontCost == E2ETestPhone.DefaultAccessory.DEFAULT_ACCESSORY_UPFRONT_COST

        and: 'and correct basket information'
        def basketTestData = BasketTestData.getBuilder()
                .title(E2ETestPhone.UpgradeFlowNonRecommendedPhone.TITLE)
                .phoneCapacity(E2ETestPhone.UpgradeFlowNonRecommendedPhone.CAPACITY)
                .phoneColour(E2ETestPhone.UpgradeFlowNonRecommendedPhone.COLOUR)
                .payToday(E2ETestPhone.UpgradeFlowNonRecommendedPhone.UpgradeNonRecommendedServicePlan.HANDSET_COST)
                .monthlyCost(E2ETestPhone.UpgradeFlowNonRecommendedPhone.UpgradeNonRecommendedServicePlan.MONTHLY_COST)
                .build()
        page.basket.testData == basketTestData
    }

    def 'then user goes to checkout page'() {
        when: 'Checkout page opens'
        UpgradeCheckoutPage page = browser.open(UpgradeCheckoutPage.class, false)

        then: 'page should contain correct personal information'
        page.userName == E2ETestUser.UpgradeFlowUser.NAME
        page.userEmail == E2ETestUser.UpgradeFlowUser.EMAIL
        page.userPhone == E2ETestUser.UpgradeFlowUser.PHONE
        page.userAddress == E2ETestUser.UpgradeFlowUser.ADDRESS

        and: 'and correct basket information'
        def basketTestData = BasketTestData.getBuilder()
                .title(E2ETestPhone.UpgradeFlowNonRecommendedPhone.TITLE)
                .phoneCapacity(E2ETestPhone.UpgradeFlowNonRecommendedPhone.CAPACITY)
                .phoneColour(E2ETestPhone.UpgradeFlowNonRecommendedPhone.COLOUR)
                .payToday(E2ETestPhone.UpgradeFlowNonRecommendedPhone.UpgradeNonRecommendedServicePlan.HANDSET_COST)
                .monthlyCost(E2ETestPhone.UpgradeFlowNonRecommendedPhone.UpgradeNonRecommendedServicePlan.MONTHLY_COST)
                .build()
        page.basket.testData == basketTestData

        when: 'the user submit pin validation form'
        Object requestPinJson = page.requestPin(browser, E2ETestUser.UpgradeFlowUser.PHONE)

        then: 'PIN sent successfully'
        requestPinJson.status == 'SendingPinSuccess'

        and: 'user sent Pin for validation'
        page.validatePin(browser)

        when: 'user proceeds to payment'
        Object personalInfoJson = page.personalInfoRequest(browser, E2ETestUser.UpgradeFlowUser.UpgradeCreditCard.CARD_ACCOUNT_NUMBER,
                E2ETestUser.UpgradeFlowUser.HOLDER, E2ETestUser.UpgradeFlowUser.UpgradeCreditCard.SORT_CODE)

        then: 'personal information request return success'
        personalInfoJson.successful
    }

    def 'then user lands on upgrade payment page'() {
        when: 'Payment page opens'
        UpgradeInitPage page = browser.open(UpgradeInitPage.class, false)

        then: 'hidden initialize page loading'
        InitializeForm payloadForm = page.getPayload()

        when: 'hidden initialize page processed successfully'
        String response = browser.submitMockForRedirect(payloadForm)

        then: 'server redirects to correct page'
        response.contains("/payment")

        when: 'user finally lands on payment page'
        UpgradePaymentPage paymentPage = browser.open(UpgradePaymentPage.class, false)

        then: 'page contains correct information'
        paymentPage.getPayTotalValue() == E2ETestPhone.UpgradeFlowNonRecommendedPhone.UpgradeNonRecommendedServicePlan.HANDSET_COST

        when: 'iframe loads'
        PaymentFrame frame = browser.open(PaymentFrame.class, true)

        then: 'iframe loaded correctly'
        frame.checkPaymentForm()

        when: 'the user submits payment details form'
        PaymentDetailsForm form = PaymentDetailsForm.getBuilder()
                .cardSecurityCode(E2ETestUser.UpgradeFlowUser.UpgradeCreditCard.SECURITY_CODE)
                .creditCardNumber(E2ETestUser.UpgradeFlowUser.UpgradeCreditCard.CARD_NUMBER)
                .creditCardType(E2ETestUser.UpgradeFlowUser.UpgradeCreditCard.CARD_TYPE)
                .csrfToken(frame.getToken().getValue())
                .expirationMonth(E2ETestUser.UpgradeFlowUser.UpgradeCreditCard.CARD_EXPIRE_MONTH)
                .expirationYear(E2ETestUser.UpgradeFlowUser.UpgradeCreditCard.CARD_EXPIRE_YEAR)
                .nameOnCard(E2ETestUser.UpgradeFlowUser.UpgradeCreditCard.NAME_ON_CARD)
                .build()
        PaymentDetailsResponseDocument cardDetailsResponse = browser.submitMockForDocument(PaymentDetailsResponseDocument.class, form)

        then: 'response contains success form'
        cardDetailsResponse.checkFormAction()

        when: 'browser sent intermediate request with secure payload'
        UpgradeWebSecurePage securePage = browser.submitHybrisForDocument(UpgradeWebSecurePage.class, cardDetailsResponse.getForm())

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
        UpgradeOrderConfirmationPage confirmationPage = browser.open(UpgradeOrderConfirmationPage.class, false)

        then: 'page should contain correct personal information'
        confirmationPage.getUserName() == E2ETestUser.UpgradeFlowUser.NAME
        confirmationPage.getUserBillingAddress() == E2ETestUser.UpgradeFlowUser.BILLING_ADDRESS
        confirmationPage.getUserDeliveryAddress() == E2ETestUser.UpgradeFlowUser.BILLING_ADDRESS
        confirmationPage.getUserPhone() == E2ETestUser.UpgradeFlowUser.PHONE
        confirmationPage.getUserAccountName() == E2ETestUser.UpgradeFlowUser.HOLDER
        confirmationPage.getPaymentMethod() == E2ETestUser.UpgradeFlowUser.UpgradeCreditCard.PAYMENT_METHOD
        confirmationPage.getAccountNumberEnding() == E2ETestUser.UpgradeFlowUser.UpgradeCreditCard.CARD_ACCOUNT_NUMBER.substring(4)
        confirmationPage.getCardType() == E2ETestUser.UpgradeFlowUser.UpgradeCreditCard.CARD_TYPE_FULL
        confirmationPage.getCardExpiryDate() == E2ETestUser.UpgradeFlowUser.UpgradeCreditCard.CARD_EXPIRE_MONTH.substring(1) + '/' +
                E2ETestUser.UpgradeFlowUser.UpgradeCreditCard.CARD_EXPIRE_YEAR
        confirmationPage.getCardNumEnding() == E2ETestUser.UpgradeFlowUser.UpgradeCreditCard.CARD_NUMBER.substring(12)

        and: 'and correct order information'
        confirmationPage.getPhoneTitle() == E2ETestPhone.UpgradeFlowNonRecommendedPhone.TITLE
        confirmationPage.getNewPlan() == E2ETestPhone.UpgradeFlowNonRecommendedPhone.UpgradeNonRecommendedServicePlan.PLAN_NAME
        confirmationPage.getPayToday() == E2ETestPhone.UpgradeFlowNonRecommendedPhone.UpgradeNonRecommendedServicePlan.HANDSET_COST
        confirmationPage.getMonthlyCost() == E2ETestPhone.UpgradeFlowNonRecommendedPhone.UpgradeNonRecommendedServicePlan.MONTHLY_COST
    }
}
