package com.juholindemark.jlportfolio.service

import android.util.Log
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class MessageServiceImpl: MessageService {

    private val scope = CoroutineScope(Job() + Dispatchers.Default)

    override fun deleteEntry(id: String, onResult: (Throwable?) -> Unit) {
        val database = Firebase.database.reference
        database.child("contacts/$id").removeValue().addOnSuccessListener {
            onResult(null)
        }.addOnFailureListener{
            onResult(it)
        }
    }

    override fun setRead(id: String, messageCopy: Message, onResult: (Throwable?) -> Unit) {
        val database = Firebase.database.reference
        messageCopy.read = !messageCopy.read
        database.child("contacts/$id").setValue(messageCopy.toMap()).addOnSuccessListener {
            onResult(null)
        }.addOnFailureListener { onResult(it) }
    }

    override fun fetchMessages(onResult: (error: Throwable?, data: MutableMap<String, Message>?) -> Unit){
        val database = Firebase.database.reference
        val dataMap: MutableMap<String, Message> = mutableMapOf()
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