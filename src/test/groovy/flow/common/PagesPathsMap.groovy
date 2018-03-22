package flow.common

import flow.acquisition.AquisitionPayMonthlyPhonesPage
import flow.acquisition.CartPage
import flow.acquisition.DeliveryPage
import flow.acquisition.HomePage
import flow.addline.AddLineCheckoutPage
import flow.addline.AddLineExtrasPage
import flow.addline.AddLineInitPage
import flow.addline.AddLineOrderConfirmationPage
import flow.addline.AddLinePayMonthlyPhonesPage
import flow.addline.AddLinePaymentPage
import flow.addline.AddLinePersonalizedHomePage
import flow.addline.AddLineWebSecurePage
import flow.upgrade.RecommendationsPage
import flow.upgrade.UpgradeAccessoriesPage
import flow.upgrade.UpgradeCheckoutPage
import flow.upgrade.UpgradeExtrasPage
import flow.upgrade.UpgradeInitPage
import flow.upgrade.UpgradeOrderConfirmationPage
import flow.upgrade.UpgradePayMonthlyPhonesPage
import flow.upgrade.UpgradePaymentPage
import flow.upgrade.UpgradePersonalizedHomePage
import flow.upgrade.UpgradeWebSecurePage

/**
 * This class contains full list of pages mapping
 */
class PagesPathsMap {

    static final pathsMap = [
            //Acquisition flow
            (HomePage.class)                      : '/',
            (AquisitionPayMonthlyPhonesPage.class): '/mobile-phones/pay-monthly/gallery?search=:best-sellers',
            (PhoneDetailsPage.class)              : '/$categoryCode/$seoBundleType/$prettyId/details',
            (CartPage.class)                      : '/cart',
            (DeliveryPage.class)                  : '/delivery',
            //AddLine flow
            (AddLinePersonalizedHomePage.class)   : '/auth/my-shop',
            (AddLinePayMonthlyPhonesPage.class)   : '/auth/mobile-phones/add-pay-monthly/gallery',
            (AddLineExtrasPage.class)             : '/addExtras',
            (AddLineCheckoutPage.class)           : '/addCheckout',
            (AddLineInitPage.class)               : '/addCheckout/payment',
            (AddLinePaymentPage.class)            : '/addCheckout/payment',
            (AddLineWebSecurePage.class)          : '/addCheckout/tcc3ds',
            (AddLineOrderConfirmationPage.class)  : '/addConfirmation',
            //Upgrade flow
            (UpgradePersonalizedHomePage.class)   : '/auth/my-shop',
            (RecommendationsPage.class)           : '/auth/upgrade/recommendations',
            (UpgradeAccessoriesPage.class)        : '/upgradeAccessories',
            (UpgradeExtrasPage.class)             : '/upgradeExtras',
            (UpgradeCheckoutPage.class)           : '/upgradeCheckout',
            (UpgradeInitPage.class)               : '/upgradeCheckout/payment',
            (UpgradePaymentPage.class)            : '/upgradeCheckout/payment',
            (UpgradeWebSecurePage.class)          : '/upgradeCheckout/tcc3ds',
            (UpgradeOrderConfirmationPage.class)  : '/upgradeConfirmation',
            (UpgradePayMonthlyPhonesPage.class)   : '/auth/mobile-phones/upg-pay-monthly/gallery',
            //common
            (PaymentFrame.class)                  : '/TCCDTP/showcardform',
            (WebSecurePageFrame.class)            : '/upgradeCheckout/threeDSHostedPage',
            (WebSecurePageSubmitFrame.class)      : '/mock-iif/tds_acs',
            (ThreeDSBreakoutPage.class)           : '/upgradeCheckout/threeDSBreakout'
    ]
}
