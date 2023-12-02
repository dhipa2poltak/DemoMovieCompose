package com.dpfht.demomoviecompose.ui.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.dpfht.demomoviecompose.R

@Composable
fun AboutDialog(title: String, subTitle: String, dismissDialog: () -> Unit) {

  Dialog(onDismissRequest = { dismissDialog() }) {
    Surface(
      shape = RoundedCornerShape(16.dp),
      color = Color.White
    ) {
      Box(
        contentAlignment = Alignment.Center
      ) {
        Column(
          verticalArrangement = Arrangement.Top,
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
        ) {

          val painter = painterResource(id = R.drawable.ic_movie)

          Text(
            text = title,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
          )

          Text(
            text = subTitle,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
          )

          Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
              .width(200.dp)
              .height(200.dp)
              .clip(RoundedCornerShape(8.dp))
          )

          Button(
            onClick = {
              dismissDialog()
            },
            colors = ButtonDefaults.buttonColors(
              containerColor = Color.Black
            )
          ) {
            Text(
              text = stringResource(id = R.string.app_text_ok)
            )
          }
        }
      }
    }
  }
}
