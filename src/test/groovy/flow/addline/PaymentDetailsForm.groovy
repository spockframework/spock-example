package flow.addline

import org.apache.commons.lang3.builder.ToStringBuilder

/**
 * This object represents payment form which is filled with user data
 */
class PaymentDetailsForm {

    private String cardSecurityCode
    private String Continue = 'Continue'
    private String creditCardNumber
    private String creditCardType
    private String csrfToken
    private String expirationMonth
    private String expirationYear
    private String nameOnCard

    static class Builder {
        static String cardSecurityCode
        static String creditCardNumber
        static String creditCardType
        static String csrfToken
        static String expirationMonth
        static String expirationYear
        static String nameOnCard

        def cardSecurityCode(String cardSecurityCode) {
            this.cardSecurityCode = cardSecurityCode
            return this
        }

        def creditCardNumber(String creditCardNumber) {
            this.creditCardNumber = creditCardNumber
            return this
        }

        def creditCardType(String creditCardType) {
            this.creditCardType = creditCardType
            return this
        }

        def csrfToken(String csrfToken) {
            this.csrfToken = csrfToken
            return this
        }

        def expirationMonth(String expirationMonth) {
            this.expirationMonth = expirationMonth
            return this
        }

        def expirationYear(String expirationYear) {
            this.expirationYear = expirationYear
            return this
        }

        def nameOnCard(String nameOnCard) {
            this.nameOnCard = nameOnCard
            return this
        }

        PaymentDetailsForm build() {
            def result = new PaymentDetailsForm()
            result.cardSecurityCode = cardSecurityCode
            result.creditCardNumber = creditCardNumber
            result.creditCardType = creditCardType
            result.csrfToken = csrfToken
            result.expirationMonth = expirationMonth
            result.expirationYear = expirationYear
            result.nameOnCard = nameOnCard

            return result
        }
    }

    static Builder getBuilder() {
        return new Builder()
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        PaymentDetailsForm that = (PaymentDetailsForm) o

        if (Continue != that.Continue) return false
        if (cardSecurityCode != that.cardSecurityCode) return false
        if (creditCardNumber != that.creditCardNumber) return false
        if (creditCardType != that.creditCardType) return false
        if (csrfToken != that.csrfToken) return false
        if (expirationMonth != that.expirationMonth) return false
        if (expirationYear != that.expirationYear) return false
        if (nameOnCard != that.nameOnCard) return false

        return true
    }

    int hashCode() {
        int result
        result = (cardSecurityCode != null ? cardSecurityCode.hashCode() : 0)
        result = 31 * result + (Continue != null ? Continue.hashCode() : 0)
        result = 31 * result + (creditCardNumber != null ? creditCardNumber.hashCode() : 0)
        result = 31 * result + (creditCardType != null ? creditCardType.hashCode() : 0)
        result = 31 * result + (csrfToken != null ? csrfToken.hashCode() : 0)
        result = 31 * result + (expirationMonth != null ? expirationMonth.hashCode() : 0)
        result = 31 * result + (expirationYear != null ? expirationYear.hashCode() : 0)
        result = 31 * result + (nameOnCard != null ? nameOnCard.hashCode() : 0)
        return result
    }

    @Override
    String toString() {
        return new ToStringBuilder(this)
                .append("cardSecurityCode", cardSecurityCode)
                .append("Continue", Continue)
                .append("creditCardNumber", creditCardNumber)
                .append("creditCardType", creditCardType)
                .append("csrfToken", csrfToken)
                .append("expirationMonth", expirationMonth)
                .append("expirationYear", expirationYear)
                .append("nameOnCard", nameOnCard)
                .toString()
    }
}
