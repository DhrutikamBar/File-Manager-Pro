package com.dhrutikambar.filemanagerpro.presentation.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StatFs
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dhrutikambar.filemanagerpro.R
import com.dhrutikambar.filemanagerpro.domain.model.FileCategoryModel
import com.dhrutikambar.filemanagerpro.domain.model.FileModel
import com.dhrutikambar.filemanagerpro.domain.model.StorageInfoModel
import com.dhrutikambar.filemanagerpro.presentation.viewmodel.MainViewModel
import com.dhrutikambar.filemanagerpro.ui.theme.FileManagerProTheme
import com.dhrutikambar.filemanagerpro.ui.theme.color1
import com.dhrutikambar.filemanagerpro.ui.theme.color1Light
import com.dhrutikambar.filemanagerpro.ui.theme.color1LightForStorageProgress
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            FileManagerProTheme {
                Scaffold(topBar = {
                    TopBar()
                }, modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //  MainScreen(modifier = Modifier.padding(innerPadding))

                    HomeScreenNew(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun HomeScreenNew(modifier: Modifier) {
    val context = LocalContext.current
    val storageInfo = remember {
        mutableStateOf(getStorageInfo(context))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .absolutePadding(top = 100.dp, bottom = 60.dp)
    ) {

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                StorageDetailsComponent(storageInfo)
            }

            item {
                StorageCategoryDetailsComponent()
            }

            item {
                RecentFilesComponent()
            }

        }

    }

}

@RequiresApi(Build.VERSION_CODES.S)
fun getStorageInfo(context: Context): StorageInfoModel {
    val stat = StatFs(Environment.getExternalStorageDirectory().path)
    val blockSize: Long
    val totalBlocks: Long
    val availableBlocks: Long


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
        blockSize = stat.blockSizeLong
        totalBlocks = stat.blockCountLong
        availableBlocks = stat.availableBlocksLong
    } else {
        blockSize = stat.blockSize.toLong()
        totalBlocks = stat.blockCount.toLong()
        availableBlocks = stat.availableBlocks.toLong()
    }

    val totalStorage = totalBlocks * blockSize
    val freeStorage = availableBlocks * blockSize
    val usedStorage = totalStorage - freeStorage

    val totalStorageBytes = totalBlocks * blockSize
    val freeStorageBytes = availableBlocks * blockSize
    val usedStorageBytes = totalStorageBytes - freeStorageBytes


    val totalStorageGB = totalStorageBytes.toDouble() / (1024.0 * 1024.0 * 1024.0)
    val freeStorageGB = freeStorageBytes.toDouble() / (1024.0 * 1024.0 * 1024.0)
    val usedStorageGB = usedStorageBytes.toDouble() / (1024.0 * 1024.0 * 1024.0)

    return StorageInfoModel(
        freeStorage = freeStorageGB,
        totalStorage = totalStorageGB,
        usedStorage = usedStorageGB
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StorageCategoryDetailsComponent() {
    val categoriesList = ArrayList<FileCategoryModel>().apply {
        add(
            FileCategoryModel(
                categoryTitle = "Images",
                categoryType = "image",
                categoryIcon = R.drawable.ic_image,
                categoryPath = "",
                storageOccupied = "",
                totalFilesCount = ""
            )
        )

        add(
            FileCategoryModel(
                categoryTitle = "Videos",
                categoryType = "video",
                categoryIcon = R.drawable.ic_video,
                categoryPath = "",
                storageOccupied = "",
                totalFilesCount = ""
            )
        )

        add(
            FileCategoryModel(
                categoryTitle = "Audio files",
                categoryType = "audio",
                categoryIcon = R.drawable.ic_music,
                categoryPath = "",
                storageOccupied = "",
                totalFilesCount = ""
            )
        )

        add(
            FileCategoryModel(
                categoryTitle = "Documents",
                categoryType = "docs",
                categoryIcon = R.drawable.ic_document,
                categoryPath = "",
                storageOccupied = "",
                totalFilesCount = ""
            )
        )

        add(
            FileCategoryModel(
                categoryTitle = "Downloads",
                categoryType = "downloads",
                categoryIcon = R.drawable.ic_download,
                categoryPath = "",
                storageOccupied = "",
                totalFilesCount = ""
            )
        )

        add(
            FileCategoryModel(
                categoryTitle = "APK files",
                categoryType = "apk",
                categoryIcon = R.drawable.ic_apk,
                categoryPath = "",
                storageOccupied = "",
                totalFilesCount = ""
            )
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .absolutePadding(left = 15.dp, right = 15.dp, top = 20.dp)
    ) {
        Text(text = "Categories", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(
            maxItemsInEachRow = 3,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceAround
        ) {
            repeat(categoriesList.size) {
                CategoryItemComponent(categoriesList[it])
            }

        }
    }
}

@Composable
fun CategoryItemComponent(data: FileCategoryModel) {
    val itemWidth = LocalConfiguration.current.screenWidthDp.div(4)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(itemWidth.dp)
            .absolutePadding(top = 10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color = color1Light, shape = RoundedCornerShape(50)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = data.categoryIcon),
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp)

            )
        }

        Spacer(modifier = Modifier.height(5.dp))
        Text(text = data.categoryTitle, fontSize = 12.sp)
    }
}

