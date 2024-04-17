package com.foodfacil.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun SkeletonLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .fillMaxWidth()
        .height(120.dp).background(Color.LightGray).shimmer()
    ) {
        Row(horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxSize()) {
            Left()
            Right()
        }
    }
}

@Composable
private fun Title(){
    Box(modifier = Modifier
        .width(120.dp)
        .height(15.dp)
        .background(Color.Gray))
}

@Composable
private fun Left(){
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Title()
        Box(modifier = Modifier
            .width(170.dp)
            .height(15.dp)
            .background(Color.Gray))

        Box(modifier = Modifier
            .width(170.dp)
            .height(15.dp)
            .background(Color.Gray))
    }
}

@Composable
private fun Right(){
    Box(modifier = Modifier.size(90.dp).background(color = Color.Gray, shape = CircleShape))
}

@Preview
@Composable
private fun Righta(){
    Box(modifier = Modifier.size(90.dp).background(color = Color.Gray, shape = CircleShape))
}


@Composable
fun SkeletonLoadingList(modifier: Modifier = Modifier, itemCount: Int = 4) {
    Column(modifier = modifier) {
        repeat(itemCount) {
            SkeletonLoading(modifier = Modifier.padding(8.dp))
        }
    }
}
