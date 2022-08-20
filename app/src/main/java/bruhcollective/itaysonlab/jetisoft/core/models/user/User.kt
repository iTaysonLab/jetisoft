package bruhcollective.itaysonlab.jetisoft.core.models.user

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    val userId: String,
)

/*
{
    "userId": "01aa9ac0-ce3d-4062-9002-147e720966e7",
    "username": "iTaysonLab",
    "nameOnPlatform": "iTaysonLab",
    "accountIssues": null,
    "communicationOptIn": true,
    "communicationThirdPartyOptIn": false,
    "country": "UA",
    "dateCreated": "2017-10-01T14:53:17.5300000Z",
    "dateAnonymized": null,
    "dateOfBirth": "1977-10-01T00:00:00.0000000Z",
    "email": "itaysontesterlab@gmail.com",
    "firstName": null,
    "gender": null,
    "hasAcceptedLegalOptins": true,
    "lastName": null,
    "preferredLanguage": "uk",
    "status": {
        "autoGeneratedUsername": false,
        "dateOfBirthApproximated": true,
        "invalidEmail": false,
        "missingRequiredInformation": false,
        "pendingDeactivation": false,
        "targetDeactivationDate": null,
        "recoveringPassword": false,
        "passwordUpdateRequired": false,
        "reserved": false,
        "changeEmailPending": false,
        "inactiveAccount": false,
        "generalStatus": "activated",
        "suspiciousActivity": false,
        "locked": false,
        "minor": false,
        "testAccount": false,
        "phoneActivated": true,
        "phoneSanctioned": false
    },
    "phone": {
        "number": "*******26",
        "countryCallingCode": "380"
    },
    "accountType": "Ubisoft"
}
 */