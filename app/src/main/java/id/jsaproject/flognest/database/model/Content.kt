package id.jsaproject.flognest.database.model

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "content",
    indices = [
        Index("title", unique = true)
    ]
)
@Immutable
data class Content(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "posterUrl") val posterUrl: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "synopsis") val synopsis: String,
    @ColumnInfo(name = "yearRelease") val yearRelease: String,
    @ColumnInfo(name = "genre") val genre: String,
    @ColumnInfo(name = "isWatched") val isWatched: Boolean,
    @ColumnInfo(name = "personalRating") val personalRating: Double,
    @ColumnInfo(name = "comment") val comment: String,
    @ColumnInfo(name = "reviewedBy") val reviewedBy: String,
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean,
)