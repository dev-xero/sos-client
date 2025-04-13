package group.one.sos.domain.models

data class ContactModel(
    val id: String,
    val displayName: String,
    val phoneNumber: String,
    val photoURI: String?,
    val photoThumbURI: String?
)
