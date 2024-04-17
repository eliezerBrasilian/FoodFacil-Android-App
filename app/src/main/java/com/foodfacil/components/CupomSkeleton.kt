package com.foodfacil.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.foodfacil.dataclass.CupomDto
import com.foodfacil.services.Print
import com.foodfacil.utils.AppDateTime
import com.simpletext.SimpleText
import com.valentinilk.shimmer.shimmer

@Composable
fun CupomSkeletonLoadingList(modifier: Modifier = Modifier, itemCount: Int = 5) {
    Column(modifier = modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(15.dp)) {
        repeat(itemCount) {
            CupomSkeletonLoading()
            // SkeletonLoading(modifier = Modifier.padding(8.dp))
        }
    }
}
@Composable
fun CupomSkeletonLoading(modifier: Modifier = Modifier) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .background(Color.LightGray)
        .shimmer(),
        ) {
        Box(modifier = Modifier.padding(10.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                CupomTop()
                CupomBottom()
            }
        }
    }
}

@Composable
private fun Title(height: Dp = 15.dp, width:Dp = 120.dp){
    Box(modifier = Modifier
        .width(width)
        .height(height)
        .background(Color.Gray))
}

@Composable
private fun CupomTop() {
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.fillMaxWidth()) {
        CupomTopLeftItem()
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Title(height = 10.dp)
            Title(height = 13.dp, width = 126.dp)
            Title(height = 10.dp,width = 128.dp)
        }
    }
}

@Composable
fun CupomTopLeftItem() {
    Circle(color = Color.Gray,  size = 90.dp) {
        Title()
    }
}

@Composable
fun CupomBottom() {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(15.dp)){
                Title(height = 10.dp)
                Title(height = 10.dp,width = 60.dp)
            }
            Title(height = 40.dp, width = 110.dp)
        }
    }
}


