package flow.common

import org.jsoup.nodes.Document

/**
 * This page object represents the Phone details page.
 */
class PhoneDetailsPage extends Page {

    public static final String PRODUCT_HERO_SECTION_ID = "#productHeroSection"
    public static final String PRODUCT_NAME_SPEC_CLASS = ".ee-product-name-spec"
    public static final String SERVICE_PLAN_CAROUSEL = '.ee-plans-carousel'

    protected PhoneDetailsPage(Document page) {
        super(page)
    }

    /**
     * Returns the title portion of the name of shown phone.
     * @return
     */
    String getPhoneTitle() {
        return element.select("${PRODUCT_HERO_SECTION_ID} ${PRODUCT_NAME_SPEC_CLASS} .ee-product-title").first().text()
    }

    /**
     * Returns the capacity portion of the name of shown phone.
     * @return
     */
    String getPhoneCapacity() {
        def capacityElem = element.select("${PRODUCT_HERO_SECTION_ID} ${PRODUCT_NAME_SPEC_CLASS} .ee-product-spec-capacity").first()
        return capacityElem.text()
    }

    /**
     * Returns the colour portion of the name of shown phone.
     * @return
     */
    String getPhoneColour() {
        def colourElem = element.select("${PRODUCT_HERO_SECTION_ID} ${PRODUCT_NAME_SPEC_CLASS} .ee-product-spec-colour").first()
        return colourElem.text()
    }

    /**
     * Returns the service plan carousel
     * @return
     */
    ServicePlanCarousel getServicePlanCarousel() {
        // duplication accepted in order to leverage power assertion statements
        assert !element.select(SERVICE_PLAN_CAROUSEL).isEmpty()
        def carouselElem = element.select(SERVICE_PLAN_CAROUSEL)
        return new ServicePlanCarousel(carouselElem.first())
    }
}
