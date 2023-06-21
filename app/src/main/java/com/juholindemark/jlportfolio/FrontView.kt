package com.juholindemark.jlportfolio

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

/**
 * Welcome view composes listing view or login in view. Checks user login state at the beginning
 * and renders either login ui or listing view.
 */
@Composable
fun WelcomeView(userViewModel: UserViewModel, messageViewModel: MessageViewModel){
    val userSignedIn by userViewModel.signedIn.collectAsState()

    if(userSignedIn){
        Listing(messageViewModel =  messageViewModel, userViewModel= userViewModel);
    }else{
        LoginUi(userViewModel = userViewModel)
    }

}