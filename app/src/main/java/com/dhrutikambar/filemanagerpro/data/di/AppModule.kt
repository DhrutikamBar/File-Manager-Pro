package com.dhrutikambar.filemanagerpro.data.di

import com.dhrutikambar.filemanagerpro.data.repository.FileRepositoryImpl
import com.dhrutikambar.filemanagerpro.domain.repository.FileRepository
import com.dhrutikambar.filemanagerpro.domain.use_case.ScanDirectoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFileRepository(): FileRepository {
        return FileRepositoryImpl()
    }

    @Provides
    fun provideScanDirectoryUseCase(fileRepository: FileRepository):ScanDirectoryUseCase{
        return ScanDirectoryUseCase(fileRepository)
    }

}