package com.example.s.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    object HomeScreen: Routes()

    @Serializable
    object AddScreen: Routes()
}