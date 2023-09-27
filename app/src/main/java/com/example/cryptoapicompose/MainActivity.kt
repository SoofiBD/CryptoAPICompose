package com.example.cryptoapicompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cryptoapicompose.ui.theme.CryptoAPIComposeTheme
import com.example.cryptoapicompose.view.CryptoDetailScreen
import com.example.cryptoapicompose.view.CryptoListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoAPIComposeTheme {
                val navController = rememberNavController()
                //val myState = remember { mutableStateOf("") }

                NavHost(navController = navController,  startDestination = "crypto_list_screen" ) {
                    composable("crypto_list_screen") {
                        CryptoListScreen(navController = navController)
                    }

                    //composable("crypto_detail_screen/{cryptoId}")
                    composable("crypto_detail_screen/{cryptoId}/{cryptoPrice}" , arguments = listOf(
                        navArgument("cryptoId") {
                            type = NavType.IntType
                        },
                        navArgument("cryptoPrice") {
                            type = NavType.StringType
                        },
                    )) {
                        val cryptoId = remember {
                         it.arguments?.getInt("cryptoId")
                        }

                        val cryptoPrice = remember {
                            it.arguments?.getString("cryptoPrice")
                        }
                        CryptoDetailScreen(navController = navController, id = cryptoId!!, price = cryptoPrice!!)
                    }
                }
            }
        }
    }
}
