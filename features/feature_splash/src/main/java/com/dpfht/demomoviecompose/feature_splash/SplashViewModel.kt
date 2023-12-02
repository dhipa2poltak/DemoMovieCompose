package com.dpfht.demomoviecompose.feature_splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpfht.demomoviecompose.feature_splash.event_state.UIEvent
import com.dpfht.demomoviecompose.framework.Constants
import com.dpfht.demomoviecompose.framework.navigation.NavigationService
import com.dpfht.demomoviecompose.framework.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
  private val navigationService: NavigationService
): ViewModel() {

  fun onEvent(event: UIEvent) {
    when (event) {
      UIEvent.Init -> {
        start()
      }
    }
  }

  private fun start() {
    viewModelScope.launch {
      delay(Constants.DELAY_SPLASH)
      navigationService.navigate(Screen.Home)
    }
  }
}
