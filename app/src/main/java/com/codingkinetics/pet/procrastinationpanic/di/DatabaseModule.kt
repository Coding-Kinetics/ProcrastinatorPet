package com.codingkinetics.pet.procrastinationpanic.di

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.codingkinetics.pet.procrastinationpanic.Database
import com.codingkinetics.pet.procrastinationpanic.tasks.data.sources.TaskDataLocalSource
import com.codingkinetics.pet.procrastinationpanic.tasks.data.sources.TaskLocalSource
import com.codingkinetics.pet.procrastinationpanic.util.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): Database {
        return Database(
            driver =
                AndroidSqliteDriver(
                    Database.Schema,
                    context,
                    "Task.db",
                    callback =
                        object : AndroidSqliteDriver.Callback(Database.Schema) {
                            override fun onOpen(db: SupportSQLiteDatabase) {
                                super.onOpen(db)
                                db.setForeignKeyConstraintsEnabled(true)
                            }
                        },
                ),
        )
    }

    @Provides
    fun provideTaskLocalDataSource(
        database: Database,
        logger: Logger,
    ): TaskLocalSource {
        return TaskDataLocalSource(
            database,
            logger,
        )
    }
}
