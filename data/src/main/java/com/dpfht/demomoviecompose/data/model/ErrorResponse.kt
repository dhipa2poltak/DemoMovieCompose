package com.dpfht.demomoviecompose.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class ErrorResponse(

  val success: Boolean? = false,
  @SerializedName("status_code")
  @Expose
  val statusCode: Int? = 0,
  @SerializedName("status_message")
  @Expose
  val statusMessage: String? = ""
)
