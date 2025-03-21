package id.jsaproject.flognest.database.repository

import id.jsaproject.flognest.di.ApplicationScope
import id.jsaproject.flognest.di.DefaultDispatcher
import id.jsaproject.flognest.database.dao.ContentDao
import id.jsaproject.flognest.database.model.Content
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultContentRepository @Inject constructor(
    private val localDataSource: ContentDao,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationScope private val applicationScope: CoroutineScope
) : ContentRepository {
    override fun getContentsStream(): Flow<List<Content>> {
        return localDataSource.observeAllContents().map { contents ->
            withContext(dispatcher) {
                contents
            }
        }
    }

    override suspend fun getContents(): List<Content> {
        return withContext(dispatcher) {
            localDataSource.getAllContents()
        }
    }

    override fun filterContentByGenreStream(genre: String): Flow<List<Content>> {
        return localDataSource.observeContentsWithGenre(genre).map { contents ->
            withContext(dispatcher) {
                contents
            }
        }
    }

    override suspend fun filterContentByGenre(genre: String): List<Content> {
        return withContext(dispatcher) {
            localDataSource.getContentsWithGenre(genre)
        }
    }

    override fun getFavoriteContentsStream(isFavorite: Boolean): Flow<List<Content>> {
        return localDataSource.observeFavoriteContents().map { contents ->
            withContext(dispatcher) {
                contents
            }
        }
    }

    override suspend fun getFavoriteContents(isFavorite: Boolean): List<Content> {
        return withContext(dispatcher) {
            localDataSource.getFavoriteContents()
        }
    }

    override fun getWatchedContentsStream(isWatched: Boolean): Flow<List<Content>> {
        return localDataSource.observeWatchedContents().map { contents ->
            withContext(dispatcher) {
                contents
            }
        }
    }

    override suspend fun getWatchedContents(isWatched: Boolean): List<Content> {
        return withContext(dispatcher) {
            localDataSource.getWatchedContents()
        }
    }

    override fun filterContentByTypeStream(type: String): Flow<List<Content>> {
        return localDataSource.observeContentsWithType(type).map { contents ->
            withContext(dispatcher) {
                contents
            }
        }
    }

    override suspend fun filterContentByType(type: String): List<Content> {
        return withContext(dispatcher) {
            localDataSource.getContentsWithType(type)
        }
    }

    override fun getContentByIdStream(contentId: String): Flow<Content> {
        return localDataSource.observeContentById(contentId).map { content ->
            withContext(dispatcher) {
                content
            }
        }
    }

    override suspend fun getContentById(contentId: String): Content {
        return withContext(dispatcher) {
            localDataSource.getContentById(contentId) ?: throw Exception("Movie Not Found")
        }
    }

    override fun searchContentStream(
        searchQuery: String?,
        selectedWatched: Boolean?,
        selectedFavorite: Boolean?,
        selectedTypes: String?,
        selectedGenres: String?
    ): Flow<List<Content>> {
        return localDataSource.observeFilteredContents(
            searchQuery,
            selectedWatched,
            selectedFavorite,
            selectedTypes,
            selectedGenres
        ).map { contents ->
            withContext(dispatcher) {
                contents
            }
        }
    }

    override suspend fun searchContent(
        searchQuery: String?,
        selectedWatched: Boolean?,
        selectedFavorite: Boolean?,
        selectedTypes: String?,
        selectedGenres: String?
    ): List<Content> {
        return withContext(dispatcher) {
            localDataSource.getFilteredContents(
                searchQuery,
                selectedWatched,
                selectedFavorite,
                selectedTypes,
                selectedGenres,
            )
        }
    }

    override suspend fun createContent(content: Content) {
        withContext(dispatcher) {
            localDataSource.upsertContent(content)
        }
    }

    override suspend fun insertListContent(contents: List<Content>) {
        withContext(dispatcher) {
            localDataSource.upsertListContent(contents)
        }
    }

    override suspend fun checkIfContentExist(contentId: String): Boolean {
        return withContext(dispatcher) {
            localDataSource.getContentById(contentId) != null
        }
    }

    override suspend fun updateContent(contentId: String, content: Content) {
        withContext(dispatcher) {
            val newContent = localDataSource.getContentById(contentId)?.copy(
                title = content.title,
                type = content.type,
                synopsis = content.synopsis,
                yearRelease = content.yearRelease,
                genre = content.genre,
                isWatched = content.isWatched,
                personalRating = content.personalRating,
                comment =  content.comment,
                reviewedBy = content.reviewedBy,
                isFavorite = content.isFavorite,
            ) ?: throw Exception("Movie not found")

            newContent.let {
                localDataSource.upsertContent(it)
            }
        }
    }

    override suspend fun updateFavoriteContent(contentId: String, isFavorite: Boolean) {
        withContext(dispatcher) {
            val content = localDataSource.getContentById(contentId) ?: throw Exception("Movie not found")
            val newContent = content.copy(isFavorite = isFavorite)
            localDataSource.upsertContent(newContent)
        }
    }

    override suspend fun deleteContent(contentId: String) {
        withContext(dispatcher) {
            localDataSource.deleteContentById(contentId)
        }
    }
}