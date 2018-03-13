package flow.acquisition

import org.apache.commons.lang3.builder.ToStringBuilder
import org.jsoup.nodes.Element

import java.text.DecimalFormat
import java.text.NumberFormat

/**
 * Provides a few convenient ways to instantiate {@link Money}
 */
class MoneyBuilder {
    public static final PATTERN = /(\p{Sc})(\d+\.?\d{0,2})/
    private String symbol
    private BigDecimal value

    /**
     * Create a builder that is initialized with the values parsed from given {@code text}
     * Searches for text like £35.00 to extract currency symbol and value
     * @param text
     * @return
     */
    static MoneyBuilder fromText(String text) {
        def builder = new MoneyBuilder()
        def matcher = (text =~ /$PATTERN/)

        builder.value = parseValue(matcher[0][2])
        builder.symbol = matcher[0][1]
        return builder
    }

    /**
     * Create a builder that is initialized with the values parsed from given {@code element}
     * Capable of extracting data from element having content like {@code <span>£</span>35.00}
     *
     * @param element
     * @return
     */
    static MoneyBuilder fromElement(Element element) {
        return fromText(element.text())
    }

    /**
     * Create a builder that is initialized with a symbol which corresponds to GBP currency code
     * @return
     */
    static MoneyBuilder fromGBP() {
        def builder = new MoneyBuilder()
        def symbol = Currency.getInstance("GBP").getSymbol(Locale.UK)
        builder.symbol = symbol
        return builder
    }

    private static BigDecimal parseValue(String valueAsString) {
        def format = (DecimalFormat) NumberFormat.getInstance(Locale.UK)
        format.setParseBigDecimal(true)
        return format.parse(valueAsString)
    }

    MoneyBuilder value(BigDecimal value) {
        this.value = value
        return this
    }

    Money build() {
        return new Money(value, symbol)
    }


    @Override
    String toString() {
        return new ToStringBuilder(this)
                .append("symbol", symbol)
                .append("value", value)
                .toString()
    }
}
