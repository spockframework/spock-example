package flow.common

/**
 * Class represents user which can log in to system
 */
class E2ETestUser {
    static class AddLineFlowUser {
        static final String NAME = 'John Green'
        static final String HOLDER = 'John Johnsson'
        static final String EMAIL = 'eeallstreams@yahoo.com'
        static final String PASSWORD = ' '
        static final String PHONE = '07873 099202'
        static final String ADDRESS = 'Integration, Everything Everywhere Ltd, The Road, 12a, LongStreet, London, Hertfordshire, A109BW'
        static final String BILLING_ADDRESS = 'Everything Everywhere Ltd Integration 12a The Road LongStreet London Hertfordshire A10 9BW'
        static final String USER_ACCAUNT_NUMBER = '150077829'
        static final String MSISDN_NUMBER = '447873099202'

        static class AddLineCreditCard {
            static final String PAYMENT_METHOD = 'direct debit'
            static final String CARD_TYPE = 'VS'
            static final String CARD_TYPE_FULL = 'Visa'
            static final String NAME_ON_CARD = 'TestUser'
            static final String CARD_NUMBER = '4012888888881881'
            static final String CARD_EXPIRE_MONTH = '06'
            static final String CARD_EXPIRE_YEAR = '2019'
            static final String SECURITY_CODE =  '111'
            static final String CARD_ACCOUNT_NUMBER = '****4444'
            static final String SORT_CODE = '444444'
        }
    }

    static class UpgradeFlowUser {
        static final String NAME = 'John Green'
        static final String HOLDER = 'John Johnsson'
        static final String EMAIL = 'eeallstreams@yahoo.com'
        static final String PASSWORD = ' '
        static final String PHONE = '07876 922722'
        static final String ADDRESS = 'The Road, 12a, LongStreet, London, Hertfordshire, AL109BW'
        static final String BILLING_ADDRESS = '12a The Road LongStreet London Hertfordshire AL10 9BW'
        static final String USER_ACCAUNT_NUMBER = '47347473714'
        static final String MSISDN_NUMBER = '447876922722'

        static class UpgradeCreditCard {
            static final String PAYMENT_METHOD = 'direct debit'
            static final String CARD_TYPE = 'VS'
            static final String CARD_TYPE_FULL = 'Visa'
            static final String NAME_ON_CARD = 'TestUser'
            static final String CARD_NUMBER = '4012888888881881'
            static final String CARD_EXPIRE_MONTH = '06'
            static final String CARD_EXPIRE_YEAR = '2019'
            static final String SECURITY_CODE =  '111'
            static final String CARD_ACCOUNT_NUMBER = '****4444'
            static final String SORT_CODE = '444444'
        }
    }
}
