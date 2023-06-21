package com.juholindemark.jlportfolio.service

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AccountServiceImpl: AccountService {
    override fun login(username: String, password: String, onResult: (Throwable?) -> Unit) {
        if(username.isEmpty() || password.isEmpty()){
            onResult(Throwable("Empty username or password."))
        }else{
            Firebase.auth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener{onResult(it.exception)}
        }
    }

    override fun logout(onResult: (Throwable?) -> Unit) {
        Firebase.auth.signOut()
    }
}