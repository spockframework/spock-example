package flow.addline

import flow.common.BasketTestData
import flow.common.Browser
import flow.acquisition.CarouselItem
import flow.common.CleanActionForm
import flow.common.E2ETestPhone
import flow.common.EndToEndTest
import flow.acquisition.Gallery
import flow.acquisition.GalleryItem
import flow.acquisition.PhoneDetailsPage
import flow.acquisition.ServicePlanCarousel
import flow.common.FlowUtils
import flow.common.User
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

    @Shared
    User addLineUser = FlowUtils.createAddLineUser()

    def setupSpec() {
        browser.startSession(addLineUser)
    }

    def 'user lands at shop home personalized page'() {

        when: 'The logged user sees Personalized Home Page page'
        PersonalizedHomePage personalizedHomePage = browser.open(PersonalizedHomePage.class, false)
        AddLineNavigationBar navBar = personalizedHomePage.getBanner()

        then: 'the navigation bar should contain link to Pay monthly phones page'
        navBar.containsLink(Browser.getPagePath(AddPayMonthlyPhonesPage.class))
    }

    def 'then user goes to add line gallery page'() {
        when: 'The user opens Pay monthly phones page'
        AddPayMonthlyPhonesPage page = browser.open(AddPayMonthlyPhonesPage.class, false)
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
        servicePlan.hasMinutes(E2ETestPhone.AddLineFlowPhone.AddLineServicePlan.MINUTES)
        servicePlan.hasTexts(E2ETestPhone.AddLineFlowPhone.AddLineServicePlan.TEXTS)

        and: 'the user able to add phone & service plan bundle to cart'
        def form = servicePlan.getAddBundleToCartForm()
        browser.submit(form) == AddExtrasPage.class
    }

    def 'then user lands on extras page'() {
        when: 'The user sees Extras page'
        AddExtrasPage page = browser.open(AddExtrasPage.class, false)

        then: 'page should contain valid addon data'
        AddonItem item = page.getAddon()
        item.addonTitle == AddonItem.DEFAULT_ADDLINE_ADDON_TITLE
        item.addonCostValue == AddonItem.DEFAULT_ADDLINE_ADDON_COST

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
        CheckoutPage page = browser.open(CheckoutPage.class, false)

        then: 'page should contain correct personal information'
        page.userName == addLineUser.name
        page.userEmail == addLineUser.email
        page.userPhone == addLineUser.phone
        page.userAddress == addLineUser.address

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
        Object json = page.requestPin(browser, addLineUser.phone)

        then: 'PIN sent successfully'
        json.status == 'SendingPinSuccess'

        and: 'user sent Pin for validation'
        page.validatePin(browser)


        then: 'user proceeds to payment'
        page.personalInfoRequest(browser, addLineUser)

    }

    def 'then user lands at add line payment page'() {
        when: 'Payment page opens'
        InitializePage page = browser.open(InitializePage.class, false)

        then: 'hidden initialize page loading'
        InitializeForm payloadForm = page.getPayload()

        when: 'hidden initialize page processed successfully'
        String response = browser.submitInitialize(payloadForm)

        then: 'server redirects to correct page'
        response.contains("/payment")

        when: 'user finally lands on payment page'
        PaymentPage paymentPage = browser.open(PaymentPage.class, false)

        then: 'page contains correct information'
        paymentPage.getPayTotalValue() == E2ETestPhone.AddLineFlowPhone.AddLineServicePlan.HANDSET_COST

        when: 'iframe loads'
        PaymentFrame frame = browser.open(PaymentFrame.class, true)

        then: 'iframe loaded correctly'
        frame.checkPaymentForm()

        when: 'the user submits payment details form'
        PaymentDetailsForm form = PaymentDetailsForm.getBuilder()
            .cardSecurityCode(addLineUser.creditCard.securityCode)
            .creditCardNumber(addLineUser.creditCard.cardNumber)
            .creditCardType(addLineUser.creditCard.cardType)
            .csrfToken(frame.getToken().getValue())
            .expirationMonth(addLineUser.creditCard.cardExpireMonth)
            .expirationYear(addLineUser.creditCard.cardExpireYear)
            .nameOnCard(addLineUser.creditCard.nameOnCard)
            .build()
        PaymentDetailsResponseForm cardDetailsResponse = browser.submitCardDetails(form)

        then: 'response contains success form'
        cardDetailsResponse.checkFormAction()

        when: ''
        WebSecurePage securePage = browser.submitTccThreeDS(cardDetailsResponse)

        then: ''

    }
}
