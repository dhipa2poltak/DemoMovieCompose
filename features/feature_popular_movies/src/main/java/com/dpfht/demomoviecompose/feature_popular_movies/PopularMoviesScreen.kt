package com.dpfht.demomoviecompose.feature_popular_movies

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dpfht.demomoviecompose.domain.entity.MovieEntity
import com.dpfht.demomoviecompose.feature_popular_movies.event_state.UIEvent
import com.dpfht.demomoviecompose.framework.commons.ui.components.ItemMovie
import com.dpfht.demomoviecompose.framework.commons.ui.components.SharedMenuTopAppBar
import kotlinx.coroutines.launch

@Composable
fun PopularMoviesScreen(
  modifier: Modifier = Modifier,
  drawerState: DrawerState,
  screenTitle: String,
  onClickAboutMenuItem: () -> Unit,
  viewModel: PopularMoviesViewModel = hiltViewModel()
) {
  val coroutineScope = rememberCoroutineScope()
  val activity = LocalContext.current as Activity

  val state = viewModel.uiState.value
  val moviePagingItems: LazyPagingItems<MovieEntity> = viewModel.uiState.value.moviesState.collectAsLazyPagingItems()

  if (state.errorMessage.isNotEmpty()) {
    viewModel.onEvent(UIEvent.OnError(state.errorMessage))
  }

  LaunchedEffect(Unit) {
    viewModel.onEvent(UIEvent.Init)
  }

  BackHandler(true) {
    if (drawerState.isOpen) {
      coroutineScope.launch {
        drawerState.close()
      }
    } else {
      activity.moveTaskToBack(true)
    }
  }

  Scaffold(
    topBar = {
      SharedMenuTopAppBar(
        title = screenTitle,
        openDrawer = {
          coroutineScope.launch {
            drawerState.open()
          }
        },
        onClickAboutMenuItem = onClickAboutMenuItem
      )
    },
    content = { padding ->
      Box(
        modifier = Modifier
          .padding(padding)
          .fillMaxSize()
      ) {
        ConstraintLayout(
          modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxSize()
        ) {

          val (tvTitle, divider, movieList) = createRefs()
          val colors = MaterialTheme.colorScheme

          Text(
            stringResource(id = R.string.popular_movies_text_popular_movies),
            color = Color.Black,
            modifier = modifier
              .padding(vertical = 8.dp)
              .constrainAs(tvTitle) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(divider.top)
              },
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
          )

          HorizontalDivider(
            modifier = modifier
              .height(1.dp)
              .constrainAs(divider) {
                start.linkTo(parent.start)
                top.linkTo(tvTitle.bottom)
                end.linkTo(parent.end)
                bottom.linkTo(movieList.top)
              },
            color = colors.onSurface.copy(alpha = .2f)
          )

          LazyColumn(
            modifier = modifier
              .fillMaxSize()
              .constrainAs(movieList) {
                start.linkTo(parent.start)
                top.linkTo(divider.bottom)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
              }
          ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }
            items(moviePagingItems.itemCount) { index ->
              ItemMovie(
                movie = moviePagingItems[index] ?: MovieEntity(),
                onClickMovieItem = { movieId ->
                  viewModel.onEvent(UIEvent.OnClickMovieItem(movieId))
                }
              )
            }
            item { Spacer(modifier = Modifier.height(32.dp)) }
          }
        }

        if (state.isLoading) {
          CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
      }
    }
  )
}
