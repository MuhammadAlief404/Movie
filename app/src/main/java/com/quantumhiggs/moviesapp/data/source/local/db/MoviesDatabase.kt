package com.quantumhiggs.moviesapp.data.source.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.quantumhiggs.moviesapp.data.source.local.entity.*

@Database(
    entities = [MovieEntity::class, PopularEntity::class, NowPlayingEntity::class, TopRatedEntity::class, FavoriteEntity::class, ReviewEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieDao(): MoviesDao

    companion object {
        @Volatile
        private var INSTANCE: MoviesDatabase? = null

        fun getInstance(context: Context): MoviesDatabase {
            if (INSTANCE == null) {
                synchronized(MoviesDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            MoviesDatabase::class.java, "Movies.db"
                        ).build()
                    }
                }
            }
            return INSTANCE as MoviesDatabase
        }
    }
}