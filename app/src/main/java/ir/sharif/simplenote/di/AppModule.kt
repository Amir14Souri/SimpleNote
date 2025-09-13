package ir.sharif.simplenote.di

import android.content.Context
import androidx.work.WorkManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.sharif.simplenote.data.remote.client.AuthApi
import ir.sharif.simplenote.data.remote.client.NotesApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import javax.inject.Singleton
import ir.sharif.simplenote.database.Database
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import ir.sharif.simplenote.data.repository.NoteRepositoryImpl
import ir.sharif.simplenote.database.Notes
import ir.sharif.simplenote.database.NotesQueries
import ir.sharif.simplenote.domain.repository.NoteRepository
import ir.sharif.simplenote.util.LocalDateTimeAdapter


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(interceptors: Set<@JvmSuppressWildcards Interceptor>, gson: Gson): Retrofit {
        val httpClientBuilder = OkHttpClient.Builder()

        for (interceptor in interceptors) {
            httpClientBuilder.addInterceptor(interceptor)
        }

        httpClientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

        val client = httpClientBuilder.build()

        return Retrofit.Builder()
            .baseUrl("http://188.245.205.57:8000")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideNotesApi(apiClient: ApiClient): NotesApi {
        return provideRetrofit(emptySet(), GsonBuilder().create()).create(NotesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthApi(apiClient: ApiClient): AuthApi {
        return provideRetrofit(emptySet(), GsonBuilder().create()).create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): Database {
        return Database(
            driver = AndroidSqliteDriver(
                schema = Database.Schema,
                context = context,
                name = "app.db"
            ),
            notesAdapter = Notes.Adapter(
               created_atAdapter = LocalDateTimeAdapter, updated_atAdapter = LocalDateTimeAdapter,
            )
        )
    }

    @Provides
    @Singleton
    fun provideUserQueries(db: Database): NotesQueries  {
        return db.notesQueries
    }

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteRepositoryImpl: NoteRepositoryImpl): NoteRepository = noteRepositoryImpl
}