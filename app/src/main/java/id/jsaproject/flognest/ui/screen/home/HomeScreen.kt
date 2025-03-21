package id.jsaproject.flognest.ui.screen.home

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableChipElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.jsaproject.flognest.database.model.Content
import id.jsaproject.flognest.genreChipItems
import id.jsaproject.flognest.typeChipItems
import id.jsaproject.flognest.ui.components.ContentListCard
import id.jsaproject.flognest.ui.viewmodel.ChipIsFavoriteState
import id.jsaproject.flognest.ui.viewmodel.ChipIsWatchedState
import id.jsaproject.flognest.ui.viewmodel.HomeUiState
import id.jsaproject.flognest.ui.viewmodel.ContentViewModel
import id.jsaproject.flognest.ui.viewmodel.FilterState
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (String, String) -> Unit,
    openEditDialog: (String, String, Boolean) -> Unit,
    shouldRefreshData: Boolean,
    contentViewModel: ContentViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val uiState by contentViewModel.homeUiState.collectAsStateWithLifecycle()
    val filterState by contentViewModel.filterState.collectAsStateWithLifecycle()
    val deleteState by contentViewModel.deleteContentUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        contentViewModel.getContentList()
    }

    LaunchedEffect(filterState) {
        contentViewModel.filterContentList(filterState)
    }

    LaunchedEffect(deleteState) {
        if (deleteState.isDeleteDataLoading) {
            Toast.makeText(
                context,
                "Deleting Data...",
                Toast.LENGTH_SHORT
            ).show()
        }
        delay(500)
        if (deleteState.isContentDeleted) {
            Toast.makeText(
                context,
                "Data Successfully Deleted!",
                Toast.LENGTH_SHORT
            ).show()
            contentViewModel.getContentList()
        }
    }

    HomeContent(
        uiState = uiState,
        modifier = modifier,
        navigateToDetail = navigateToDetail,
        openEditDialog = openEditDialog,
        onContentFavorite = { id, isFavorite ->
            contentViewModel.updateFavoriteContent(id, isFavorite)
        },
        onContentDelete = { id ->
            contentViewModel.deleteContent(id)
        },
        onSearch = { query -> contentViewModel.updateFilters { copy(searchQuery = query) } },
        onTypeSelected = { type, selected ->
            contentViewModel.updateFilters {
                copy(selectedTypes = if (selected) selectedTypes + type else selectedTypes - type)
            }
        },
        onGenreSelected = { genre, selected ->
            contentViewModel.updateFilters {
                copy(selectedGenres = if (selected) selectedGenres + genre else selectedGenres - genre)
            }
        },
        onFavoriteFilter = { chipsIsFavoriteType ->
            contentViewModel.updateFilters { copy(chipIsFavoriteState = chipsIsFavoriteType) }
        },
        onIsWatchedSelected = { chipIsWatchedType ->
            contentViewModel.updateFilters { copy(chipIsWatchedState = chipIsWatchedType) }
        },
        onClearFilter = {
            contentViewModel.clearFilterState { FilterState() }
            contentViewModel.getContentList()
        },
        filterState = filterState
    )

    if (shouldRefreshData) {
        LaunchedEffect(Unit) {
            contentViewModel.refreshContentList()
        }
    }
}

@Composable
fun HomeContent(
    uiState: HomeUiState,
    filterState: FilterState,
    modifier: Modifier = Modifier,
    navigateToDetail: (String, String) -> Unit,
    openEditDialog: (String, String, Boolean) -> Unit,
    onContentFavorite: (String, Boolean) -> Unit,
    onContentDelete: (String) -> Unit,
    onSearch: (String) -> Unit,
    onTypeSelected: (String, Boolean) -> Unit,
    onGenreSelected: (String, Boolean) -> Unit,
    onFavoriteFilter: (ChipIsFavoriteState) -> Unit,
    onIsWatchedSelected: (ChipIsWatchedState) -> Unit,
    onClearFilter: () -> Unit
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchFilterField(
                modifier = modifier.weight(0.8f),
                onSearch = onSearch,
                searchQuery = filterState.searchQuery
            )
            Button (
                onClick = {
                    onClearFilter()
                    onSearch("")
                    onTypeSelected
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE5E4E4),
                ),
                shape = RoundedCornerShape(
                    8.dp
                ),
                modifier = modifier
                    .weight(0.2f)
                    .height(53.dp)
                    .padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    tint = Color(0xFF000000),
                    contentDescription = "Clear All Filter",
                )
            }
        }
        FilterChips(
            modifier = modifier,
            onTypeSelected = { type, selected ->
                onTypeSelected(type, selected)
            },
            onGenreSelected = { genre, selected ->
                onGenreSelected(genre, selected)
            },
            onIsWatchedSelected = { chipIsWatchedType ->
                onIsWatchedSelected(chipIsWatchedType)
            },
            onIsFavoriteSelected = { chipIsFavorite ->
                onFavoriteFilter(chipIsFavorite)
            },
            filterState = filterState
        )
        when {
            uiState.isFetchDataLoading -> LoadingIndicator()
            uiState.contentList.isEmpty() && filterState.isFilterActive -> EmptyState("I've Been Looking Everywhere, But I Can't Find Anything.")
            uiState.contentList.isEmpty() -> EmptyState("Is Empty Here, Maybe You Should Add Something?")
            else -> ContentList(
                contentList = uiState.contentList,
                modifier = modifier,
                navigateToDetail = navigateToDetail,
                openEditDialog = openEditDialog,
                onDeleteClick = onContentDelete,
                onFavoriteClick = onContentFavorite,
            )
        }
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun EmptyState(
    message: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = message,
            modifier = Modifier
                .align(Alignment.Center)
                .width(250.dp)
        )
    }
}

