package ir.sharif.simplenote.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.sharif.simplenote.data.repository.NoteRepositoryImpl
import ir.sharif.simplenote.data.repository.UserRepositoryImpl
import ir.sharif.simplenote.domain.repository.NoteRepository
import ir.sharif.simplenote.domain.repository.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

}