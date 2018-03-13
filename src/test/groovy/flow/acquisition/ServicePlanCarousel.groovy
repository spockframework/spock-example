package flow.acquisition

import org.jsoup.nodes.Element

/**
 * This page object is used to interact with the service plan carousel on Phone details page.
 */
final class ServicePlanCarousel extends ElementWrapper {

    ServicePlanCarousel(Element element) {
        super(element)
    }

    /**
     * Finds the service plans that are shown on the carousel.
     * @return
     */
    List<CarouselItem> getListItems() {
        return element.select('.item').collect {new CarouselItem(it)}
    }
}

