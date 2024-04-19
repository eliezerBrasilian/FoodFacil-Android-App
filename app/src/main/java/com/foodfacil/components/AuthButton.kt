package com.foodfacil.components

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.simpletext.SimpleText

@Composable
fun AuthButton(
    title: String,
    color: Color = Color.White,
    fontSize: Int = 18,
    fontWeight: String = "100",
    backgroundColor: Color = Color.Blue,
    onClick: () -> Unit = {},
    isLoading: Boolean = false,
    loadingSize: Dp = 25.dp
)
{
    val TAG = "LOGIN-CLICK"
    Log.d(TAG, isLoading.toString())

    Button(onClick =  onClick, colors = ButtonDefaults.buttonColors(
        containerColor = backgroundColor
    ),
        modifier = Modifier
            .fillMaxWidth()
            , contentPadding = PaddingValues(15.dp)
    ) {
        if(isLoading) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(loadingSize)) else SimpleText(title = title,color = color, fontSize = fontSize,fontWeight = fontWeight)
    }
}