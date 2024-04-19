package com.foodfacil.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ObservacaoCaixaDeTexto(value: String, onChangeObservacaoInput: (text: String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        placeholder = {
            Text(
                text = "Escreva sua obervação aqui...",
                fontSize = 17.sp,
                color = Color.LightGray
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            unfocusedIndicatorColor = Color(0xffBCBABA),
            focusedIndicatorColor = Color(0xffBCBABA),

            ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        value = value,
        onValueChange = onChangeObservacaoInput,
        shape = RoundedCornerShape(10.dp)
    )
}