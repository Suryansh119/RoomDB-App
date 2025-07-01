package com.example.s.presentation.screen

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.s.ContactViewModel
import com.example.s.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllContactScreen(viewModel: ContactViewModel= hiltViewModel(),navController: NavController){
    val contact=viewModel.state.collectAsState().value
    val context= LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(modifier = Modifier.clickable{
                viewModel.changeSorting()
            },title={
                Icon(Icons.Default.Menu,null)
            })
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Routes.AddScreen)
                }
            ) {
                Icon(Icons.Default.Add, null)
            }
        }
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(it)
        ) {
            items(contact.allContact){
                var bitmap: Bitmap?=null
                if(it.image!=null){
                    bitmap= BitmapFactory.decodeByteArray(it.image,0, it.image!!.size)
                }
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row() {
                        Column(
                            modifier = Modifier.clickable{
                                contact.id.value=it.id
                                contact.name.value=it.Name
                                contact.phoneNumber.value=it.phoneNumber
                                contact.email.value=it.email
                                contact.dateOfCreation.value=it.dateOfCreation

                                navController.navigate(Routes.AddScreen)
                            }
                        ) {
                            if (bitmap!=null){
                                Image(bitmap.asImageBitmap(),null,modifier = Modifier.size(30.dp))
                            }
                            Text(text = it.Name)
                            Text(text = it.phoneNumber)
                            Text(text = it.email)

                            Button(
                                onClick = {
                                    val intent= Intent(Intent.ACTION_CALL)
                                    intent.data=Uri.parse("tel:${it.phoneNumber}")
                                    context.startActivity(intent)
                                },
                                modifier = Modifier.padding(10.dp)

                            ) {
                                Text(text = "Call")

                            }

                        }

                        Icon(
                            Icons.Default.Delete,
                            null,
                            modifier = Modifier.clickable {
                                contact.id.value=it.id
                                contact.name.value=it.Name
                                contact.phoneNumber.value=it.phoneNumber
                                contact.email.value=it.email
                                contact.dateOfCreation.value=it.dateOfCreation
                                viewModel.delete()
                            }
                        )

                    }
                }
            }
        }
    }

}