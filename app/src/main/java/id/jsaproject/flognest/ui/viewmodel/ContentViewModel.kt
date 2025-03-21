package id.jsaproject.flognest.ui.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.jsaproject.flognest.database.dummyContentList
import id.jsaproject.flognest.database.model.Content
import id.jsaproject.flognest.database.repository.ContentRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AppUiState(
    val contentId: String = "",
    val contentTitle: String = "",
)

data class HomeUiState(
    val contentList: List<Content> = emptyList(),
    val isInsertDataLoading: Boolean = false,
    val isFetchDataLoading: Boolean = true,
    val error: String = "",
    val isListContentInserted: Boolean = false,
    val isFavoriteContent: Boolean = false,
    val isContentUpdated: Boolean = false,
)

data class AddContentUiState(
    val content: Content? = null,
    val isAddDataLoading: Boolean = false,
    val error: String = "",
    val isContentAdded: Boolean = false,
    val isContentExist: Boolean = false
)

data class DetailUiState(
    val content: Content? = null,
    val isFetchDataLoading: Boolean = false,
    val error: String = "",
    val isFavoriteContent: Boolean = false,
    val isContentUpdated: Boolean = false
)

enum class ChipIsWatchedState {
    INACTIVE,
    FALSE,
    TRUE
}

enum class ChipIsFavoriteState {
    INACTIVE,
    FALSE,
    TRUE
}

data class FilterState(
    val searchQuery: String = "",
    val selectedTypes: List<String> = emptyList(),
    val selectedGenres: List<String> = emptyList(),
    val selectedFavorite: Boolean = false,
    val isFilterActive: Boolean = false,
    val isFilterByQueryActive: Boolean = false,
    val isFilterTypeActive: Boolean = false,
    val isFilterGenreActive: Boolean = false,
    val chipIsWatchedState: ChipIsWatchedState = ChipIsWatchedState.INACTIVE,
    val chipIsFavoriteState: ChipIsFavoriteState = ChipIsFavoriteState.INACTIVE
) {
    fun isEmpty(): Boolean {
        return searchQuery.isEmpty() &&
                selectedTypes.isEmpty() &&
                selectedGenres.isEmpty() &&
                chipIsWatchedState == ChipIsWatchedState.INACTIVE &&
                chipIsFavoriteState == ChipIsFavoriteState.INACTIVE
    }
    fun fncIsFilterByQueryActive(): Boolean {
        return searchQuery.isNotEmpty()
    }
    fun fncIsFilterTypeActive(): Boolean {
        return selectedTypes.isNotEmpty()
    }
    fun fncIsFilterGenreActive(): Boolean {
        return selectedGenres.isNotEmpty()
    }
}

data class UpdateContentUiState(
    val content: Content? = null,
    val isUpdateDataLoading: Boolean = false,
    val error: String = "",
    val isContentUpdated: Boolean = false,
    val existingRating: Double = 0.0,
    val existingReview: String = "",
)

data class DeleteContentUiState(
    val content: Content? = null,
    val isDeleteDataLoading: Boolean = false,
    val error: String = "",
    val isContentDeleted: Boolean = false
)

