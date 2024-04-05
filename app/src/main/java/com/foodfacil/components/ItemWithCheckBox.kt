package com.foodfacil.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.simpletext.SimpleText
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ChevronDown

//ckecbox border color = 0xffBCBABA
@Composable
fun ItemWithCheckBox(
    text:String, fontColor:Color = Color.Black,
    fontSize:Int = 17, fontWeight: String = "normal", checkBoxSize:Int = 25,
    checkBoxBorderWidth:Int = 1, checkBoxBorderColor: Color = Color.Red,
    checkBoxBorderRadius:Int = 10,
    isActive:Boolean = false,
    icon: ImageVector = FontAwesomeIcons.Solid.ChevronDown,
    onClick:(isActive:Boolean)->Unit = {}
){
    val (isChecked, setChecked) = remember { mutableStateOf(isActive) }

    val md = Modifier
    Row(modifier = md.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.SpaceBetween) {
        SimpleText(title = text, fontSize = fontSize, fontWeight = fontWeight, color = fontColor)
        if(!isChecked)
            NonActiveSquare(checkBoxSize, checkBoxBorderWidth, checkBoxBorderColor = checkBoxBorderColor,
            checkBoxBorderRadius = checkBoxBorderRadius,
                onCLick = {
                    setChecked(true)
                    onClick(true)
                })
        else
            ActiveSquare(checkBoxSize = checkBoxSize, icon = icon, onCLick = {
                setChecked(false)
                onClick(false)
            })
    }
}

@Composable
private fun NonActiveSquare(
    checkBoxSize: Int,
    checkBoxBorderWidth: Int,
    checkBoxBorderRadius: Int,
    checkBoxBorderColor: Color,
    modifier: Modifier = Modifier,
    onCLick: () -> Unit
){
    Box(modifier = modifier
        .size(checkBoxSize.dp)
        .border(
            color = checkBoxBorderColor,
            shape = RoundedCornerShape(checkBoxBorderRadius.dp), width = checkBoxBorderWidth.dp
        )
        .clickable { onCLick() })
}
@Composable
private fun ActiveSquare(
    checkBoxSize: Int,
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onCLick:()-> Unit
){
    Box(modifier = modifier
        .size(checkBoxSize.dp)
        .clickable { onCLick() },
        contentAlignment = Alignment.Center){
        Icon(imageVector = icon, contentDescription = null)
    }
}