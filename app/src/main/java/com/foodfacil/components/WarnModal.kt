package com.foodfacil.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.foodfacil.R

@Composable
fun WarnModal(
    title: String = "Você deseja prosseguir?",
    confirmTitle: String = "Confirmar",
    confirmButtonBackgroundColor: Color = Color(
        0xFF157E09
    ),
    toogleAddDialogVisibility: () -> Unit = {},
    onConfirmClick: () -> Unit = {},
) {
    Dialog(onDismissRequest = { toogleAddDialogVisibility() }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.error),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp)
                )
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.overline,
                    fontSize = 17.sp
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { onConfirmClick() },
                        colors = ButtonDefaults.buttonColors(confirmButtonBackgroundColor)
                    ) {
                        Text(
                            text = confirmTitle,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.caption,
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }

    }
}