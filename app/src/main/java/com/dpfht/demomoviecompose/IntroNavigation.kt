package com.dpfht.demomoviecompose

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dpfht.demomoviecompose.feature_splash.SplashScreen
import com.dpfht.demomoviecompose.framework.navigation.NavigationService
import com.dpfht.demomoviecompose.framework.navigation.Screen
import com.dpfht.demomoviecompose.framework.navigation.Screen.IntroBaseRoute
import com.dpfht.demomoviecompose.framework.navigation.Screen.None

@Composable
fun IntroNavigation(navigationService: NavigationService, activity: Activity) {

  navigationService.screen.collectAsState().value.also { screen ->
    when (screen) {
      Screen.Home -> {
        val itn = Intent(activity, MainActivity::class.java)
        activity.startActivity(itn)
        navigationService.navigate(None)
        activity.finish()
      }
      else -> {}
    }
  }
}

fun NavGraphBuilder.introGraph() {
  navigation(route = IntroBaseRoute.route, startDestination = Screen.Splash.route) {
    composable(route = Screen.Splash.route) {
      SplashScreen()
    }
  }
}