@Composable
fun ContentList(
    contentList: List<Content>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String, String) -> Unit,
    openEditDialog: (String, String, Boolean) -> Unit,
    onDeleteClick: (String) -> Unit,
    onFavoriteClick: (String, Boolean) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        items(
            items = contentList,
            key = { content -> content.id }
        ) { content ->
            ContentListCard(
                posterUrl = content.posterUrl,
                title = content.title,
                genre = content.genre,
                type = content.type,
                synopsis = content.synopsis,
                isWatched = content.isWatched,
                isFavorite = content.isFavorite,
                personalRating = content.personalRating,
                onFavoriteClick = {
                    if (content.isWatched == true) {
                        onFavoriteClick(content.id, !content.isFavorite)
                    }
                },
                onCardClick = {
                    if (content.isWatched == true) {
                        navigateToDetail(content.id, content.title)
                    } else {
                        openEditDialog(
                            content.id,
                            content.title,
                            true
                        )
                    }
                },
                onDeleteClick = {
                    onDeleteClick(content.id)
                }
            )
        }
    }
}

@Composable
fun SearchFilterField(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearch: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = searchQuery,
        onValueChange = {
            onSearch(it)
        },
        placeholder = {
            Text(
                text = "Search By Title",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            )
        },
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            fontSize = 12.sp
        ),
        modifier = modifier
            .height(68.dp)
            .padding(
                8.dp
            ),
        shape = RoundedCornerShape(
            8.dp
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(searchQuery)
                focusManager.clearFocus()
            }
        ),
    )
}

