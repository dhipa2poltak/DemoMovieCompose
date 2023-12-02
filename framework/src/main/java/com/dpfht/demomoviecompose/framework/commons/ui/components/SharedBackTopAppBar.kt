package com.dpfht.demomoviecompose.framework.commons.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.dpfht.demomoviecompose.framework.R.string

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedBackTopAppBar(
  title: String,
  onBackPressed: () -> Unit,
  onClickAboutMenuItem: () -> Unit
) {
  var mDisplayMenu by remember { mutableStateOf(false) }

  TopAppBar(
    title = {
      Text(
        text = title,
        color = MaterialTheme.colorScheme.onPrimary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
    },
    navigationIcon = {
      IconButton(
        onClick = {
          onBackPressed()
        }
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
          contentDescription = "Back Button",
          tint = MaterialTheme.colorScheme.onPrimary
        )
      }
    },
    actions = {
      IconButton(onClick = {
        mDisplayMenu = !mDisplayMenu
      }) {
        Icon(
          Icons.Default.MoreVert,
          contentDescription = "",
          tint = MaterialTheme.colorScheme.onPrimary
        )

        DropdownMenu(
          expanded = mDisplayMenu,
          onDismissRequest = {
            mDisplayMenu = false
          }
        ) {
          DropdownMenuItem(
            text = {
              Text(stringResource(id = string.framework_text_about))
            }, onClick = {
              mDisplayMenu = false
              onClickAboutMenuItem()
            })
        }
      }
    },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.primary
    )
  )
}
