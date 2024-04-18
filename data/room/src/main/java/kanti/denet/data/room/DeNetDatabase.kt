package kanti.denet.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import kanti.denet.data.room.node.NodeDao
import kanti.denet.data.room.node.NodeEntity

@Database(
    entities = [ NodeEntity::class ],
    version = 1,
    exportSchema = true
)
abstract class DeNetDatabase : RoomDatabase() {

    abstract fun nodeDao(): NodeDao
}