package com.dpfht.demomoviecompose.framework.commons.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dpfht.demomoviecompose.framework.R
import com.dpfht.demomoviecompose.framework.navigation.Screen
import java.util.Calendar

@Composable
fun AppDrawerContent(
  currentScreen: String,
  onScreenSelected: (Screen) -> Unit
) {
  ModalDrawerSheet(
    modifier = Modifier.width(300.dp)
  ) {
    Surface(color = MaterialTheme.colorScheme.background) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        AppDrawerHeader()

        HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = .2f))

        ScreenNavigationButton(
          icon = Filled.Home,
          label = stringResource(id = R.string.framework_text_popular_movies),
          isSelected = currentScreen == Screen.PopularMovies.route,
          onClick = {
            onScreenSelected.invoke(Screen.PopularMovies)
          }
        )
        ScreenNavigationButton(
          icon = Filled.Search,
          label = stringResource(id = R.string.framework_text_search_movie),
          isSelected = currentScreen == Screen.SearchMovie.route,
          onClick = {
            onScreenSelected.invoke(Screen.SearchMovie)
          }
        )
        ScreenNavigationButton(
          icon = Filled.Favorite,
          label = stringResource(id = R.string.framework_text_favorite_movies),
          isSelected = currentScreen == Screen.FavoriteMovies.route,
          onClick = {
            onScreenSelected.invoke(Screen.FavoriteMovies(Calendar.getInstance().timeInMillis))
          }
        )
      }
    }
  }
}

@Composable
private fun AppDrawerHeader() {
  Image(
    modifier = Modifier
      .size(size = 120.dp),
    painter = painterResource(id = R.drawable.ic_movie_splash),
    contentDescription = "Header Image"
  )
}

@Composable
private fun ScreenNavigationButton(
  icon: ImageVector,
  label: String,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  val colors = MaterialTheme.colorScheme

  // Define alphas for the image for two different states
  // of the button: selected/unselected
  val imageAlpha = if (isSelected) {
    1f
  } else {
    0.6f
  }

  // Define color for the text for two different states
  // of the button: selected/unselected
  val textColor = if (isSelected) {
    colors.primary
  } else {
    colors.onSurface.copy(alpha = 0.6f)
  }

  // Define color for the background for two different states
  // of the button: selected/unselected
  val backgroundColor = if (isSelected) {
    colors.primary.copy(alpha = 0.12f)
  } else {
    colors.surface
  }

  Surface( // 1
    modifier = Modifier
      .fillMaxWidth()
      .padding(start = 8.dp, end = 8.dp, top = 8.dp),
    color = backgroundColor,
    shape = MaterialTheme.shapes.small
  ) {
    Row( // 2
      horizontalArrangement = Arrangement.Start,
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier
        .clickable(onClick = onClick)
        .fillMaxWidth()
        .padding(4.dp)
    ) {
      Image(
        imageVector = icon,
        contentDescription = "Screen Navigation Button",
        colorFilter = ColorFilter.tint(textColor),
        alpha = imageAlpha
      )
      Spacer(Modifier.width(16.dp)) // 3
      Text(
        text = label,
        style = MaterialTheme.typography.bodyMedium,
        color = textColor,
        modifier = Modifier.fillMaxWidth()
      )
    }
  }
}
