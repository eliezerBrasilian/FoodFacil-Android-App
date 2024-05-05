package com.foodfacil.components

import android.Manifest
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.azmithabet.circleimageviewcompose.CircleImage
import com.foodfacil.R
import com.foodfacil.api.updateProfilePicture
import com.foodfacil.datastore.StoreUserData
import com.foodfacil.services.Print
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch

@SuppressLint("PermissionLaunchedDuringComposition")
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
 fun PerfilTop(
    md: Modifier,
    user: String?,
    userPhoto: String?,
    userName: String?,
    userToken: String?,
    userId: String?,
    storeUserData: StoreUserData,

    ){
    val TAG = "Profile"
    val print = Print(TAG)

    var selectedImage by remember {
        mutableStateOf<Uri?>(null)
    }

    val readImagePermissionState = rememberPermissionState(
        permission = Manifest.permission.READ_MEDIA_IMAGES
    )

    val PERMISSAO_READ_IMAGES = storeUserData.getPermissaoReadImageStatus.collectAsState(initial = "NEGADO")

    val scope = rememberCoroutineScope()

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            selectedImage = uri
            print.log("CAPA", uri.toString())
        }

    val launcherPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            launcher.launch(arrayOf("image/*"))
            print.log("PERMISSION JÁ GRANTED")
        } else {
            scope.launch {
                print.log("PERMISSION GRANTED")
                storeUserData.savePermissionReadImage("PERMITIDO")
            }
        }
    }

    val escolheFotoDePerfil: ()->Unit = {
        if (readImagePermissionState.status.isGranted ||
            Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU || PERMISSAO_READ_IMAGES.value == "PERMITIDO"
        ) {
            launcher.launch(arrayOf("image/*"))
        } else {
            launcherPermission.launch(Manifest.permission.READ_MEDIA_IMAGES)
        }
    }

    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    val salvar: ()->Unit = {
        coroutineScope.launch {
            updateProfilePicture(selectedImage.toString(), userToken, userId, context)
            selectedImage = null
        }
    }

    val descartar = {
        selectedImage = null
    }

    Box(
        modifier = md
            .height(180.dp)
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 20.dp), contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            if (userPhoto == null || userPhoto == "") {
                CircleImage(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = null, size = 70.dp, clickable = true,
                    onClickImage = escolheFotoDePerfil
                )
            } else {
                AsyncImage(
                    model = if (selectedImage == null) userPhoto else selectedImage,
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .clickable { escolheFotoDePerfil() }
                )
                if (selectedImage != null) {

                    ImageButtonsRow(descartar, salvar)
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            val nameLength = userName.toString().length

            Text(
                if(nameLength > 20) userName.toString().take(20) + "..." else userName.toString(),
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}