package id.jsaproject.flognest.database.repository

import id.jsaproject.flognest.database.model.Content
import kotlinx.coroutines.flow.Flow

interface ContentRepository {
    fun getContentsStream(): Flow<List<Content>>
    suspend fun getContents(): List<Content>

    fun filterContentByGenreStream(genre: String): Flow<List<Content>>
    suspend fun filterContentByGenre(genre: String): List<Content>

    fun getFavoriteContentsStream(isFavorite: Boolean): Flow<List<Content>>
    suspend fun getFavoriteContents(isFavorite: Boolean): List<Content>

    fun getWatchedContentsStream(isWatched: Boolean): Flow<List<Content>>
    suspend fun getWatchedContents(isWatched: Boolean): List<Content>

    fun filterContentByTypeStream(type: String): Flow<List<Content>>
    suspend fun filterContentByType(type: String): List<Content>

    fun getContentByIdStream(contentId: String): Flow<Content>
    suspend fun getContentById(contentId: String): Content

    fun searchContentStream(
        searchQuery: String?,
        selectedWatched: Boolean?,
        selectedFavorite: Boolean?,
        selectedTypes: String?,
        selectedGenres: String?,
    ): Flow<List<Content>>

    suspend fun searchContent(
        searchQuery: String?,
        selectedWatched: Boolean?,
        selectedFavorite: Boolean?,
        selectedTypes: String?,
        selectedGenres: String?,
    ): List<Content>

    suspend fun createContent(content: Content)

    suspend fun insertListContent(contents: List<Content>)

    suspend fun checkIfContentExist(contentId: String): Boolean

    suspend fun updateContent(contentId: String, content: Content)

    suspend fun updateFavoriteContent(contentId: String, isFavorite: Boolean)

    suspend fun deleteContent(contentId: String)
}