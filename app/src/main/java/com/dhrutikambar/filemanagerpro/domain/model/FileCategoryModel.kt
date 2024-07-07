package com.dhrutikambar.filemanagerpro.domain.model

class FileCategoryModel(
    val categoryTitle: String,
    val categoryType: String,
    val categoryIcon: Int,
    val categoryPath: String,
    val storageOccupied: String,
    val totalFilesCount: String
) {
}