package com.codingkinetics.pet.procrastinationpanic.di

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.codingkinetics.pet.procrastinationpanic.Database
import com.codingkinetics.pet.procrastinationpanic.pet.data.sources.PetDataLocalSource
import com.codingkinetics.pet.procrastinationpanic.pet.data.sources.PetLocalSource
import com.codingkinetics.pet.procrastinationpanic.tasks.data.sources.TaskDataLocalSource
import com.codingkinetics.pet.procrastinationpanic.tasks.data.sources.TaskLocalSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    @Named("TaskDatabase")
    fun provideTaskDatabase(@ApplicationContext context: Context): Database = Database(
        AndroidSqliteDriver(
            Database.Schema,
            context,
            "Task.db",
            callback = object : AndroidSqliteDriver.Callback(Database.Schema) {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    db.setForeignKeyConstraintsEnabled(true)
                }
            },
        ),
    )

    @Provides
    @Singleton
    @Named("PetDatabase")
    fun providePetDatabase(@ApplicationContext context: Context): Database = Database(
        AndroidSqliteDriver(
            Database.Schema,
            context,
            "Pet.db",
            callback = object : AndroidSqliteDriver.Callback(Database.Schema) {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    db.setForeignKeyConstraintsEnabled(true)
                }
            },
        ),
    )
}
