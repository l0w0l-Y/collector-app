package com.kaleksandra.composetemplate

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kaleksandra.collector.presentation.create.CreateCollectionScreen
import com.kaleksandra.collector.presentation.collection.CollectionScreen
import com.kaleksandra.corenavigation.AddCollectionDirection
import com.kaleksandra.corenavigation.CollectionDirection
import com.kaleksandra.coretheme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                Scaffold {
                    NavHost(
                        navController = navController,
                        startDestination = CollectionDirection.path
                    ) {
                        composable(CollectionDirection.path) { CollectionScreen(navController = navController) }
                        composable(AddCollectionDirection.path) { CreateCollectionScreen(navController = navController) }
                    }
                }
            }
        }
    }

    @Composable
    fun Main() {
        Text(
            text = "Hello Main!",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}