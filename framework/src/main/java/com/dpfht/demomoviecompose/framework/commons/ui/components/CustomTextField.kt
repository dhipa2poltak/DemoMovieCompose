package com.dpfht.demomoviecompose.framework.commons.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
  modifier: Modifier = Modifier,
  title: String,
  textState: String,
  onTextChange: (String) -> Unit,
  isError: Boolean = false
) {
  OutlinedTextField(
    value = textState,
    onValueChange = { onTextChange(it) },
    singleLine = true,
    label = {
      Text(title)
    },
    modifier = modifier.padding(10.dp),
    keyboardOptions = KeyboardOptions(
      keyboardType = KeyboardType.Text,
      imeAction = ImeAction.Done
    ),
    isError = isError
  )
}
