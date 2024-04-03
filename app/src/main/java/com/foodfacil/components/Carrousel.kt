package com.carrousel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Carrousel(imagesResources: List<Int>, imageActiveColor:Color = Color.Green,
              imageInactiveColor:Color = Color.Black,
              dotBorderRadius:Dp = 20.dp,
              dotHeight:Dp = 10.dp,
              dotWidth:Dp = 15.dp
              ) {
    val count = imagesResources.size
    // You can use PagerState to define an initial page
    val state = rememberPagerState(initialPage = 0) { count }
    val md  = Modifier

        Column(md.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)){
            HorizontalPager(
                state = state,
                md.fillMaxWidth()
            ) { page ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    val composableToDisplay = imagesResources[page]
                      Image(
                      painter = painterResource(id = imagesResources[page]),
                      contentDescription = null
                        )
                }
            }
            Dots(count,state.currentPage,imageActiveColor, imageInactiveColor, dotHeight, dotWidth, dotBorderRadius)
        }
}

@Composable
fun Dots(
    total: Int,
    currentPage: Int,
    imageActiveColor: Color,
    imageInactiveColor: Color,
    dotHeight: Dp,
    dotWidth: Dp,
    dotBorderRadius: Dp
){
        Box(contentAlignment = Alignment.Center){
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)){
                items(count = total){dot->
                    val dotColor by animateColorAsState(
                        targetValue = if (dot == currentPage) imageActiveColor else imageInactiveColor,
                        label = ""
                    )

                    Log.e("Pager_", "current dot: $dot, currentPage: $currentPage")
                    Dot(dotColor, dotHeight, dotWidth, dotBorderRadius)
                }
            }
        }
    }

@Composable
fun Dot(dotColor: Color, dotHeight: Dp, dotWidth: Dp, dotBorderRadius: Dp) {
    Box(modifier = Modifier
        .width(dotWidth)
        .height(dotHeight)
        .background(color = dotColor, shape = RoundedCornerShape(dotBorderRadius)))
}