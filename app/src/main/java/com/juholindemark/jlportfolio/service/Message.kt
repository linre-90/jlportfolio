package com.juholindemark.jlportfolio.service

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
}
