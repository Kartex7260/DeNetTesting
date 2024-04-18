package kanti.denet.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kanti.denet.data.room.DeNetDatabase
import kanti.denet.data.room.databaseBuilder
import kanti.denet.data.room.node.NodeDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomProvides {

    @Singleton
    @Provides
    fun provideDeNetDatabase(@ApplicationContext context: Context): DeNetDatabase {
        return databaseBuilder(context)
    }

    @Singleton
    @Provides
    fun providesNodeDao(database: DeNetDatabase): NodeDao = database.nodeDao()
}