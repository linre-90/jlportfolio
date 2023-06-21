package com.juholindemark.jlportfolio

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.juholindemark.jlportfolio.service.AccountService
import com.juholindemark.jlportfolio.service.AccountServiceImpl


/**
 * User login state
 * */
class UserViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val accountService: AccountService
    ): ViewModel() {
    val signedIn = savedStateHandle.getStateFlow("signedin", false)
    val errorMsg = savedStateHandle.getStateFlow("loginerror", "")

    /**
     * Log user in
     * */
    fun logIn(username: String, password: String){
        accountService.login(username, password ){ error ->
            if(error != null){
                savedStateHandle["loginerror"] = error.message.toString()
            }else{
                savedStateHandle["signedin"] = true
            }
        }
    }

    /**
     * Call logout service and log user out
     * */
    fun logout(){
        accountService.logout { error ->
            if(error != null){
                Log.d("DEV", error.message.toString())
            }
        }
        savedStateHandle["signedin"] = false
    }

    /**
     * Clears the login related stored error message.
     * */
    fun clearErrorMessage(){
        savedStateHandle["loginerror"] = ""
    }

    /**
     * Fucktory to initialize view model.
     * */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // Savedstate
                val savedStateHandle = createSavedStateHandle()
                // AccountServiceImpl used through AccountService interface
                val myAccountService = AccountServiceImpl()
                UserViewModel(
                    accountService = myAccountService,
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }


}