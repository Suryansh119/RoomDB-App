package com.example.s.presentation.screen

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.s.AppState
import kotlin.toString


@Composable
fun ContactFormUI(onEvent:()-> Unit={}, appState: AppState) {
    val context= LocalContext.current
    val launcher= rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            uri:Uri? ->
        if (uri!=null){
            val imageInputStream = context.contentResolver.openInputStream(uri)
            val imageByteArray=imageInputStream!!.readBytes()
            if (imageByteArray!=null)
                appState.image.value=imageByteArray
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Edit Contact Details", style = MaterialTheme.typography.labelLarge)

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                launcher.launch("image/*")
            }
        ) {
            Text("Add Image")
        }
        // Name Input Field
        Text(text = "Name:")
        TextField(
            value = appState.name.value,
            onValueChange = { appState.name.value = it },
            label = { Text("Enter Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Phone Number Input Field
        Text(text = "Phone Number:")
        TextField(
            value = appState.phoneNumber.value,
            onValueChange = { appState.phoneNumber.value = it },
            label = { Text("Enter Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Email Input Field
        Text(text = "Email:")
        TextField(
            value = appState.email.value,
            onValueChange = { appState.email.value = it },
            label = { Text("Enter Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Save Button (you will handle the action outside)
        Button(
            onClick = {
                onEvent.invoke()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Save")
        }
    }
}
