package flow.acquisition

import flow.common.Browser
import flow.common.E2ETestPhone
import flow.common.EndToEndTest
import org.junit.experimental.categories.Category
import spock.lang.Shared
import spock.lang.Specification

/**
 * Multi step test which covers "acquisition" scenario
 */
@Category(EndToEndTest.class)
//@Stepwise
class AcquisitionFlowSpec extends Specification {

    @Shared
    Browser browser = new Browser()

    def 'An anonymous user starts at Home page'() {

        when: 'A user opens a Home page'
        HomePage homePage = browser.open(HomePage.class, false)
        NavigationBar navBar = homePage.getNavigationBar()

        then: 'the navigation bar should contain link to Pay monthly phones page'
        navBar.containsLink(Browser.getPagePath(PayMonthlyPhonesPage.class))
    }

    def 'then goes to Pay monthly phones page '() {
        when: 'A user opens Pay monthly phones page'
        PayMonthlyPhonesPage page = browser.open(PayMonthlyPhonesPage.class, false)
        Gallery gallery = page.getGallery()

        then: 'a gallery should contain product tile with specific SEO Id'
        GalleryItem galleryItem = gallery.getItemByPrettyId(E2ETestPhone.AcquisitionFlowPhone.PRETTY_ID)

        and: 'tile should display correct information'
        galleryItem.title == E2ETestPhone.AcquisitionFlowPhone.TITLE
        galleryItem.monthlyCost == E2ETestPhone.AcquisitionFlowPhone.MONTHLY_COST
        galleryItem.upfrontCost == E2ETestPhone.AcquisitionFlowPhone.UPFRONT_COST
    }

    def 'then goes to Phone details page and adds bundle to cart'() {
        when: 'A user opens Phone details page'
        PhoneDetailsPage page = browser.open(PhoneDetailsPage.class, false,
                [categoryCode : E2ETestPhone.AcquisitionFlowPhone.CATEGORY,
                 seoBundleType: E2ETestPhone.AcquisitionFlowPhone.SEO_BUNDLE_TYPE,
                 prettyId     : E2ETestPhone.AcquisitionFlowPhone.PRETTY_ID
                ])
        then: 'the page should display correct phone information'
        page.phoneTitle == E2ETestPhone.AcquisitionFlowPhone.TITLE
        page.phoneCapacity == E2ETestPhone.AcquisitionFlowPhone.CAPACITY
        page.phoneColour == E2ETestPhone.AcquisitionFlowPhone.COLOUR

        and: 'the page should display list of available plans'
        ServicePlanCarousel planCarousel = page.getServicePlanCarousel()
        List<CarouselItem> carouselItems = planCarousel.getListItems()
        carouselItems.size() == 5

        and: 'the correct service plan displayed as first in carousel'
        def servicePlan = carouselItems.first()
        servicePlan.data == E2ETestPhone.AcquisitionFlowPhone.ServicePlan.DATA
        servicePlan.monthlyCost == E2ETestPhone.AcquisitionFlowPhone.ServicePlan.MONTHLY_COST
        servicePlan.handsetCost == E2ETestPhone.AcquisitionFlowPhone.ServicePlan.HANDSET_COST
        servicePlan.hasMinutes(E2ETestPhone.AcquisitionFlowPhone.ServicePlan.MINUTES)
        servicePlan.hasTexts(E2ETestPhone.AcquisitionFlowPhone.ServicePlan.TEXTS)

        and: 'a user able to add phone & service plan bundle to cart'
        def form = servicePlan.getAddBundleToCartForm()
        browser.submit(form) == CartPage.class
    }

    def 'then user lands at cart page'() {
        when: 'A user sees recently added bundle at cart page'
        CartPage page = browser.open(CartPage.class, false)

        then: 'cart page should have correct total values'
        def cartTotal = page.getCartTotal()
        cartTotal.payToday == E2ETestPhone.AcquisitionFlowPhone.ServicePlan.HANDSET_COST
        cartTotal.payMonthly == E2ETestPhone.AcquisitionFlowPhone.ServicePlan.MONTHLY_COST
    }

    def 'then user choose delivery option'() {
        when: 'A user goes to Delivery page'
        DeliveryPage page = browser.open(DeliveryPage.class, false)
        /*Document result = browser.getHttpBuilder().get {
            request.uri = 'https://ee.local:9002/delivery'
        }*/
        //println page

        then: 'cart page should have correct total values'
        true
    }

}
