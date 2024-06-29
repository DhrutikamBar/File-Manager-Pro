package com.dhrutikambar.filemanagerpro.domain.model

data class FileModel(
    val name: String,
    val path: String,
    val isDirectory: Boolean,
    val children: List<FileModel>? = emptyList()
) {


}