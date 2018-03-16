package flow.addline

import flow.acquisition.E2ETestPhone
import org.jsoup.nodes.Element

/**
 * Class provides some util methods
 */
class FlowUtils {

    /**
     * Method checks correctness of basket information
     * @param basket element from page
     * @return boolean result
     */
    static boolean checkAddLineUserBasket(Basket basket){
        return basket.phoneTitle == E2ETestPhone.AddLineFlowPhone.TITLE &&
        basket.phoneCapacity == E2ETestPhone.AddLineFlowPhone.CAPACITY &&
        basket.phoneColour == E2ETestPhone.AddLineFlowPhone.COLOUR &&
        basket.payTodayValue == E2ETestPhone.AddLineFlowPhone.AddLineServicePlan.HANDSET_COST &&
        basket.monthlyCostValue == E2ETestPhone.AddLineFlowPhone.AddLineServicePlan.MONTHLY_COST
    }

    /**
     * Method build new user instance for Add line flow
     * @return User
     */
    static User createAddLineUser() {
        User user = new User()
        user.setName('John Green')
        user.setEmail('eeallstreams@yahoo.com')
        user.setPhone('07873 099202')
        user.setAddress('Integration, Everything Everywhere Ltd, The Road, 12a, LongStreet, London, Hertfordshire, A109BW')
        user.setAccountNumber('150077829')
        user.setMsisdn('447873099202')
        user.setPassword(' ')

        CreditCard card = new CreditCard()
        card.setCardType('VS')
        card.setNameOnCard('TestUser')
        card.setCardNumber('4012888888881881')
        card.setCardExpireMonth('06')
        card.setCardExpireYear('2019')
        card.setAccountNumber('****4444')
        card.setSortCode('444444')
        card.setSecurityCode('111')
        user.setCreditCard(card)

        return user
    }
}
