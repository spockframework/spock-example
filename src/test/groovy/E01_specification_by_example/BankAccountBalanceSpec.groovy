package E01_specification_by_example

import spock.lang.Specification

/**
 * @author varsum
 */
class BankAccountBalanceSpec extends Specification{
    def "Bank account balance is updated after bank account is credited" () {
        given: "an empty bank account"

        when: "the account is credited \$10"

        then: "the account's balance is \$10"
    }
}
