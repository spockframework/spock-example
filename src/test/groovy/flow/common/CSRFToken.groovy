package flow.common

/**
 * This object represents CSFR token instance
 */
class CSRFToken {
    private String name
    private String value

    CSRFToken(String name, String value) {
        this.name = name
        this.value = value
    }

    String getName() {
        return this.name
    }

    String getValue() {
        return this.value
    }


    @Override
    String toString() {
        return "CSRFToken{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}'
    }
}
