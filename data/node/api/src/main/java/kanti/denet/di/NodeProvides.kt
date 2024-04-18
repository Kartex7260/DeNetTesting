package kanti.denet.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.kotlincrypto.core.Digest
import org.kotlincrypto.hash.sha3.Keccak256
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NodeProvides {

    @Singleton
    @Provides
    fun provideKeccak256(): Digest {
        return Keccak256()
    }
}