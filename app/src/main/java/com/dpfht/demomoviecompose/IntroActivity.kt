package com.dpfht.demomoviecompose

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.dpfht.demomoviecompose.framework.navigation.NavigationService
import com.dpfht.demomoviecompose.framework.navigation.Screen.IntroBaseRoute
import com.dpfht.demomoviecompose.ui.theme.DemoMovieComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IntroActivity : ComponentActivity() {

  @Inject
  lateinit var navigationService: NavigationService

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val navController = rememberNavController()
      val activity = LocalContext.current as Activity

      DemoMovieComposeTheme {
        IntroNavigation(navigationService = navigationService, activity = activity)

        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          NavHost(
            navController = navController,
            startDestination = IntroBaseRoute.route
          ) {
            introGraph()
          }
        }
      }
    }
  }
}
