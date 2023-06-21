package com.juholindemark.jlportfolio.service

/**
 * Message service interface.
 * */
interface MessageService {
    /** Delete entry from database. */
    fun deleteEntry(id: String, onResult: (Throwable?) -> Unit);

    /** Mark contact as read. */
    fun setRead(boolean: Boolean, onResult: (Throwable?) -> Unit);

    /** fetch message */
    fun fetchMessages(onResult: (error: Throwable?, data: MutableMap<String, Message>?) ->Unit)

}