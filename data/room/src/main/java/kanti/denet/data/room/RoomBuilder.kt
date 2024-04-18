package kanti.denet.data.room

import android.content.Context
import androidx.room.Room

private const val databaseName = "database-denet"

fun databaseBuilder(context: Context): DeNetDatabase {
    return Room.databaseBuilder(
        context = context,
        klass = DeNetDatabase::class.java,
        name = databaseName
    ).build()
}