package kanti.denet.data.room.node

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "node")
data class NodeEntity(
    @PrimaryKey val hash: String = "",
    @ColumnInfo(name = "parent_hash") val parentHash: String? = null
)
