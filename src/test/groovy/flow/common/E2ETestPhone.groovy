package flow.common

/**
 * This constant class contains the test data that is required to write end-to-end tests for methods
 * that rely on Phone data.
 */
class E2ETestPhone {
    static class AcquisitionFlowPhone {
        static final String PRETTY_ID = 'samsung-galaxy-s4-black-mist'
        static final String SEO_BUNDLE_TYPE = 'pay-monthly'
        static final String CATEGORY = 'smartphones'
        static final String TITLE = 'Samsung Galaxy S4'
        static final String CAPACITY = '4GB'
        static final String COLOUR = 'Black Mist'
        static final Money MONTHLY_COST = MoneyBuilder.fromGBP().value(89.0).build()
        static final Money UPFRONT_COST = MoneyBuilder.fromGBP().value(19.99).build()

        static class ServicePlan {
            static final String DATA = '500MB'
            static final Money MONTHLY_COST = MoneyBuilder.fromGBP().value(35.0).build()
            static final Money HANDSET_COST = MoneyBuilder.fromGBP().value(269.99).build()
            static final String MINUTES = '350'
            static final String TEXTS = 'Unlimited'
        }
    }

    static class AddLineFlowPhone {
        static final String PRETTY_ID = 'samsung-galaxy-s4-black-mist'
        static final String SEO_BUNDLE_TYPE = 'add-pay-monthly'
        static final String CATEGORY = 'auth/smartphones'
        static final String TITLE = 'Samsung Galaxy S4'
        static final String CAPACITY = '4GB'
        static final String COLOUR = 'Black Mist'
        static final Money MONTHLY_COST = MoneyBuilder.fromGBP().value(41.0).build()
        static final Money UPFRONT_COST = MoneyBuilder.fromGBP().value(269.99).build()

        static class AddLineServicePlan {
            static final String PLAN_NAME = '4GEE+ Package Desc 24 month'
            static final String DATA = 'Unlimited'
            static final Money MONTHLY_COST = MoneyBuilder.fromGBP().value(50.0).build()
            static final Money HANDSET_COST = MoneyBuilder.fromGBP().value(269.99).build()
            static final String MINUTES = 'Unlimited'
            static final String TEXTS = 'Unlimited'
        }
    }

    static class UpgradeFlowPhone {
        static final String TITLE = 'Samsung Galaxy S4'
        static final String CAPACITY = '4GB'
        static final String COLOUR = 'Black Mist'

        static class UpgradeServicePlan {
            static final String PLAN_NAME = '4GEE+ Package Desc 24 month'
            static final String DATA = '4GB'
            static final Money MONTHLY_COST = MoneyBuilder.fromGBP().value(50.0).build()
            static final Money HANDSET_COST = MoneyBuilder.fromGBP().value(269.99).build()
            static final String MINUTES = '200'
            static final String TEXTS = '300'
        }
    }

    static class UpgradeFlowNonRecommendedPhone {
        static final String PRETTY_ID = 'nokia-lumia-920-black'
        static final String SEO_BUNDLE_TYPE = 'upg-pay-monthly'
        static final String CATEGORY = 'auth/smartphones'
        static final String TITLE = 'Nokia Lumia 920'
        static final String CAPACITY = '4GB'
        static final String COLOUR = 'Black'
        static final Money MONTHLY_COST = MoneyBuilder.fromGBP().value(35.0).build()
        static final Money UPFRONT_COST = MoneyBuilder.fromGBP().value(279.99).build()

        static class UpgradeNonRecommendedServicePlan {
            static final String PLAN_NAME = '4GEE+ Package Desc 24 month'
            static final String DATA = '8GB'
            static final Money MONTHLY_COST = MoneyBuilder.fromGBP().value(75.0).build()
            static final Money HANDSET_COST = MoneyBuilder.fromGBP().value(279.99).build()
            static final String MINUTES = '500'
            static final String TEXTS = '200'
        }
    }

    static class DefaultAddon {
        static final String DEFAULT_ADDON_TITLE = 'Get Apple Music free for six months'
        static final String DEFAULT_ADDON_COST = '£ 0.00 a month'
        static final String DEFAULT_ADDON_CODE = 'YMUSICB01'
    }

    static class DefaultAccessory {
        static final String DEFAULT_ACCESSORY_TITLE = 'Apple Watch Series 2'
        static final String DEFAULT_ACCESSORY_MONTHLY_COST = '£27.00'
        static final String DEFAULT_ACCESSORY_UPFRONT_COST = 'Now, £20.00'
    }
}
