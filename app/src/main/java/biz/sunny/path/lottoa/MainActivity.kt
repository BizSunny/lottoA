package biz.sunny.path.lottoa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import biz.sunny.path.lottoa.ui.theme.LottoATheme
import biz.sunny.path.lottoa.view.Screen
import biz.sunny.path.lottoa.view.SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LottoATheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Screen.Splash.route){
                    composable(Screen.Splash.route){
                        SplashScreen(navController = navController)
                    }

                    composable(Screen.Main.route){
                        MainScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Text(text = "메인화면")
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    LottoATheme {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Screen.Splash.route){
            composable(Screen.Splash.route){
                SplashScreen(navController = navController)
            }

            composable(Screen.Main.route){
                MainScreen()
            }
        }
    }
}