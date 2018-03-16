package flow.addline

import flow.acquisition.Browser
import flow.acquisition.CarouselItem
import flow.acquisition.E2ETestPhone
import flow.acquisition.EndToEndTest
import flow.acquisition.FormWrapper
import flow.acquisition.Gallery
import flow.acquisition.GalleryItem
import flow.acquisition.PhoneDetailsPage
import flow.acquisition.ServicePlanCarousel
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
        PersonalizedHomePage personalizedHomePage = browser.open(PersonalizedHomePage.class)
        AddLineNavigationBar navBar = personalizedHomePage.getBanner()

        then: 'the navigation bar should contain link to Pay monthly phones page'
        navBar.containsLink(Browser.getPagePath(AddPayMonthlyPhonesPage.class))
    }

    def 'then user goes to add line gallery page'() {
        when: 'The user opens Pay monthly phones page'
        AddPayMonthlyPhonesPage page = browser.open(AddPayMonthlyPhonesPage.class)
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
        PhoneDetailsPage page = browser.open(PhoneDetailsPage.class,
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
        AddExtrasPage page = browser.open(AddExtrasPage.class)

        then: 'page should contain valid addon data'
        AddonItem item = page.getAddon()
        item.addonTitle == AddonItem.DEFAULT_ADDLINE_ADDON_TITLE
        item.addonCostValue == AddonItem.DEFAULT_ADDLINE_ADDON_COST

        and: 'and correct basket information'
        FlowUtils.checkAddLineUserBasket(page.basket)
    }

    def 'then user goes to checkout page'() {
        when: 'Checkout page opens'
        CheckoutPage page = browser.open(CheckoutPage.class)

        then: 'page should contain correct personal information'
        page.userName == addLineUser.name
        page.userEmail == addLineUser.email
        page.userPhone == addLineUser.phone
        page.userAddress == addLineUser.address

        and: 'and correct basket information'
        FlowUtils.checkAddLineUserBasket(page.basket)

        when: 'the user submit pin validation form'
        boolean sendPinResult = browser.doSendPinRequest(page)
        boolean validatePinResult = browser.doValidatePinRequest(page)
        boolean sendPersonalInfoResult = browser.doPersonalInfoRequest(page, addLineUser)

        then: 'correct responses appear on page'
        sendPinResult
        validatePinResult
        sendPersonalInfoResult

    }

    def 'then user lands at add line payment page'() {
        when: 'Payment page opens'
        InitializePage page = browser.open(InitializePage.class)

        then: 'hidden initialize page loading'
        FormWrapper payloadForm = page.getPayload()

        when: 'hidden initialize page processed successfully'
        String response = browser.submitTCC(payloadForm)

        then: 'server redirects to correct page'
        response.contains("/payment")

        when: 'user finally lands on payment page'
        PaymentPage paymentPage = browser.open(PaymentPage.class)

        then: 'page contains correct information'
        paymentPage.getPayTotalValue() == E2ETestPhone.AddLineFlowPhone.AddLineServicePlan.HANDSET_COST

        when: 'iframe loads'
        PaymentFrame frame = browser.doIframeRequest()

        then: 'iframe loaded correctly'
        frame.checkPaymentForm()

        when: 'the user submits payment details form'
        def finalResponse = browser.doCardDetailsRequest(frame, addLineUser)

        then: 'user lands to confirmation page'
        browser.submit(finalResponse) == WebSecurePage.class

    }
}
