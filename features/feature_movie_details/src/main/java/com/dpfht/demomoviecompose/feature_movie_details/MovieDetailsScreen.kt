package com.dpfht.demomoviecompose.feature_movie_details

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter.State.Success
import coil.compose.rememberAsyncImagePainter
import com.dpfht.demomoviecompose.feature_movie_details.event_state.UIEvent
import com.dpfht.demomoviecompose.feature_movie_details.event_state.UIEvent.OnBackPressed
import com.dpfht.demomoviecompose.framework.commons.ui.components.SharedBackTopAppBar
import kotlinx.coroutines.launch

@Composable
fun MovieDetailsScreen(
  drawerState: DrawerState,
  screenTitle: String,
  isForResult: Boolean = false,
  movieId: Int,
  onClickAboutMenuItem: () -> Unit,
  viewModel: MovieDetailsViewModel = hiltViewModel()
) {
  val coroutineScope = rememberCoroutineScope()
  val state = viewModel.uiState.value

  LaunchedEffect(Unit) {
    viewModel.onEvent(UIEvent.Init(isForResult = isForResult, movieId = movieId))
  }

  BackHandler(true) {
    if (drawerState.isOpen) {
      coroutineScope.launch {
        drawerState.close()
      }
    } else {
      viewModel.onEvent(OnBackPressed)
    }
  }

  Scaffold(
    topBar = {
      SharedBackTopAppBar(
        title = screenTitle,
        onClickAboutMenuItem = onClickAboutMenuItem,
        onBackPressed = {
          viewModel.onEvent(OnBackPressed)
        }
      )
    },
    content = { padding ->
      Box(
        modifier = Modifier
          .padding(padding)
          .fillMaxSize()
      ) {
        Column(
          verticalArrangement = Arrangement.Top,
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
        ) {
          val painter = rememberAsyncImagePainter(state.imageUrl)
          val transition by animateFloatAsState(
            targetValue = if (painter.state is Success) 1f else 0f, label = ""
          )

          Text(
            text = state.title,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
              .padding(vertical = 10.dp)
          )
          Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
              .width(320.dp)
              .height(320.dp)
              .padding(vertical = 10.dp)
              .clip(RoundedCornerShape(8.dp))
              .alpha(transition)
          )

          if (state.genres.isNotEmpty()) {
            var strGenres = ""
            for (genre in state.genres) {
              if (strGenres.isEmpty()) {
                strGenres = genre.name
              } else {
                strGenres += ", ${genre.name}"
              }
            }

            Text(
              text = strGenres,
              textAlign = TextAlign.Center,
              modifier = Modifier
                .padding(top = 10.dp, start = 15.dp, end = 15.dp)
                .align(Alignment.CenterHorizontally),
              fontWeight = FontWeight.Bold
            )
          }

          Text(
            text = state.description,
            modifier = Modifier.padding(top = 10.dp, start = 15.dp, end = 15.dp)
          )
          Spacer(
            modifier = Modifier.height(20.dp)
          )
          if (state.errorMessage.isEmpty()) {
            if (state.isFavorite) {
              Button(
                onClick = {
                  viewModel.onEvent(UIEvent.OnClickDeleteFromFavorite)
                }
              ) {
                Text(
                  text = stringResource(id = R.string.movie_details_text_delete_from_favorite)
                )
              }
            } else {
              Button(
                onClick = {
                  viewModel.onEvent(UIEvent.OnClickAddToFavorite)
                }
              ) {
                Text(
                  text = stringResource(id = R.string.movie_details_text_add_to_favorite)
                )
              }
            }
          }
        }

        if (state.isLoading) {
          CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
      }
    }
  )
}
