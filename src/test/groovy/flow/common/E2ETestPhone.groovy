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
}
