package com.foodfacil.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.simpletext.SimpleText

@Composable
fun AddressInput(
    placeholderText:String = "informe o nome da rua...",
    placeholderTextColor:Color = Color.DarkGray,
    fontSize:Int = 16,
    value:String?,
    labelTitle:String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    md:Modifier = Modifier,
    onChangeText:(v:String)->Unit = {}
    ){
    OutlinedTextField(
        value =  if(value == null) "" else value,
        placeholder = {
            SimpleText(
                title = placeholderText,
                color = placeholderTextColor,
                fontSize = fontSize) },
        onValueChange = onChangeText,
        modifier = md.fillMaxWidth(),
        label = { SimpleText(title = labelTitle, fontSize = fontSize) },
        keyboardOptions = keyboardOptions,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
        )
    )
}