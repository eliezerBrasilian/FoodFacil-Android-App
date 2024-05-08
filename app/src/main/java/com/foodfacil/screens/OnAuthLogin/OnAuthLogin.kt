
import android.app.Activity
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.foodfacil.R
import com.foodfacil.components.TopBarOnAuth
import com.foodfacil.enums.NavigationScreens
import com.foodfacil.services.Print
import com.foodfacil.services.getGoogleLoginAuth
import com.foodfacil.ui.theme.MainRed
import com.foodfacil.ui.theme.MainYellow
import com.foodfacil.viewModel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.simpletext.SimpleText

@Composable
fun OnAuthLogin(navController: NavHostController, authViewModel: AuthViewModel) {
    val md = Modifier

    val  clientId = "191389897644-f2qqgp4g23jsbu5f4sapr8o9n74f8gb7.apps.googleusercontent.com"

    val isLoading by authViewModel.loading.observeAsState(false)

    val context = LocalContext.current
    val googleSignInClient = getGoogleLoginAuth(clientId, LocalContext.current)
    val print = Print()

    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (result.data != null) {
                    val task =
                        GoogleSignIn.getSignedInAccountFromIntent(intent)

                    print.log("Task", task)
                    print.log("Id", task.result.id)
                    handleGoogleSign(task.result,authViewModel, navController,context)
                }
            }
        }


    val onClickGoogleSignIn = {
        startForResult.launch(googleSignInClient.signInIntent)
    }

    Surface(
        md
            .fillMaxSize()
            .padding(top = 50.dp), color = Color.White) {
        Box(md.fillMaxWidth()){
            TopBarOnAuth(iconSize = 25.dp, iconColor = MainYellow){
                navController.popBackStack()
            }
        }
        Column(md.fillMaxHeight(),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.pastel),
                contentDescription = null,
                modifier = md.size(150.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(md.height(20.dp))
            SimpleText("Bem-vindo de volta!", fontSize = 22, fontWeight = "bold")
            Spacer(md.height(20.dp))
            ButtonWithLeftIcon(imageResource = R.drawable.email_icon, text = "Entrar com E-mail", textColor = Color.White ,
                padding = 5.dp,  marginHorizontal = 20.dp,
                onClick = {navController.navigate(NavigationScreens.LOGIN)})
            Spacer(md.height(20.dp))
            ButtonWithLeftIcon(imageResource = R.drawable.google_icon, text = "Entrar com com Google", textColor = MainRed ,
                padding = 5.dp, isOutline = true, background = Color.White, borderColor = MainRed,  marginHorizontal = 20.dp,
                onClick = onClickGoogleSignIn, isLoading = isLoading, progressIndicatorColor = MainYellow
                )
        }
    }
}


private fun handleGoogleSign(
    result: GoogleSignInAccount,
    authViewModel: AuthViewModel,
    navController: NavHostController,
    context: Context
) {

    val navigateToHome: ()->Unit = {
       // navController.navigate(Graph.HOME)
        navController.navigate(NavigationScreens.HOME)
    }
    authViewModel.googleSignIn(
        userUid = result.id!!,
        email = result.email!!,
        name = result.displayName!!,
        profilePicture = result.photoUrl,
        context = context,
        onSuccess = navigateToHome
    )
}