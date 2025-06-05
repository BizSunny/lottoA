package biz.sunny.path.lottoa.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import biz.sunny.path.lottoa.R
import biz.sunny.path.lottoa.viewmodel.SplashViewModel

@Composable
fun SplashScreen(navController: NavController, splashViewModel: SplashViewModel = viewModel()) {
    val isLoading by splashViewModel.isLoading.collectAsState()
    val errorMessage by splashViewModel.errorMessage.collectAsState()

    LaunchedEffect(isLoading) {
        if(!isLoading){
            if(errorMessage == null){
                navController.popBackStack()
                navController.navigate(Screen.Main.route)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center)
    {
        Image(painter = painterResource(R.drawable.app_splash),
            contentDescription = "App Splash",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize())

        if(isLoading){
            Column(modifier = Modifier.fillMaxSize().padding(bottom = 20.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = stringResource(R.string.splash_data_loading_msg),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimary)
            }
        }

        errorMessage?.let { message ->
            Column(modifier = Modifier.fillMaxSize().padding(bottom = 20.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = message,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
fun SplashScreenContent() {
    val isLoading: Boolean = true
    val errorMessage: String? = null

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center)
    {
        Image(painter = painterResource(R.drawable.app_splash),
            contentDescription = "App Splash",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize())

        if(isLoading){
            Column(modifier = Modifier.fillMaxSize().padding(bottom = 20.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = stringResource(R.string.splash_data_loading_msg),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimary)
            }
        }

        errorMessage?.let { message ->
            Column(modifier = Modifier.fillMaxSize().padding(bottom = 20.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = message,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreenContent()
}