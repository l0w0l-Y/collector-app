package com.kaleksandra.collector

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kaleksandra.collector.presentation.collection.CollectionScreen
import com.kaleksandra.collector.presentation.create.CreateCollectionScreen
import com.kaleksandra.corenavigation.CollectionDirection
import com.kaleksandra.corenavigation.CreateCollectionDirection
import com.kaleksandra.corenavigation.ProfileDirection
import com.kaleksandra.coretheme.AppTheme
import com.kaleksandra.featureprofile.presentation.ProfileScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                SetStatusBarColor(
                    MaterialTheme.colorScheme.background,
                    !isSystemInDarkTheme()
                )
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                Scaffold {
                    NavHost(
                        navController = navController,
                        startDestination = CollectionDirection.path
                    ) {
                        composable(CollectionDirection.path) {
                            CollectionScreen(navController = navController)
                        }
                        composable(CreateCollectionDirection.path) {
                            CreateCollectionScreen(
                                navController = navController
                            )
                        }
                        composable(ProfileDirection.path) {
                            ProfileScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun SetStatusBarColor(
        color: Color,
        darkIcons: Boolean = true
    ) {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setStatusBarColor(color, darkIcons)
            systemUiController.setNavigationBarColor(color, darkIcons)
        }
    }
}