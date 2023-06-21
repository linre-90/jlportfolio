package com.juholindemark.jlportfolio

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginUi(userViewModel: UserViewModel){
    // <Component states>
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    // Possible error message from false login info
    val loginErrorMessage by userViewModel.errorMsg.collectAsState()
    // Delay to clear loginErrorMessage
    var messageDelay by remember { mutableStateOf(5) }
    // </Component states>

    // UI layout
    Column(
       //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top= 40.dp)
    ) {
        Text(
            text = "JL Portfolio",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 60.dp)
        )
        // Email input
        OutlinedTextField(
            value = userName,
            onValueChange = { newText ->
                userName = newText
            },
            label = {Text(text = "Email")},
            placeholder = { Text(text = "Your Email")},
            leadingIcon = {Icon(imageVector = Icons.Default.Email, contentDescription = "Email icon")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )
        // Password input
        OutlinedTextField(
            value = password,
            onValueChange = { newText ->
                password = newText
            },
            label = {Text(text = "Password")},
            placeholder = { Text(text = "Your Password")},
            leadingIcon = {Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock icon")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (showPassword)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Please provide localized description for accessibility services
                val description = if (showPassword) "Hide password" else "Show password"

                IconButton(onClick = {showPassword = !showPassword}){
                    Icon(imageVector  = image, description)
                }
            }
        )
        // Show error message with clearing timer
        if(loginErrorMessage.isNotEmpty()){
            Text(
                text = loginErrorMessage,
                color = Color.Red,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                textAlign = TextAlign.Center
            )
            // Error message clearing coroutine
            LaunchedEffect(Unit){
                while (messageDelay > 0){
                    delay(1000L)
                    messageDelay -= 1
                }
                userViewModel.clearErrorMessage()
                messageDelay = 5
            }
        }
        // Login button
        Button(
            onClick = { userViewModel.logIn(userName, password) },
            modifier = Modifier.padding(top = 20.dp)) {
            Text(text = "Login")
        }
    }
}