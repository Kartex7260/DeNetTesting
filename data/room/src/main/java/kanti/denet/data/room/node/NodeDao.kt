package kanti.denet.data.room.node

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NodeDao {

    @Query("SELECT * FROM node WHERE hash = :hash LIMIT 1")
    suspend fun getNode(hash: String): NodeEntity?

    @Query("SELECT * FROM node WHERE parent_hash = :parentHash")
    suspend fun getChildren(parentHash: String): List<NodeEntity>

    @Query("SELECT * FROM node WHERE parent_hash IS null")
    suspend fun getRootChildren(): List<NodeEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(node: NodeEntity): Long

    @Query("DELETE FROM node WHERE hash = :hash")
    suspend fun delete(hash: String)

    @Query("DELETE FROM node WHERE parent_hash = :parentHash")
    suspend fun deleteChildren(parentHash: String?)
}