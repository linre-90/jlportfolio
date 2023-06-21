package com.juholindemark.jlportfolio.service

/**
 * User account functionality interface.
 * */
interface AccountService {
    /**
     * Used to log user in
     * */
    fun login(username:String, password: String, onResult: (Throwable?) -> Unit)

    /**
     * Used to log user out
     * */
    fun logout(onResult: (Throwable?) -> Unit)
}