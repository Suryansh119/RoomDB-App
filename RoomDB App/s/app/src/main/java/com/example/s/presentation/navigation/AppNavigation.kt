package com.example.s.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.s.ContactViewModel
import com.example.s.presentation.screen.AllContactScreen

import com.example.s.presentation.screen.ContactFormUI
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun AppNavigation(modifier: Modifier =Modifier,viewModel: ContactViewModel= hiltViewModel()){
    var navController = rememberNavController()
    var state=viewModel.state.collectAsState()
    NavHost(navController=navController, startDestination = Routes.HomeScreen){

        composable<Routes.HomeScreen>(){
            AllContactScreen(navController=navController)
        }
        composable<Routes.AddScreen>(){
            ContactFormUI(onEvent = {viewModel.insert()},appState = state.value)
        }
    }
}