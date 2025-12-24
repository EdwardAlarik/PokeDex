package com.edwardalarik.app

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import android.os.Handler
import android.os.Looper
import com.edwardalarik.app.api.db.DB
import com.edwardalarik.app.api.db.DBHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppHiltModule {

    @Provides
    @Singleton
    fun provideExecutionService(): ExecutorService {
        return Executors.newFixedThreadPool(1)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application,
    ): SQLiteDatabase {
        val databaseFile = application.getDatabasePath(DB.DB_NAME)
        val needCreate = !databaseFile.exists()

        return SQLiteDatabase.openOrCreateDatabase(
            databaseFile,
            null
        ).apply {
            if (needCreate) {
                DBHelper().onCreate(this)
            }
            else if (this.version < DB.DB_VERSION) {
                DBHelper().onUpgrade(this,this.version, DB.DB_VERSION)
            }
            this.version = DB.DB_VERSION
        }
    }

    @Provides
    @Singleton
    fun provideHandler(): Handler {
        return Handler(Looper.getMainLooper())
    }

    @Provides
    @Singleton
    fun provideAppDataSource(
        application: Application,
        db: SQLiteDatabase,
        mainThreadHandler: Handler,
        executorService: ExecutorService
    ): AppDataSource {
        return AppDataSource(
            application = application,
            mainThreadHandler = mainThreadHandler,
            executorService = executorService,
            db = db
        )
    }
}