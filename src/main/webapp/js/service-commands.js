/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 2/27/14
 * Time: 11:43 AM
 * To change this template use File | Settings | File Templates.
 */

var SERVICE_COMMAND = {

    DATA_BUNDLE_PURCHASE: { name: "data-bundle-purchase",
                            confirmationMessageHeaderText: "Data Bundle Purchase Confirmation",
                            serviceCommandButtonText: "Buy Bundle",
                            serviceCommand: null,
                            isProductSelected:false,
                            isBeneficiaryMobileNumberSubmitted:false,
                            isSelectionConfirmed:false,
                            successMessage: "Bundle Purchase Successful",
                            failureMessage: "Bundle Purchase Failed"
                          },

    AIRTIME_TRANSFER:     { name: "airtime-transfer",
                            confirmationMessageHeaderText: "Airtime Transfer Confirmation",
                            serviceCommandButtonText: "Transfer",
                            serviceCommand: null,
                            successMessage: "Airtime Transfer Successful",
                            failureMessage: "Airtime Transfer Failed",
                            minimumAmount: "0.25",
                            maximumAmount: "100.00"
                          },

    VOUCHER_RECHARGE:     { name: "voucher-recharge",
                            confirmationMessageHeaderText: "Voucher Recharge Confirmation",
                            serviceCommandButtonText: "Recharge",
                            serviceCommand: null,
                            successMessage: "Voucher Recharge Successful",
                            failureMessage: "Voucher Recharge Failed"
                           },

    DEACTIVATE_USER:   { name: "deactivate-user",
        confirmationMessageHeaderText: "Delete Account Confirmation",
        serviceCommandButtonText: "Delete",
        serviceCommand: null,
        successMessage: "Account Deletion Successful",
        failureMessage: "Account Deletion Failed"
    },

    SET_USER_PASSWORD:   { name: "set-user-password",
        confirmationMessageHeaderText: "Password Change Confirmation",
        serviceCommandButtonText: "Change",
        serviceCommand: null,
        successMessage: "Password Change Successful",
        failureMessage: "Password Change Failed"
    },

    ACTIVATE_USER:   { name: "activate-user",
        confirmationMessageHeaderText: "Account Activation",
        serviceCommandButtonText: "Activate",
        serviceCommand: null,
        successMessage: "Account Activation Successful",
        failureMessage: "Account Activation Failed"
    }
};

var DIRECTION = {
    SEND :{}, RECEIVED:{}
}