package com.juholindemark.jlportfolio.service

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

/**
 * Message data class that service provides for view model.
 * */
@IgnoreExtraProperties
data class Message(
    var headline:String,
    var mail: String,
    var message: String,
    var name: String,
    var read: Boolean,
    var special: String? = "",
    var timestamp: String
){
    constructor(): this("", "", "", "", false,"", "")
    constructor(message: Message) : this() {
        headline = message.headline
        mail = message.mail
        this.message = message.message
        name = message.name
        read = message.read
        special = message.special
        timestamp = message.timestamp
    }

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "headline" to headline,
            "mail" to mail,
            "message" to message,
            "name" to name,
            "read" to read,
            "special" to special,
            "timestamp" to timestamp
        )
    }
}
