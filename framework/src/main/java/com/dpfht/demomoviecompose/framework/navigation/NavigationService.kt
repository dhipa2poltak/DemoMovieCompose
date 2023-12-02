package com.dpfht.demomoviecompose.framework.navigation

import com.dpfht.demomoviecompose.framework.navigation.Screen.NavigateUp
import com.dpfht.demomoviecompose.framework.navigation.Screen.None
import com.dpfht.demomoviecompose.framework.navigation.Screen.Error
import com.dpfht.demomoviecompose.framework.navigation.Screen.NavigateBackWithResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NavigationService {

  private val _screen = MutableStateFlow<Screen>(None)
  val screen: StateFlow<Screen> = _screen

  fun navigate(newRoute: Screen) {
    _screen.value = newRoute
  }

  fun navigateUp() {
    _screen.value = NavigateUp
  }

  fun navigateToErrorMessage(msg: String) {
    _screen.value = Error(msg)
  }

  fun navigateBackForResult(data: Map<String, Any>?) {
    _screen.value = NavigateBackWithResult(data)
  }
}
