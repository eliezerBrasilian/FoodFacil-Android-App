package com.foodfacil.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.foodfacil.dataClass.Address
import com.gamestate.utils.Toast
import com.simpletext.SimpleText

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("NewApi", "UnrememberedMutableState")
@Composable
fun AddAddressDialog(
    rua:String?,
    numero:String?,
    bairro:String?,
    complememto:String?,
    handleAddAddress:()->Unit  = {},
    toogleDialogVisible: () -> Unit = {},
    onChangeRua:(v:String)->Unit= {},
    onChangeNumero:(v:String)->Unit= {},
    onChangeBairro:(v:String)->Unit= {},
    onChangeComplemento:(v:String)->Unit= {},
    )
{

    val localContext = LocalContext.current
    val toast = Toast(localContext)

    val onClick: () -> Unit = {
        handleAddAddress()
    }

    val md = Modifier

    Dialog(onDismissRequest = { toogleDialogVisible()}) {
        Card(modifier = Modifier
            .fillMaxWidth(),
            colors = CardDefaults.cardColors(Color.White)) {
            Column(horizontalAlignment =  Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)) {
                SimpleText(title = "Adicionar Endereço", fontWeight = "bold")

                AddressInput(value = rua, labelTitle = "rua", placeholderText =  "informe o nome da rua", onChangeText = onChangeRua)
                AddressInput(value = numero, labelTitle = "número", placeholderText = "informe o número da casa",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) ,onChangeText = onChangeNumero)
                AddressInput(value = bairro, labelTitle = "bairro", placeholderText = "informe o bairro...",onChangeText = onChangeBairro)
                AddressInput(value = complememto, labelTitle = "complemento (opcional)", placeholderText = "informe algum complemento ",onChangeText = onChangeComplemento)

                TextField(
                    value = "Brás Pires",
                    placeholder = {
                        SimpleText(
                            title = "Brás Pires",
                            color = Color.DarkGray,
                            fontSize = 16
                        )
                    },
                    onValueChange = {},
                    modifier = md.fillMaxWidth(),
                    maxLines = 1,
                    enabled = false,
                    suffix = { CustomIcon() },
                )

                if(rua.toString().isNotEmpty() && numero.toString().isNotEmpty()
                    && bairro.toString().isNotEmpty())
                    Button(onClick = {
                        onClick()
                        toogleDialogVisible()
                    }) {
                        SimpleText(
                            title = "Adicionar",
                            color = Color.White,
                            fontSize = 15,
                            fontWeight = "bold"
                        )
                    }
            }
        }
    }
}