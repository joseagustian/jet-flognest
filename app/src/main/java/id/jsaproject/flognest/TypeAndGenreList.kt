package id.jsaproject.flognest

val genres = listOf(
    "Action",
    "Adventure",
    "Animation",
    "Comedy",
    "Crime",
    "Documentary",
    "Drama",
    "Family",
    "Fantasy",
    "History",
    "Horror",
    "Music",
    "Mystery",
    "Romance",
    "Science Fiction",
    "Thriller",
    "War",
    "Western",
    "Others"
)

val contentTypes = listOf(
    "Movie",
    "Series",
    "TV Series",
    "TV Movie",
    "Short Film",
    "Web Series",
    "Others"
)

data class GenreChipItem(
    val genre: String,
    var isSelected: Boolean
)

val genreChipItems = genres.map {
    GenreChipItem(it, false)
}

data class TypeChipItem(
    val type: String,
    var isSelected: Boolean
)

val typeChipItems = contentTypes.map {
    TypeChipItem(it, false)
}