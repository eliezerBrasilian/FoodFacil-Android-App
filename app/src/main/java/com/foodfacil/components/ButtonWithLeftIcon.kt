import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.foodfacil.R

@Composable
fun ButtonWithLeftIcon(
    imageResource: Int = R.drawable.ic_launcher_background,
    background: Color = Color.Red,
    text: String = "Eu sou um botão",
    fontSize: TextUnit = 18.sp,
    textColor: Color = Color.Black,
    padding:Dp = 10.dp,
    isOutline:Boolean = false,
    borderColor: Color = Color.Black,
    borderWidth:Dp = 1.dp,
    marginHorizontal:Dp = 0.dp,
    isLoading:Boolean = false,
    progressIndicatorColor:Color = Color.Magenta,
    progressIndicatorSize:Dp = 23.dp,
    onClick: () -> Unit = {}
){
    val md = Modifier

    if(!isOutline)
    Box(
        md
            .fillMaxWidth()
            .padding(horizontal = marginHorizontal)){
        Button(onClick = onClick, colors = ButtonDefaults.buttonColors(background),
            modifier = md.fillMaxWidth()){
            Box(contentAlignment = Alignment.Center){
                if(isLoading) CircularProgressIndicator(md.size(progressIndicatorSize), color = progressIndicatorColor) else Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically, modifier = md.padding(padding)) {
                    Image(painter = painterResource(id = imageResource), contentDescription = null, modifier = md.size(20.dp))
                    Text(text = text,fontSize = fontSize, color = textColor )
                }
            }
        }
    }

    else{
        Box(
            md
                .fillMaxWidth()
                .padding(horizontal = marginHorizontal)){
            Button(onClick = onClick, colors = ButtonDefaults.buttonColors(background),
                modifier = md.fillMaxWidth(), border = BorderStroke(width = borderWidth, color = borderColor,)
            ){
                Box(contentAlignment = Alignment.Center){
                    if(isLoading) CircularProgressIndicator(md.size(progressIndicatorSize), color = progressIndicatorColor)
                        else {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = md.padding(padding)
                        ) {
                            Image(
                                painter = painterResource(id = imageResource),
                                contentDescription = null,
                                modifier = md.size(20.dp)
                            )
                            Text(text = text, fontSize = fontSize, color = textColor)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ButtonWithLeftIconPreview(
    imageResource: Int = R.drawable.ic_launcher_background,
    background: Color = Color.White,
    text: String = "Eu sou um botão",
    fontSize: TextUnit = 18.sp,
    textColor: Color = Color.Black,
    padding:Dp = 10.dp,
    isOutline:Boolean = true,
    borderColor: Color = Color.Black,
    borderWidth:Dp = 1.dp,
    marginHorizontal:Dp = 0.dp,
    isLoading:Boolean = true,
    progressIndicatorColor:Color = Color.Magenta,
    progressIndicatorSize:Dp = 23.dp,
    onClick: () -> Unit = {}
){
    val md = Modifier

    if(!isOutline)
        Box(
            md
                .fillMaxWidth()
                .padding(horizontal = marginHorizontal)){
            Button(onClick = onClick, colors = ButtonDefaults.buttonColors(background), enabled = !isLoading,
                modifier = md.fillMaxWidth()){
                Box(contentAlignment = Alignment.Center){
                    if(isLoading) CircularProgressIndicator(md.size(progressIndicatorSize), color = progressIndicatorColor) else Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically, modifier = md.padding(padding)) {
                        Image(painter = painterResource(id = imageResource), contentDescription = null, modifier = md.size(20.dp))
                        Text(text = text,fontSize = fontSize, color = textColor )
                    }
                }
            }
        }

    else{
        Box(
            md
                .fillMaxWidth()
                .padding(horizontal = marginHorizontal)){
            Button(onClick = onClick, colors = ButtonDefaults.buttonColors(background),
                modifier = md.fillMaxWidth(), border = BorderStroke(width = borderWidth, color = borderColor)
            ){

                Box(contentAlignment = Alignment.Center){
                    if(isLoading) CircularProgressIndicator(md.size(progressIndicatorSize), color = progressIndicatorColor)
                        else Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically, modifier = md.padding(padding)) {
                        Image(painter = painterResource(id = imageResource), contentDescription = null, modifier = md.size(20.dp))
                        Text(text = text,fontSize = fontSize, color = textColor )
                    }
                }

            }
        }
    }

}