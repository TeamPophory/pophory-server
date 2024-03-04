package com.pophory.pophoryexternal.fcm

import com.pophory.pophoryexternal.fcm.data.FcmMessage
import com.pophory.pophoryexternal.fcm.data.FcmPushRequest
import com.pophory.pophoryexternal.fcm.data.Message
import com.pophory.pophoryexternal.fcm.data.Notification

class FcmService {

    fun sendFcm(
        fcmToken: String,
        message: FcmMessage
    ) {
        FcmPushRequest(
            validateOnly = false,
            message = Message(
                notification = Notification(
                    title = message.title,
                    body = message.body
                ),
                token = fcmToken
                )
            )
    }
}