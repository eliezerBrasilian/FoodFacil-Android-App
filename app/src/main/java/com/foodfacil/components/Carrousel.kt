package com.foodfacil.components

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.foodfacil.services.Print
import com.foodfacil.ui.theme.MainYellow

val print = Print()
@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Carrousel(imagesResources: List<Int>, imageActiveColor:Color = Color.Green,
              imageInactiveColor:Color = Color.Black,
              dotBorderRadius:Dp = 20.dp,
              dotHeight:Dp = 10.dp,
              dotWidth:Dp = 15.dp,
              isCircle:Boolean = false,
              imageHeight:Dp = 135.dp,
              borderRadius:Dp = 10.dp,
              spacingBetweenImageAndDots:Dp = 10.dp
              ) {
    val count = imagesResources.size

    val state = rememberPagerState(initialPage = 0) { count }
    val md  = Modifier

        Column(md.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(spacingBetweenImageAndDots)){
            HorizontalPager(
                state = state,
                md.fillMaxWidth()
            ) { page ->
                Box(modifier = Modifier.fillMaxWidth().padding( end = if(state.isScrollInProgress) 15.dp else 0.dp)) {
                      Image(
                      painter = painterResource(id = imagesResources[page]),
                      contentDescription = null,
                          modifier = md.height(imageHeight).clip(RoundedCornerShape(borderRadius)
                              ),
                          contentScale = ContentScale.FillWidth
                        )
                }
            }
            Dots(count,state.currentPage,imageActiveColor, imageInactiveColor, dotHeight, dotWidth, dotBorderRadius,isCircle)
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
    dotBorderRadius: Dp,
    isCircle:Boolean
){
        Box(contentAlignment = Alignment.Center){
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)){
                items(count = total){dot->
                    val dotColor by animateColorAsState(
                        targetValue = if (dot == currentPage) imageActiveColor else imageInactiveColor,
                        label = ""
                    )

                    print.log("Pager_", "current dot: $dot, currentPage: $currentPage")
                    Dot(dotColor, dotHeight, dotWidth, dotBorderRadius, isCircle)
                }
            }
        }
    }

@Composable
fun Dot(dotColor: Color = MainYellow,
        dotHeight: Dp = 20.dp, dotWidth: Dp = 20.dp,
        dotBorderRadius: Dp = 10.dp, isCircle:Boolean = true) {
    Box(modifier = Modifier
        .width(dotWidth)
        .height(if (isCircle)dotWidth else dotHeight)
        .background(color = dotColor, shape = if(!isCircle) RoundedCornerShape(dotBorderRadius) else CircleShape))
}