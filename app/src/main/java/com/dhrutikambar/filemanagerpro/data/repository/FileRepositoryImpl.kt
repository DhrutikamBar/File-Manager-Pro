package com.dhrutikambar.filemanagerpro.data.repository

import com.dhrutikambar.filemanagerpro.domain.model.FileModel
import com.dhrutikambar.filemanagerpro.domain.repository.FileRepository
import java.io.File

class FileRepositoryImpl : FileRepository {
    override suspend fun scanDirectory(path: String): FileModel {
        val file = File(path)
        return file.toFileModel()
    }

    private fun File.toFileModel(): FileModel {
        return FileModel(name = this.name, isDirectory = this.isDirectory, path = this.path)
    }
}