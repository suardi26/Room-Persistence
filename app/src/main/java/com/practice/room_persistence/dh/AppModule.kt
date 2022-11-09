package com.practice.room_persistence.dh

import android.content.Context
import androidx.room.Room
import com.practice.room_persistence.dao.NoteDao
import com.practice.room_persistence.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext app: Context
    ): AppDatabase{
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "note_database"
        ).build()
    }


    @Provides
    @Singleton
    fun provideNoteDao(
        db: AppDatabase
    ): NoteDao{
        return db.getNoteDao()
    }
}