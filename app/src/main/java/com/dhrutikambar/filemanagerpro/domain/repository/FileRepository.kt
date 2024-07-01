package com.dhrutikambar.filemanagerpro.domain.repository

import com.dhrutikambar.filemanagerpro.domain.model.FileModel


interface FileRepository {
    suspend fun scanDirectory(path: String):FileModel
}