package com.gamestate.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import com.gamestate.enums.KeyboardTypes
import com.simpletext.SimpleText

@Composable
fun InputText(value: String, placeHolderText:String, keyboardType:String = "",
              singleLine:Boolean = true,
              onAction:KeyboardActions = KeyboardActions.Default,
              setEmailInput: (value:String)->Unit) {
    var currentKeyboardType:KeyboardType = KeyboardType.Text;

    if(keyboardType == KeyboardTypes.EMAIL.name){
        currentKeyboardType = KeyboardType.Email
    }
    if(keyboardType == KeyboardTypes.PASSWORD.name){
        currentKeyboardType = KeyboardType.Password
    }
    if(keyboardType == KeyboardTypes.URL.name){
        currentKeyboardType = KeyboardType.Uri
    }


    TextField(value = value, onValueChange = {it->
        setEmailInput(it)
    },
        singleLine = singleLine,
       keyboardActions = onAction,

        modifier = Modifier
        .fillMaxWidth()
        .background(Color.White),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedTextColor = Color.DarkGray,
            unfocusedTextColor = Color.DarkGray
           ),
        placeholder = { SimpleText(
            title = placeHolderText,
            color = Color.Gray,
            fontSize = 15,
        )
        },
        keyboardOptions = KeyboardOptions(keyboardType = currentKeyboardType)
        )

}

