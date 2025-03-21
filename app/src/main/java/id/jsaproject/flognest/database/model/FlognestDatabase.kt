package id.jsaproject.flognest.database.model

import androidx.room.Database
import androidx.room.RoomDatabase
import id.jsaproject.flognest.database.dao.ContentDao

@Database(
    entities = [
        Content::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class FlognestDatabase : RoomDatabase() {
    abstract fun contentDao(): ContentDao
}