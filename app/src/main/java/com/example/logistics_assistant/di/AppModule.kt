package com.example.logistics_assistant.di

import android.content.Context
import com.example.logistics_assistant.database.AppDatabase
import com.example.logistics_assistant.database.StatisticsDao
import com.example.logistics_assistant.database.TasksDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context,
    ): AppDatabase {
        return AppDatabase.getDatabase(appContext, CoroutineScope(Dispatchers.IO))
    }

    @Provides
    fun provideTasksDao(database: AppDatabase): TasksDao {
        return database.tasksDao()
    }

    @Provides
    fun provideStatisticsDao(database: AppDatabase): StatisticsDao {
        return database.statisticsDao()
    }
}