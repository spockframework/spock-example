package flow.addline

/**
 * This class represents user's credit card
 */
class CreditCard {

    String cardType
    String nameOnCard
    String cardNumber
    String cardExpireMonth
    String cardExpireYear
    String securityCode
    String accountNumber
    String sortCode

    String getSortCode() {
        return sortCode
    }

    void setSortCode(String sortCode) {
        this.sortCode = sortCode
    }

    String getAccountNumber() {
        return accountNumber
    }

    void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber
    }

    String getSecurityCode() {
        return securityCode
    }

    void setSecurityCode(String securityCode) {
        this.securityCode = securityCode
    }

    String getCardExpireMonth() {
        return cardExpireMonth
    }

    void setCardExpireMonth(String cardExpireMonth) {
        this.cardExpireMonth = cardExpireMonth
    }

    String getCardExpireYear() {
        return cardExpireYear
    }

    void setCardExpireYear(String cardExpireYear) {
        this.cardExpireYear = cardExpireYear
    }

    String getCardType() {
        return cardType
    }

    void setCardType(String cardType) {
        this.cardType = cardType
    }

    String getNameOnCard() {
        return nameOnCard
    }

    void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard
    }

    String getCardNumber() {
        return cardNumber
    }

    void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber
    }
}
