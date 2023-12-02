package com.dpfht.demomoviecompose.framework.commons.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter.State.Success
import coil.compose.rememberAsyncImagePainter
import com.dpfht.demomoviecompose.domain.entity.MovieEntity

@Composable
fun ItemMovie(
  movie: MovieEntity,
  onClickMovieItem: (movieId: Int) -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .clip(RoundedCornerShape(8.dp))
      .clickable { onClickMovieItem(movie.id) },
    shape = RoundedCornerShape(8.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    elevation = CardDefaults.cardElevation(
      defaultElevation = 8.dp
    )
  ) {
    val painter = rememberAsyncImagePainter(movie.imageUrl)
    val transition by animateFloatAsState(
      targetValue = if (painter.state is Success) 1f else 0f, label = ""
    )

    Row(
      modifier = Modifier.padding(8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
          .width(120.dp)
          .height(120.dp)
          .clip(RoundedCornerShape(8.dp))
          .alpha(transition)
      )

      Column(
        modifier = Modifier.padding(start = 4.dp, end = 8.dp),
        verticalArrangement = Arrangement.Center
      ) {
        Text(
          text = movie.title,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = movie.overview,
          maxLines = 4,
          overflow = TextOverflow.Ellipsis
        )
      }
    }
  }
}
