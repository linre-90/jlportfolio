package com.juholindemark.jlportfolio.service

import android.util.Log
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine

class MessageServiceImpl: MessageService {

    private val scope = CoroutineScope(Job() + Dispatchers.Default)

    override fun deleteEntry(id: String, onResult: (Throwable?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun setRead(boolean: Boolean, onResult: (Throwable?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun fetchMessages(onResult: (error: Throwable?, data: MutableMap<String, Message>?) -> Unit){
        val database = Firebase.database.reference
        var dataMap: MutableMap<String, Message> = mutableMapOf()
        database.child("contacts").get().addOnSuccessListener { it ->
            it.children.forEach { dataSnap ->
                val msg = dataSnap.getValue<Message>();
                if (msg != null) {
                    dataMap[dataSnap.key.toString()] = msg;
                }
            }
            onResult(null, dataMap);

        }.addOnFailureListener{
            onResult(Throwable( it.message.toString()), mutableMapOf<String, Message>());
        }
    }
}