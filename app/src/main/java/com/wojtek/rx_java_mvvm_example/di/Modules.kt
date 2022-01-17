package com.wojtek.rx_java_mvvm_example.di

import android.content.Context
import androidx.room.Room
import com.wojtek.rx_java_mvvm_example.api.GoogleBookApi
import com.wojtek.rx_java_mvvm_example.data.AppDatabase
import com.wojtek.rx_java_mvvm_example.data.googleBook.GoogleBookDao
import com.wojtek.rx_java_mvvm_example.data.googleBook.GoogleBookRepository
import com.wojtek.rx_java_mvvm_example.data.query.SearchQueryDao
import com.wojtek.rx_java_mvvm_example.data.query.SearchQueryRepository
import com.wojtek.rx_java_mvvm_example.ui.main.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val viewModelModules = module {
    viewModel { MainViewModel(get(), get()) }
}

val networkModules = module {

    val httpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(GoogleBookApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()


    fun provideBookApi(retrofit: Retrofit): GoogleBookApi =
        retrofit.create(GoogleBookApi::class.java)

    single { provideRetrofit() }
    single { provideBookApi(get()) }
}

val repositoryModules = module {
    fun provideGoogleBookRepository(api: GoogleBookApi, dao: GoogleBookDao) =
        GoogleBookRepository(api, dao)

    fun provideSearchQueryRepository(dao: SearchQueryDao) = SearchQueryRepository(dao)

    single { provideGoogleBookRepository(get(), get()) }
    single { provideSearchQueryRepository(get()) }
}

val databaseModule = module {
    fun buildDatabase(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()

    fun provideGoogleBookDao(database: AppDatabase) = database.googleBookDao()
    fun provideSearchQueryDao(database: AppDatabase) = database.searchQueryDao()

    single { buildDatabase(androidApplication()) }
    single { provideGoogleBookDao(get()) }
    single { provideSearchQueryDao(get()) }
}

