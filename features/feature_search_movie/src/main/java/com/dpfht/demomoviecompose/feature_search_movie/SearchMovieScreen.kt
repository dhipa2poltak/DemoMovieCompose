package com.dpfht.demomoviecompose.feature_search_movie

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dpfht.demomoviecompose.domain.entity.MovieEntity
import com.dpfht.demomoviecompose.feature_search_movie.event_state.UIEvent
import com.dpfht.demomoviecompose.framework.commons.ui.components.CustomTextField
import com.dpfht.demomoviecompose.framework.commons.ui.components.ItemMovie
import com.dpfht.demomoviecompose.framework.commons.ui.components.SharedMenuTopAppBar
import kotlinx.coroutines.launch

@Composable
fun SearchMovieScreen(
  modifier: Modifier = Modifier,
  drawerState: DrawerState,
  screenTitle: String,
  onClickAboutMenuItem: () -> Unit,
  viewModel: SearchMovieViewModel = hiltViewModel()
) {
  val coroutineScope = rememberCoroutineScope()

  val state = viewModel.uiState.value
  val moviePagingItems: LazyPagingItems<MovieEntity> = viewModel.uiState.value.moviesState.collectAsLazyPagingItems()

  if (state.errorMessage.isNotEmpty()) {
    viewModel.onEvent(UIEvent.OnError(state.errorMessage))
  }

  BackHandler(true) {
    if (drawerState.isOpen) {
      coroutineScope.launch {
        drawerState.close()
      }
    } else {
      viewModel.onEvent(UIEvent.OnBackPressed)
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
        modifier = modifier
          .padding(padding)
          .fillMaxSize()
      ) {
        ConstraintLayout(
          modifier = modifier
            .padding(padding)
            .fillMaxSize()
        ) {

          val (tvTitle, divider1, rowQuery, divider2, movieList) = createRefs()
          val colors = MaterialTheme.colorScheme

          Text(
            stringResource(id = R.string.search_movie_text_search_movie),
            color = Color.Black,
            modifier = modifier
              .padding(vertical = 8.dp)
              .constrainAs(tvTitle) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                bottom.linkTo(divider1.top)
              },
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
          )

          HorizontalDivider(
            modifier = modifier
              .height(1.dp)
              .constrainAs(divider1) {
                start.linkTo(parent.start)
                top.linkTo(tvTitle.bottom)
                end.linkTo(parent.end)
                bottom.linkTo(rowQuery.top)
              },
            color = colors.onSurface.copy(alpha = .2f)
          )

          Row(
            modifier = modifier
              .constrainAs(rowQuery) {
                start.linkTo(parent.start)
                top.linkTo(divider1.bottom)
                end.linkTo(parent.end)
                bottom.linkTo(divider2.top)
              },
            verticalAlignment = Alignment.CenterVertically
          ) {
            CustomTextField(
              modifier = Modifier.weight(1f),
              title = stringResource(id = R.string.search_movie_text_search_movie_by_title),
              textState = state.queryText,
              onTextChange = {
                viewModel.onEvent(UIEvent.QueryTextChanged(it))
              },
              isError = state.hasQueryTextError
            )
            Button(
              onClick = {
                viewModel.onEvent(UIEvent.OnSearch)
              },
              modifier = Modifier.padding(end = 8.dp)
            ) {
              Text(
                text = stringResource(id = R.string.search_movie_text_search)
              )
            }
          }

          HorizontalDivider(
            modifier = modifier
              .height(1.dp)
              .constrainAs(divider2) {
                start.linkTo(parent.start)
                top.linkTo(rowQuery.bottom)
                end.linkTo(parent.end)
                bottom.linkTo(movieList.top)
              },
            color = colors.onSurface.copy(alpha = .2f)
          )

          Box(
            modifier = modifier
              .fillMaxSize()
              .constrainAs(movieList) {
                start.linkTo(parent.start)
                top.linkTo(divider2.bottom)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
              }
          ) {
            if (state.isLoaded && moviePagingItems.itemCount > 0) {
              LazyColumn {
                item { Spacer(modifier = Modifier.height(8.dp)) }
                items(moviePagingItems.itemCount) { index ->
                  ItemMovie(
                    movie = moviePagingItems[index] ?: MovieEntity(),
                    onClickMovieItem = { movieId ->
                      viewModel.onEvent(UIEvent.OnClickMovieItem(movieId))
                    }
                  )
                }
                item { Spacer(modifier = Modifier.height(72.dp)) }
              }
            }
          }
        }

        if (state.isLoading) {
          CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        if (state.isLoaded && moviePagingItems.itemCount == 0) {
          Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = R.string.search_movie_text_no_data),
            fontStyle = FontStyle.Italic
          )
        }
      }
    }
  )
}
