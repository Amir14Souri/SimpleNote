package ir.sharif.simplenote.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import ir.sharif.simplenote.data.remote.AuthMiddleware
import okhttp3.Interceptor

@Module
@InstallIn(SingletonComponent::class)
abstract class InterceptorModule {
    @Binds
    @IntoSet
    abstract fun bindAuthMiddleware(authMiddleware: AuthMiddleware): Interceptor
}