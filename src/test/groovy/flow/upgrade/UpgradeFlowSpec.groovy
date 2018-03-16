package flow.upgrade

import flow.common.Browser
import flow.common.EndToEndTest
import org.junit.experimental.categories.Category
import spock.lang.Shared
import spock.lang.Specification

/**
 * Multi step test which covers "Add Line" scenario
 */
@Category(EndToEndTest.class)
class UpgradeFlowSpec extends Specification{

    @Shared
    Browser browser = new Browser()

}
