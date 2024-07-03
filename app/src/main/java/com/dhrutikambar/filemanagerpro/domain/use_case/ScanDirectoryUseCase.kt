package com.dhrutikambar.filemanagerpro.domain.use_case

import com.dhrutikambar.filemanagerpro.domain.repository.FileRepository
import com.dhrutikambar.filemanagerpro.domain.model.FileModel
import java.io.File

class ScanDirectoryUseCase(private val fileRepository: FileRepository) {
    /*suspend fun execute(path: String): FileModel {
        return fileRepository.scanDirectory(path)
    }*/

    fun execute(rootDirectory: File): FileModel {

        val children = rootDirectory.listFiles()?.map {

            if (it.isDirectory) {
                execute(it)
            } else {
                FileModel(it.name, path = it.path, isDirectory = false)
            }

        } ?: emptyList()

        return FileModel(
            name = rootDirectory.name,
            path = rootDirectory.path,
            isDirectory = true,
            children = children
        )
    }
}