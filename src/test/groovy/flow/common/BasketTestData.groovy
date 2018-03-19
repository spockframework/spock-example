package flow.common

import org.apache.commons.lang3.builder.ToStringBuilder

/**
 * This object represents concrete user basket with builder
 */
class BasketTestData {

    private String title
    private String phoneCapacity
    private Money payToday
    private Money monthlyCost
    private String phoneColour

    static class Builder {
        private String title
        private String phoneCapacity
        private String phoneColour
        private Money payToday
        private Money monthlyCost

        def title(String title) {
            this.title = title
            return this
        }

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
            result.title = title
            result.phoneCapacity = phoneCapacity
            result.phoneColour = phoneColour
            result.payToday = payToday
            result.monthlyCost = monthlyCost
            return result
        }
    }

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
        if (title != that.title) return false

        return true
    }

    int hashCode() {
        int result
        result = (title != null ? title.hashCode() : 0)
        result = 31 * result + (phoneCapacity != null ? phoneCapacity.hashCode() : 0)
        result = 31 * result + (payToday != null ? payToday.hashCode() : 0)
        result = 31 * result + (monthlyCost != null ? monthlyCost.hashCode() : 0)
        result = 31 * result + (phoneColour != null ? phoneColour.hashCode() : 0)
        return result
    }

    @Override
    String toString() {
        return new ToStringBuilder(this)
                .append("title", title)
                .append("phoneCapacity", phoneCapacity)
                .append("payToday", payToday)
                .append("monthlyCost", monthlyCost)
                .append("phoneColour", phoneColour)
                .toString()
    }
}

