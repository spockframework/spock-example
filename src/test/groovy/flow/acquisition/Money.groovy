package flow.acquisition

/**
 * Represents money. Contains <code>value</code> and <code>symbol</code> parts.
 */
class Money {
    private BigDecimal value
    private String symbol

    protected Money(BigDecimal value, String symbol) {
        this.value = value
        this.symbol = symbol
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Money money = (Money) o

        if (symbol != money.symbol) return false
        if (value != money.value) return false

        return true
    }

    int hashCode() {
        int result
        result = (value != null ? value.hashCode() : 0)
        result = 31 * result + (symbol != null ? symbol.hashCode() : 0)
        return result
    }


    @Override
    public String toString() {
        return "$symbol$value"
    }
}
