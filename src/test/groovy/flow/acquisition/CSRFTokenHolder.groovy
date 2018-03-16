package flow.acquisition

/**
 * This interface provide behavior for getting CSRF token from page
 */
interface CSRFTokenHolder {
    CSRFToken getToken()
}