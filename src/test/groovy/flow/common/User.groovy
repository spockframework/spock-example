package flow.common
/**
 * Class represents user which can log in to system
 */
class User {

    private String name
    private String email
    private String password
    private String phone
    private String address
    private String accountNumber
    private String msisdn
    private CreditCard creditCard

    CreditCard getCreditCard() {
        return creditCard
    }

    void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    String getEmail() {
        return email
    }

    void setEmail(String email) {
        this.email = email
    }

    String getPassword() {
        return password
    }

    void setPassword(String password) {
        this.password = password
    }

    String getPhone() {
        return phone
    }

    void setPhone(String phone) {
        this.phone = phone
    }

    String getAddress() {
        return address
    }

    void setAddress(String address) {
        this.address = address
    }

    String getAccountNumber() {
        return accountNumber
    }

    void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber
    }

    String getMsisdn() {
        return msisdn
    }

    void setMsisdn(String msisdn) {
        this.msisdn = msisdn
    }
}