@HiltViewModel
class ContentViewModel @Inject constructor(
    private val repository: ContentRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _appUiState = MutableStateFlow(AppUiState())
    val appUiState: StateFlow<AppUiState> = _appUiState.asStateFlow()

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    private val _addContentUiState = MutableStateFlow(AddContentUiState())
    val addContentUiState: StateFlow<AddContentUiState> = _addContentUiState.asStateFlow()

    private val _detailUiState = MutableStateFlow(DetailUiState())
    val detailUiState: StateFlow<DetailUiState> = _detailUiState.asStateFlow()

    private val _deleteContentUiState = MutableStateFlow(DeleteContentUiState())
    val deleteContentUiState: StateFlow<DeleteContentUiState> = _deleteContentUiState.asStateFlow()

    private val _filterState = MutableStateFlow(FilterState())
    val filterState: StateFlow<FilterState> = _filterState.asStateFlow()

    private val _updateContentUiState = MutableStateFlow(UpdateContentUiState())
    val updateContentUiState: StateFlow<UpdateContentUiState> = _updateContentUiState.asStateFlow()

    fun insertContent(content: Content) {
        _addContentUiState.update {
            it.copy(isAddDataLoading = true)
        }
        viewModelScope.launch {
            val isContentExist = repository.checkIfContentExist(contentId = content.id)
            if (!isContentExist) {
                repository.createContent(content)
                _addContentUiState.update {
                    it.copy(isContentAdded = true)
                }
            } else {
                _addContentUiState.update {
                    it.copy(isContentExist = true)
                }
            }
            _addContentUiState.update {
                it.copy(isContentExist = isContentExist)
            }
            _addContentUiState.update {
                it.copy(isAddDataLoading = false)
            }
        }
    }

    fun resetAddContentUiState() {
        _addContentUiState.update {
            it.copy(isContentAdded = false)
        }
        _addContentUiState.update {
            it.copy(isContentExist = false)
        }
    }

    private var currentGetListJob: Job? = null
    fun getContentList() {
        currentGetListJob?.cancel()
        currentGetListJob = viewModelScope.launch {
            _homeUiState.update {
                it.copy(isFetchDataLoading = true)
            }
            viewModelScope.launch {
                val contentList = repository.getContents()
                _homeUiState.update {
                    it.copy(contentList = contentList)
                }
                delay(2000)
                _homeUiState.update {
                    it.copy(isFetchDataLoading = false)
                }
            }
        }
    }

    fun updateFilters(update: FilterState.() -> FilterState) {
        _filterState.value = _filterState.value.update()

        currentFilterJob?.cancel()
        viewModelScope.launch {
            delay(300)
            filterContentList(_filterState.value)
        }
    }

    fun clearFilterState(update: FilterState.() -> FilterState) {
        _filterState.value = _filterState.value.update()
        _filterState.update {
            it.copy(
                isFilterActive = false
            )
        }
    }

    private var currentFilterJob: Job? = null
    fun filterContentList(filterState: FilterState) {
        currentFilterJob?.cancel()
        _homeUiState.update { it.copy(isFetchDataLoading = true) }

        currentFilterJob = viewModelScope.launch {
            _filterState.update {
                it.copy(
                    isFilterActive = !filterState.isEmpty(),
                    isFilterByQueryActive = filterState.fncIsFilterByQueryActive(),
                    isFilterTypeActive = filterState.fncIsFilterTypeActive(),
                    isFilterGenreActive = filterState.fncIsFilterGenreActive(),
                )
            }
            val contentList = if (filterState.isEmpty()) {
                repository.getContents()
            } else {
                repository.searchContent(
                    searchQuery = if (filterState.isFilterByQueryActive) filterState.searchQuery else null,
                    selectedWatched = if (filterState.chipIsWatchedState == ChipIsWatchedState.TRUE) true else if (filterState.chipIsWatchedState == ChipIsWatchedState.FALSE) false else null,
                    selectedFavorite = if (filterState.chipIsFavoriteState == ChipIsFavoriteState.TRUE) true else if (filterState.chipIsFavoriteState == ChipIsFavoriteState.FALSE) false else null,
                    selectedTypes = if (filterState.isFilterTypeActive) filterState.selectedTypes.joinToString(",") else null,
                    selectedGenres = if (filterState.isFilterGenreActive) filterState.selectedGenres.joinToString(",") else null
                )
            }

            delay(1000)
            _homeUiState.update {
                it.copy(contentList = contentList, isFetchDataLoading = false)
            }
        }
    }

    fun refreshContentList() {
        _homeUiState.update {
            it.copy(isFetchDataLoading = true)
        }
        viewModelScope.launch {
            val contentList = repository.getContents()
            _homeUiState.update {
                it.copy(contentList = contentList)
            }
            delay(2000)
            _homeUiState.update {
                it.copy(isFetchDataLoading = false)
            }
        }
    }

    fun getDetailContent(contentId: String) {
        viewModelScope.launch {
            val content = repository.getContentById(contentId)
            _detailUiState.update {
                it.copy(content = content)
            }
            _updateContentUiState.update {
                it.copy(existingRating = content.personalRating)
            }
            _updateContentUiState.update {
                it.copy(existingReview = content.comment)
            }
            delay(500)
            _detailUiState.update {
                it.copy(isFetchDataLoading = false)
            }
            _appUiState.update {
                it.copy(contentId = content.id)
            }
            _appUiState.update {
                it.copy(contentTitle = content.title)
            }
        }
    }

    fun updateFavoriteContent(
        contentId: String,
        isFavorite: Boolean
    ) {
        viewModelScope.launch {
            repository.updateFavoriteContent(contentId, isFavorite)
            if (filterState.value.isFilterActive) {
                filterContentList(filterState.value)
            } else {
                refreshContentList()
            }
        }

        _homeUiState.update {
            it.copy(isContentUpdated = true)
        }
    }

    fun updateContent(
        contentId: String,
        newRating: Double,
        newReview: String,
        newReviewer: String
    ) {
        _updateContentUiState.update {
            it.copy(isUpdateDataLoading = true)
        }
        viewModelScope.launch {
            val content = repository.getContentById(contentId)
            val newContentData = content.copy(
                personalRating = newRating,
                comment = newReview,
                reviewedBy = if (newReviewer.isNotEmpty()) newReviewer else content.reviewedBy,
                isWatched = true
            )
            repository.updateContent(contentId, newContentData)
            _updateContentUiState.update {
                it.copy(isUpdateDataLoading = false)
            }
            _updateContentUiState.update {
                it.copy(isContentUpdated = true)
            }
        }
    }

    fun deleteContent(contentId: String?) {
        if (contentId.isNullOrBlank()) {
            return
        }

        _deleteContentUiState.update {
            it.copy(isDeleteDataLoading = true)
        }

        viewModelScope.launch {
            try {
                repository.deleteContent(contentId)

                if (filterState.value.isFilterActive) {
                    filterContentList(filterState.value)
                } else {
                    refreshContentList()
                }
            } catch (e: Exception) {

            } finally {
                _deleteContentUiState.update {
                    it.copy(
                        isDeleteDataLoading = false,
                    )
                }
                _deleteContentUiState.update {
                    it.copy(isContentDeleted = true)
                }
                delay(500)
                _deleteContentUiState.update {
                    it.copy(isContentDeleted = false)
                }
            }
        }
    }
}