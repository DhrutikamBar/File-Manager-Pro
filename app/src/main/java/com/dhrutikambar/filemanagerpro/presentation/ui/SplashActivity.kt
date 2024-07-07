package com.dhrutikambar.filemanagerpro.presentation.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.dhrutikambar.filemanagerpro.R
import com.dhrutikambar.filemanagerpro.presentation.ui.ui.theme.FileManagerProTheme

class RequestPermissionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FileManagerProTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SplashScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun SplashScreen(modifier: Modifier) {
    val context = LocalContext.current
    val permissions = arrayOf(Manifest.permission.READ_MEDIA_IMAGES)

    val isPermissionGranted = remember {
        mutableStateOf(isAllPermissionGranted(context = context, permissions = permissions))
    }

    val openDialog = remember {
        mutableStateOf(false)
    }

    if (isPermissionGranted.value) {
        goToMainActivity(context)
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
            if (it) {
                goToMainActivity(context)
            }
        }




    Column(
        modifier = Modifier
            .fillMaxSize()
            .absolutePadding(top = 30.dp, bottom = 70.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
            MultiplePermissionUI()
        } else {
            SinglePermissionUI(permissionLauncher)
        }


    }

}

@Composable
fun SinglePermissionUI(permissionLauncher: ManagedActivityResultLauncher<String, Boolean>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .absolutePadding(top = 250.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_folder),
            contentDescription = "fileManager", modifier = Modifier.size(100.dp)
        )
        Text(text = "File Manager Pro", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "File Manager Pro required storage permission to show exact usage & statics of your device storage properly.",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .absolutePadding(left = 15.dp, right = 15.dp),
            textAlign = TextAlign.Center
        )
    }


    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "")

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .absolutePadding(left = 15.dp, right = 15.dp),
            onClick = {
                permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            }) {
            Text(text = "ALLOW")
        }
    }
}

@Composable
fun MultiplePermissionUI() {

}

@Composable
fun ShowPermissionDialog(openDialog: MutableState<Boolean>) {

    if (openDialog.value) {
        AlertDialog(onDismissRequest = {

        }, confirmButton = {

        })
    }
}

@Composable
fun RequestPermissionScreen() {

    Box(modifier = Modifier.fillMaxSize()) {

    }
}

fun goToMainActivity(context: Context) {
    val activity = context as Activity
    activity.startActivity(Intent(activity, MainActivity::class.java))
    activity.finish()
}

fun isAllPermissionGranted(context: Context?, permissions: Array<String>): Boolean {
    var isAllGranted = true
    for (permission in permissions) {
        if (ContextCompat.checkSelfPermission(
                context!!, permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            isAllGranted = false
        }
    }
    return isAllGranted
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    FileManagerProTheme {
        Greeting2("Android")
    }
}