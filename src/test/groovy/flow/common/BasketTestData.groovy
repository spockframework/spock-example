package flow.common

import org.apache.commons.lang3.builder.ToStringBuilder

/**
 * Created by Artsiom_Janchuryn on 3/16/2018.
 */
class BasketTestData {
    private String phoneCapacity

    private String phoneColour

    static class Builder {
        private String phoneCapacity
        private String phoneColour
        private Money payToday
        private Money monthlyCost

        def phoneCapacity(String phoneCapacity) {
            this.phoneCapacity = phoneCapacity
            return this
        }

        def phoneColour(String phoneColour) {
            this.phoneColour = phoneColour
            return this
        }

        def payToday(Money payToday) {
            this.payToday = payToday
            return this
        }

        def monthlyCost(Money monthlyCost) {
            this.monthlyCost = monthlyCost
            return this
        }

        BasketTestData build() {
            def result = new BasketTestData()
            result.phoneCapacity = phoneCapacity
            result.phoneColour = phoneColour
            result.payToday = payToday
            result.monthlyCost = monthlyCost
            return result
        }
    }

    private Money payToday
    private Money monthlyCost
    static Builder getBuilder() {
        return new Builder()
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        BasketTestData that = (BasketTestData) o

        if (monthlyCost != that.monthlyCost) return false
        if (payToday != that.payToday) return false
        if (phoneCapacity != that.phoneCapacity) return false
        if (phoneColour != that.phoneColour) return false

        return true
    }

    int hashCode() {
        int result
        result = (phoneCapacity != null ? phoneCapacity.hashCode() : 0)
        result = 31 * result + (phoneColour != null ? phoneColour.hashCode() : 0)
        result = 31 * result + (payToday != null ? payToday.hashCode() : 0)
        result = 31 * result + (monthlyCost != null ? monthlyCost.hashCode() : 0)
        return result
    }

    @Override
    String toString() {
        return new ToStringBuilder(this)
                .append("phoneCapacity", phoneCapacity)
                .append("phoneColour", phoneColour)
                .append("payToday", payToday)
                .append("monthlyCost", monthlyCost)
                .toString()
    }
}

