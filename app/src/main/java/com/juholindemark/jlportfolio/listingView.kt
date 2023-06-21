package com.juholindemark.jlportfolio


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.juholindemark.jlportfolio.util.OutputFilter

/**
 * Constructs scaffold container for ListingContent composable.
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Listing(messageViewModel: MessageViewModel, userViewModel:UserViewModel){
    var menuActive by remember { mutableStateOf(false) }
    // Constructs scaffold container for ListingContent composable.
    Scaffold(
        topBar = { TopAppBar(
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary, titleContentColor = Color.White
            ),
            title = {Text("JLPortfolio")},
            actions = {
                IconButton(onClick = { menuActive = true }) {
                    Icon(Icons.Default.Menu, contentDescription = "Open menu", tint = Color.White )
                }
                // Quick dropdown menu
                DropdownMenu(expanded = menuActive, onDismissRequest = { menuActive = false }){
                    DropdownMenuItem(text = { Text(text = "Logout") }, onClick = {
                        menuActive = false; userViewModel.logout()
                    })
                    DropdownMenuItem(text = { Text(text = "Refresh") }, onClick = {
                        menuActive = false; messageViewModel.fetchMessages()
                    })
                }
            }
        )},
    )
    {
        paddingValues -> ListingContent(messageViewModel = messageViewModel, paddingValues=paddingValues)
    }
}

/**
 * Builds actual message content "cards"
 * */
@Composable
fun ListingContent(messageViewModel: MessageViewModel, paddingValues: PaddingValues){
    val messages by messageViewModel.messages.collectAsState()

    // Perform initial update
    if(!messages.initialized){
        messageViewModel.fetchMessages();
    }

    // Display messages
    if (!messages.refreshRequested && messages.initialized) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()

        ){
            messages.uiMessages.forEach {
                ListingCard(message = it.value, key = it.key, messageViewModel = messageViewModel)
            }

        }
    // Reloading content
    } else {
        LoadingIconComponent(paddingValues = paddingValues)
    }

}