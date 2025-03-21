package id.jsaproject.flognest.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.jsaproject.flognest.database.dao.ContentDao
import id.jsaproject.flognest.database.model.FlognestDatabase
import id.jsaproject.flognest.database.repository.ContentRepository
import id.jsaproject.flognest.database.repository.DefaultContentRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindContentRepository(repository: DefaultContentRepository): ContentRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): FlognestDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            FlognestDatabase::class.java,
            "flognest.db"
        ).build()
    }

    @Provides
    fun provideContentDao(database: FlognestDatabase): ContentDao = database.contentDao()
}