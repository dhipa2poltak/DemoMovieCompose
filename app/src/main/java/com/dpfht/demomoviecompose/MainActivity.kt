package com.dpfht.demomoviecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue.Closed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dpfht.demomoviecompose.framework.commons.ui.components.AppDrawerContent
import com.dpfht.demomoviecompose.framework.navigation.NavigationService
import com.dpfht.demomoviecompose.framework.navigation.Screen.MainBaseRoute
import com.dpfht.demomoviecompose.ui.dialog.AboutDialog
import com.dpfht.demomoviecompose.ui.theme.DemoMovieComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  @Inject
  lateinit var navigationService: NavigationService

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val navController = rememberNavController()
      val coroutineScope = rememberCoroutineScope()
      val drawerState: DrawerState = rememberDrawerState(initialValue = Closed)
      val navBackStackEntry by navController.currentBackStackEntryAsState()

      val appWindowTitle = "${stringResource(id = R.string.app_name)}${stringResource(id = R.string.running_mode)}"

      val showDialog =  remember { mutableStateOf(false) }

      val onClickAboutMenuItem: () -> Unit = {
        showDialog.value = true
      }

      DemoMovieComposeTheme {
        MainNavigation(navigationService = navigationService, navController = navController)

        if (showDialog.value) {
          val theTitle = "${getString(R.string.app_name)}${getString(R.string.running_mode)}"
          val theSubTitle = "v${BuildConfig.VERSION_NAME}"

          AboutDialog(title = theTitle, subTitle = theSubTitle) {
            showDialog.value = false
          }
        }

        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = true,
            drawerContent = {
              AppDrawerContent(
                currentScreen = navBackStackEntry?.destination?.route ?: "",
                onScreenSelected = { screen ->
                  navigationService.navigate(screen)

                  coroutineScope.launch {
                    drawerState.close()
                  }
                }
              )
            }
          ) {
            NavHost(
              navController = navController,
              startDestination = MainBaseRoute.route
            ) {
              mainGraph(appWindowTitle = appWindowTitle, drawerState = drawerState, onClickAboutMenuItem = onClickAboutMenuItem)
            }
          }
        }
      }
    }
  }
}