@Composable
fun RecentFilesComponent() {
    val dividerWidth = LocalConfiguration.current.screenWidthDp.div(1.1)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .absolutePadding(left = 15.dp, right = 15.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Recent files", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = "View all", fontWeight = FontWeight.Normal, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(20.dp))

        repeat(25) {
            RecentFileItemComponent(it)
            Box(
                modifier = Modifier
                    .width(dividerWidth.dp)
                    .height(1.dp)
                    .background(Color.Gray)
            )
        }
    }
}

@Composable
fun RecentFileItemComponent(fileIndex: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(.15f),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_file),
                contentDescription = "",
                modifier = Modifier
                    .size(32.dp),
                colorFilter = ColorFilter.tint(color = color1LightForStorageProgress)

            )
        }

        Column(modifier = Modifier.weight(.7f)) {
            Text(
                text = "File $fileIndex.Jpg",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color.Black
            )
            Text(
                text = "16 Jun, 12:40 pm",
                fontSize = 10.sp,
                color = Color.Gray
            )
        }

        Text(
            text = "11 MB",
            fontSize = 12.sp,
            color = Color.Gray, modifier = Modifier.weight(.15f)
        )
    }
}

@Composable
fun StorageProgressComponent(usedPercentage: Float) {

    Box(modifier = Modifier.size(110.dp), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(110.dp)) {
            val strokeWidth = 15.dp.toPx()
            val radius = size.minDimension / 2 - strokeWidth / 2

            // incomplete part
            drawArc(
                color = color1LightForStorageProgress,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
            )

            // completed part

            drawArc(
                color = color1,
                startAngle = -90f,
                sweepAngle = 360f * usedPercentage.div(100),
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )


        }

        Text(
            text = "${usedPercentage.toString().substring(0, 4).trim()}%",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.absolutePadding(left = 2.dp)
        )

    }
}


@Composable
fun StorageDetailsComponent(storageInfo: MutableState<StorageInfoModel>) {
    val usedPercentage =
        (storageInfo.value.usedStorage.toFloat() / storageInfo.value.totalStorage.toFloat()) * 100
    Log.d("USED_STORAGE", storageInfo.value.usedStorage.toString())
    Log.d("FREE_STORAGE", storageInfo.value.freeStorage.toString())
    Log.d("TOTAL_STORAGE", storageInfo.value.totalStorage.toString())

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .absolutePadding(left = 15.dp, right = 15.dp)
            .background(color = color1Light, shape = RoundedCornerShape(11.dp)),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        StorageProgressComponent(usedPercentage)
        Column {
            Text(text = "Cloud Storage Unavailable", fontSize = 12.sp)
            Text(
                text = "${
                    storageInfo.value.usedStorage.toString().substring(0, 4).trim()
                }GB of ${
                    storageInfo.value.totalStorage.toString().substring(0, 4).trim()
                }GB used",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "Run Scan",
                fontSize = 12.sp,
                color = color1,
                fontWeight = FontWeight.SemiBold, modifier = Modifier.clickable {

                }
            )


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(title = {
        Text(
            text = "Manage your files",
            modifier = Modifier
                .fillMaxWidth()
                .absolutePadding(left = 15.dp, right = 15.dp),
            textAlign = TextAlign.Start, fontWeight = FontWeight.Bold, fontSize = 30.sp
        )
    }, actions = {

    }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White))
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
    val context = LocalContext.current
    Log.d("FILE_TREE", "1")
    val dividerSize = LocalConfiguration.current.screenWidthDp.div(1.2)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                Toast
                    .makeText(context, "${fileModel.path} ", Toast.LENGTH_LONG)
                    .show()
            }, horizontalAlignment = Alignment.CenterHorizontally

    ) {
        FileOrFolderItem(fileModel)
        Box(
            modifier = Modifier
                .width(dividerSize.dp)
                .height(1.dp)
                .background(Color.Gray)
                .absolutePadding()
        )

        fileModel.children?.forEachIndexed { index, fileModel ->
            FileTree(fileModel = fileModel, isScanning = isScanning)

        }
        /*if (fileModel.isDirectory && !fileModel.name.trimStart().startsWith('.')) {

            FileTree(fileModel = fileModel, isScanning = isScanning)

            Log.d("FILE_TREE", "2")
            fileModel.children?.forEachIndexed { index, fileModel ->
                Log.d("FILE_TREE", "3 $index")
                FileTree(fileModel = fileModel, isScanning = isScanning)
            }
            Log.d("FILE_TREE", "4")
        }*/
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