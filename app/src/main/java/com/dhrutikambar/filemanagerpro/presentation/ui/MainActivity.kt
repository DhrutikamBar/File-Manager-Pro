package com.dhrutikambar.filemanagerpro.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dhrutikambar.filemanagerpro.R
import com.dhrutikambar.filemanagerpro.domain.model.FileModel
import com.dhrutikambar.filemanagerpro.presentation.viewmodel.MainViewModel
import com.dhrutikambar.filemanagerpro.ui.theme.FileManagerProTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            FileManagerProTheme {
                Scaffold(topBar = {
                    TopBar()
                }, modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(title = {
        Text(
            text = "File Manager Pro", color =
            Color.DarkGray
        )
    }, actions = {
    }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Cyan))
}

@Composable
fun MainScreen(modifier: Modifier) {
    val viewModel: MainViewModel = hiltViewModel()
    val fileModel by viewModel.fileModel.observeAsState()
    val isScanning = remember {
        mutableStateOf(false)
    }
    /*  val textFieldValue = remember {
          mutableStateOf("")
      }

      LaunchedEffect(key1 = textFieldValue.value) {
          if (textFieldValue.value.isNotEmpty()) {
              viewModel.scanDirectory(textFieldValue.value)
              isScanning.value = true
          } else {
              isScanning.value = false
          }

      }

      Box(
          modifier = Modifier
              .fillMaxSize()
              .absolutePadding(left = 11.dp, right = 11.dp),
          contentAlignment = Alignment.TopCenter
      ) {

          Column(modifier = Modifier.fillMaxWidth()) {
              Spacer(modifier = Modifier.height(100.dp))
              TextField(value = textFieldValue.value, onValueChange = {
                  textFieldValue.value = it
              }, label = {
                  Text(text = "Enter directory path")
              }, modifier = Modifier.fillMaxWidth(), trailingIcon = {
                  if (isScanning.value) {
                      CircularProgressIndicator(
                          modifier = Modifier.size(25.dp),
                          color = Color.Gray,
                          strokeWidth = 2.dp
                      )
                  }
              })
          }


      }*/

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray), contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .absolutePadding(left = 11.dp, right = 11.dp, top = 100.dp, bottom = 50.dp)
                .background(color = Color.White, shape = RoundedCornerShape(11.dp))
        ) {
            item {
                fileModel?.let {
                    FileTree(it, isScanning)
                }

            }

        }
    }


}

@Composable
fun FileTree(fileModel: FileModel, isScanning: MutableState<Boolean>) {
    Log.d("FILE_TREE", "1")
    val dividerSize = LocalConfiguration.current.screenWidthDp.div(1.2)
    Column(
        modifier = Modifier
            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally

    ) {
        FileOrFolderItem(fileModel)
        Box(
            modifier = Modifier
                .width(dividerSize.dp)
                .height(1.dp)
                .background(Color.Gray)
                .absolutePadding()
        )
        if (fileModel.isDirectory) {
            Log.d("FILE_TREE", "2")
            fileModel.children?.forEachIndexed { index, fileModel ->
                Log.d("FILE_TREE", "3 $index")
                FileTree(fileModel = fileModel, isScanning = isScanning)
            }
            Log.d("FILE_TREE", "4")
        }
        Log.d("FILE_TREE", "5")
        isScanning.value = false
    }

}

@Composable
fun FileOrFolderItem(fileModel: FileModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        if (fileModel.isDirectory) {
            Image(
                painter = painterResource(id = R.drawable.ic_folder),
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_file),
                contentDescription = "",
                modifier = Modifier.size(30.dp),
                colorFilter = ColorFilter.tint(color = Color.Yellow)
            )
        }

        Spacer(modifier = Modifier.width(20.dp))
        Text(text = fileModel.name)

    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FileManagerProTheme {
        MainScreen(Modifier)
    }
}