@Composable
fun FilterChips(
    modifier: Modifier = Modifier,
    filterState: FilterState,
    onTypeSelected: (String, Boolean) -> Unit,
    onGenreSelected: (String, Boolean) -> Unit,
    onIsWatchedSelected: (ChipIsWatchedState) -> Unit,
    onIsFavoriteSelected: (ChipIsFavoriteState) -> Unit,
) {
    var chipIsWatchedState = filterState.chipIsWatchedState
    var chipIsFavoriteState = filterState.chipIsFavoriteState
    Row(
        modifier = modifier
            .padding(
                start = 8.dp,
                end = 8.dp,
            )
            .horizontalScroll(rememberScrollState())
    ) {
        FilterChip(
            onClick = {
                val newChipIsWatchedStates = when (chipIsWatchedState) {
                    ChipIsWatchedState.INACTIVE -> ChipIsWatchedState.FALSE
                    ChipIsWatchedState.FALSE -> ChipIsWatchedState.TRUE
                    ChipIsWatchedState.TRUE -> ChipIsWatchedState.INACTIVE
                }
                chipIsWatchedState = newChipIsWatchedStates
                onIsWatchedSelected(chipIsWatchedState)
            },
            label = {
                when (chipIsWatchedState) {
                    ChipIsWatchedState.INACTIVE -> Text("Have Watchlist?")
                    ChipIsWatchedState.FALSE -> Text("Watchlist")
                    ChipIsWatchedState.TRUE -> Text("Watched")
                }
            },
            selected = chipIsWatchedState == ChipIsWatchedState.TRUE,
            leadingIcon = if (chipIsWatchedState == ChipIsWatchedState.TRUE) {
                {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "Done Icon",
                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                    )
                }
            } else {
                null
            },
            colors = FilterChipDefaults.filterChipColors(
                containerColor = if (chipIsWatchedState == ChipIsWatchedState.INACTIVE) {
                    Color(0xFF0096FF)
                } else  {
                    Color(0xFFEFAD00)
                },
                labelColor = Color(0xFFFFFFFF),
                selectedContainerColor = Color(0xFF4CAF50),
                selectedLabelColor = Color(0xFFFFFFFF),
                selectedLeadingIconColor = Color(0xFFFFFFFF)
            ),
            border = BorderStroke(width = 0.dp, color = Color.Transparent),
            elevation = SelectableChipElevation(
                elevation = 0.dp,
                pressedElevation = 0.dp,
                focusedElevation = 0.dp,
                hoveredElevation = 0.dp,
                draggedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
        )
        Spacer(modifier.width(5.dp))
        typeChipItems.forEach { item ->
            val isSelected = filterState.selectedTypes.contains(item.type)
            FilterChip(
                onClick = {
                    onTypeSelected(item.type, !isSelected)
                },
                label = {
                    Text(item.type)
                },
                selected = isSelected,
                leadingIcon = if (isSelected) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    null
                },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = Color(0xFF000000),
                    labelColor = Color(0xFFFFFFFF),
                    selectedContainerColor = Color(0xFF00866D),
                    selectedLabelColor = Color(0xFFFFFFFF),
                    selectedLeadingIconColor = Color(0xFFFFFFFF)
                ),
                border = BorderStroke(width = 0.dp, color = Color.Transparent),
                elevation = SelectableChipElevation(
                    elevation = 0.dp,
                    pressedElevation = 0.dp,
                    focusedElevation = 0.dp,
                    hoveredElevation = 0.dp,
                    draggedElevation = 0.dp,
                    disabledElevation = 0.dp
                ),
                modifier = modifier.padding(
                    end = 5.dp
                )
            )
        }
    }
    Row(
        modifier = modifier
            .padding(
                start = 8.dp,
                end = 8.dp,
            )
            .horizontalScroll(rememberScrollState())
    ) {
        FilterChip(
            onClick = {
                val newChipIsFavoriteState = when (chipIsFavoriteState) {
                    ChipIsFavoriteState.INACTIVE -> ChipIsFavoriteState.TRUE
                    ChipIsFavoriteState.TRUE -> ChipIsFavoriteState.FALSE
                    ChipIsFavoriteState.FALSE -> ChipIsFavoriteState.INACTIVE
                }
                chipIsFavoriteState = newChipIsFavoriteState
                onIsFavoriteSelected(chipIsFavoriteState)
            },
            label = {
                when (chipIsFavoriteState) {
                    ChipIsFavoriteState.INACTIVE -> Text("Have Favorite?")
                    ChipIsFavoriteState.FALSE -> Text("Not Favorite")
                    ChipIsFavoriteState.TRUE -> Text("Favorite")
                }
            },
            selected = chipIsFavoriteState == ChipIsFavoriteState.TRUE,
            leadingIcon = if (chipIsFavoriteState == ChipIsFavoriteState.TRUE) {
                {
                    Icon(
                        imageVector = Icons.Rounded.Favorite,
                        contentDescription = "Favorite Icon",
                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                    )
                }
            } else {
                null
            },
            colors = FilterChipDefaults.filterChipColors(
                containerColor = if (chipIsFavoriteState == ChipIsFavoriteState.INACTIVE) {
                    Color(0xFFFF0084)
                } else  {
                    Color(0xFF00295A)
                },
                labelColor = Color(0xFFFFFFFF),
                selectedContainerColor = Color(0xFFFF4242),
                selectedLabelColor = Color(0xFFFFFFFF),
                selectedLeadingIconColor = Color(0xFFFFFFFF)
            ),
            border = BorderStroke(width = 0.dp, color = Color.Transparent),
            elevation = SelectableChipElevation(
                elevation = 0.dp,
                pressedElevation = 0.dp,
                focusedElevation = 0.dp,
                hoveredElevation = 0.dp,
                draggedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            modifier = modifier.padding(
                end = 5.dp
            )
        )
        genreChipItems.forEach { item ->
            val isSelected = filterState.selectedGenres.contains(item.genre)
            FilterChip(
                onClick = {
                    onGenreSelected(item.genre, !isSelected)
                },
                label = {
                    Text(item.genre)
                },
                selected = isSelected,
                leadingIcon = if (isSelected) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    null
                },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = Color(0xFFE5E4E4),
                    labelColor = Color(0xFF030303),
                    selectedContainerColor = Color(0xFF5533A1),
                    selectedLabelColor = Color(0xFFFFFFFF),
                    selectedLeadingIconColor = Color(0xFFFFFFFF)
                ),
                border = BorderStroke(width = 0.dp, color = Color.Transparent),
                elevation = SelectableChipElevation(
                    elevation = 0.dp,
                    pressedElevation = 0.dp,
                    focusedElevation = 0.dp,
                    hoveredElevation = 0.dp,
                    draggedElevation = 0.dp,
                    disabledElevation = 0.dp
                )
            )
            Spacer(modifier.width(5.dp))
        }
    }
}