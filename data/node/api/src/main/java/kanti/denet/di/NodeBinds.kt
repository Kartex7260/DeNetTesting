package kanti.denet.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kanti.denet.data.model.node.NodeRepository
import kanti.denet.data.model.node.NodeRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NodeBinds {

    @Singleton
    @Binds
    fun bindNodeRepositoryImpl(repository: NodeRepositoryImpl): NodeRepository
}