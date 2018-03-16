package flow.common
/**
 * This interface provide behavior for getting CSRF token from page
 */
interface CSRFTokenHolder {
    CSRFToken getToken()

    CSRFToken getTokenFromForm()
}