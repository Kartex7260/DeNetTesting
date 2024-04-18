package kanti.denet.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kanti.denet.data.model.node.datasource.local.NodeRoomDataSource
import kanti.denet.data.model.node.datasoure.local.NodeLocalDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NodeRoomBinds {

    @Singleton
    @Binds
    fun bindNodeRoomDataSource(dataSource: NodeRoomDataSource): NodeLocalDataSource
}