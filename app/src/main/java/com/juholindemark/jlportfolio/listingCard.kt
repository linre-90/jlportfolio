package com.juholindemark.jlportfolio

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.MailTo
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.juholindemark.jlportfolio.service.Message
import com.juholindemark.jlportfolio.util.OutputFilter

/**
 * every message renders this card.
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingCard(message: Message, key:String, messageViewModel: MessageViewModel){
    val outputFilter = OutputFilter()
    var expanded by remember { mutableStateOf(false) }
    val contx = LocalContext.current;
    Card(
        onClick = { expanded = !expanded },
        modifier = Modifier.padding(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ){
        Column( modifier = Modifier.padding(10.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = outputFilter.filterText(message.headline), style = MaterialTheme.typography.headlineSmall  )
                IconButton(onClick = { Log.d("CARD", "Set read true") }, modifier = Modifier.border(BorderStroke(1.dp, Color.Gray), shape = CircleShape), ) {
                    if(message.read){
                        Icon(Icons.Outlined.AutoStories, contentDescription = "read", tint = Color.Green)
                    }else{
                        Icon(Icons.Outlined.Book, contentDescription = "read", tint = Color.Red)
                    }
                }
            }
            Text(text = "From: ${outputFilter.filterText(message.name)}")
            Text(text = outputFilter.filterTimeStamp(message.timestamp), fontStyle = FontStyle.Italic)
            if(expanded){
                Text(text = outputFilter.filterText(message.message))
                OutlinedButton(onClick = { openMailApp(contx, outputFilter.filterEmail(message.mail))  }) {
                    Text(text = outputFilter.filterEmail(message.mail))
                }
                Button(onClick = { Log.d("CARD", "remove item") }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                    Text(text = "Delete")
                }
            }
        }
    }
}

/**
 * Open email app to send message.
 * */
private fun openMailApp(contx: Context,emailAdd: String){
    try {
        var intent: Intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:${emailAdd}")
        startActivity(contx, intent, null);
    }catch (e: ActivityNotFoundException){
        Log.d("Open Mail", "Error opening mail.")
    }
}