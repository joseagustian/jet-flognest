package id.jsaproject.flognest.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import id.jsaproject.flognest.database.model.Content
import kotlinx.coroutines.flow.Flow

@Dao
interface ContentDao {
    @Upsert()
    suspend fun upsertContent(content: Content)

    @Upsert
    suspend fun upsertListContent(contents: List<Content>)

    @Query("SELECT * FROM content WHERE id = :id")
    suspend fun getContentById(id: String): Content?

    @Query("SELECT * FROM content WHERE id = :id")
    fun observeContentById(id: String): Flow<Content>

    @Query("SELECT * FROM content")
    suspend fun getAllContents(): List<Content>

    @Query("SELECT * FROM content")
    fun observeAllContents(): Flow<List<Content>>

    @Query("SELECT * FROM content WHERE genre = :genre")
    suspend fun getContentsWithGenre(genre: String): List<Content>

    @Query("SELECT * FROM content WHERE genre = :genre")
    fun observeContentsWithGenre(genre: String): Flow<List<Content>>

    @Query("SELECT * FROM content WHERE isFavorite = 1")
    suspend fun getFavoriteContents(): List<Content>

    @Query("SELECT * FROM content WHERE isFavorite = 1")
    fun observeFavoriteContents(): Flow<List<Content>>

    @Query("SELECT * FROM content WHERE isWatched = 1")
    suspend fun getWatchedContents(): List<Content>

    @Query("SELECT * FROM content WHERE isWatched = 1")
    fun observeWatchedContents(): Flow<List<Content>>

    @Query("SELECT * FROM content WHERE type = :type")
    suspend fun getContentsWithType(type: String): List<Content>

    @Query("SELECT * FROM content WHERE type = :type")
    fun observeContentsWithType(type: String): Flow<List<Content>>

    @Query("""
        SELECT * FROM content 
        WHERE (:searchQuery IS NULL OR title LIKE '%' || :searchQuery || '%')
        AND (:selectedWatched IS NULL OR isWatched = :selectedWatched)
        AND (:selectedFavorite IS NULL OR isFavorite = :selectedFavorite)
        AND (
            :selectedTypes IS NULL 
            OR EXISTS (
                SELECT 1 FROM content AS c WHERE c.id = content.id 
                AND (',' || :selectedTypes || ',' LIKE '%,' || c.type || ',%')
            )
        )
        AND (
            :selectedGenres IS NULL 
            OR EXISTS (
                SELECT 1 FROM content AS c WHERE c.id = content.id 
                AND (',' || :selectedGenres || ',' LIKE '%,' || c.genre || ',%')
            )
        )
    """)
    suspend fun getFilteredContents(
        searchQuery: String?,
        selectedWatched: Boolean?,
        selectedFavorite: Boolean?,
        selectedTypes: String?,
        selectedGenres: String?,
    ): List<Content>


    @Query("""
        SELECT * FROM content 
        WHERE (:searchQuery IS NULL OR title LIKE '%' || :searchQuery || '%')
        AND (:selectedWatched IS NULL OR isWatched = :selectedWatched)
        AND (:selectedFavorite IS NULL OR isFavorite = :selectedFavorite)
        AND (
            :selectedTypes IS NULL 
            OR EXISTS (
                SELECT 1 FROM content AS c WHERE c.id = content.id 
                AND (',' || :selectedTypes || ',' LIKE '%,' || c.type || ',%')
            )
        )
        AND (
            :selectedGenres IS NULL 
            OR EXISTS (
                SELECT 1 FROM content AS c WHERE c.id = content.id 
                AND (',' || :selectedGenres || ',' LIKE '%,' || c.genre || ',%')
            )
        )
    """)
    fun observeFilteredContents(
        searchQuery: String?,
        selectedWatched: Boolean?,
        selectedFavorite: Boolean?,
        selectedTypes: String?,
        selectedGenres: String?
    ): Flow<List<Content>>

    @Query("DELETE FROM content WHERE id = :id")
    suspend fun deleteContentById(id: String): Int
}