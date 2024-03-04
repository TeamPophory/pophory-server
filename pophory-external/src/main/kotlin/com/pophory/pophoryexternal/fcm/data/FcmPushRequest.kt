package com.pophory.pophoryexternal.fcm.data

data class FcmPushRequest(
    val validateOnly: Boolean = false,
    val message: Message
) {
}