package com.dhrutikambar.filemanagerpro.presentation.viewmodel

import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhrutikambar.filemanagerpro.domain.model.FileModel
import com.dhrutikambar.filemanagerpro.domain.use_case.ScanDirectoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val scanDirectoryUseCase: ScanDirectoryUseCase) :
    ViewModel() {
    private val _fileViewModel = MutableLiveData<FileModel>()
    val fileModel: LiveData<FileModel> = _fileViewModel

    init {

        scanRootDirectory()
    }

   /* fun scanDirectory(path: String) {
        viewModelScope.launch {
            scanDirectoryUseCase.execute(path)
        }
    }*/

    fun scanRootDirectory(){

        viewModelScope.launch {
            val root = Environment.getExternalStorageDirectory()
            _fileViewModel.value = scanDirectoryUseCase.execute(root)
        }
    }
}