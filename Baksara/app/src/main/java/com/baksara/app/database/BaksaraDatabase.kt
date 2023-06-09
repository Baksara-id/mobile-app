package com.baksara.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Modul::class, Pelajaran::class, SoalBaca::class, SoalGambar::class, SoalPilihan::class, Penggunaan::class, KamusBelajar::class, Kamus::class],
    version = 2,
    exportSchema = false
)
abstract class BaksaraDatabase: RoomDatabase() {

    abstract fun baksaraDao(): BaksaraDao

    companion object {
        @Volatile
        private var INSTANCE: BaksaraDatabase? = null

        fun getDatabase(context: Context): BaksaraDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BaksaraDatabase::class.java,
                    "baksara_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }

        fun destroyDatabase(context: Context){
            val dbFile = context.getDatabasePath("baksara_db")
            dbFile.delete()
            INSTANCE = null
        }
    }
}