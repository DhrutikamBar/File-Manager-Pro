package com.dhrutikambar.filemanagerpro.domain.use_case

import com.dhrutikambar.filemanagerpro.domain.repository.FileRepository
import com.dhrutikambar.filemanagerpro.domain.model.FileModel

class ScanDirectoryUseCase(private val fileRepository: FileRepository) {
    suspend fun execute(path: String): FileModel {
        return fileRepository.scanDirectory(path)
    }
}