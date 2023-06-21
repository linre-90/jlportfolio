package com.juholindemark.jlportfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.juholindemark.jlportfolio.ui.theme.JlportfolioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JlportfolioTheme {
                val userViewModel: UserViewModel = viewModel( factory = UserViewModel.Factory)
                val messageViewModel: MessageViewModel = viewModel(factory = MessageViewModel.Factory )
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WelcomeView(userViewModel = userViewModel, messageViewModel = messageViewModel)
                }
            }
        }
    }
}
