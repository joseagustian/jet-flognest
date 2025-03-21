package id.jsaproject.flognest.ui.screen.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.jsaproject.flognest.ui.components.DetailContentCard
import id.jsaproject.flognest.ui.components.DetailContentCardTitle
import id.jsaproject.flognest.ui.components.DetailRatingContentCard
import id.jsaproject.flognest.ui.components.DetailReviewContentCard
import id.jsaproject.flognest.ui.viewmodel.ContentViewModel
import id.jsaproject.flognest.ui.viewmodel.DetailUiState

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    contentId: String,
    shouldRefreshDetail: Boolean,
    contentViewModel: ContentViewModel = hiltViewModel(),
) {
    val uiState by contentViewModel.detailUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        contentViewModel.getDetailContent(contentId)
    }

    LaunchedEffect(shouldRefreshDetail) {
        if (shouldRefreshDetail) {
            contentViewModel.getDetailContent(contentId)
        }
    }

    DetailContent(
        modifier = modifier,
        uiState = uiState
    )
}

@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    uiState: DetailUiState
) {
    Column(
        modifier = modifier
            .padding(
                bottom = 75.dp,
            )
            .fillMaxSize()
    ) {
        DetailContentCard(
            modifier = modifier,
            posterUrl = uiState.content?.posterUrl ?: "",
            title = uiState.content?.title ?: "",
            year = uiState.content?.yearRelease ?: "",
            genre = uiState.content?.genre ?: "",
            type = uiState.content?.type ?: "",
            synopsis = uiState.content?.synopsis ?: ""
        )
        DetailContentCardTitle(
            modifier = modifier,
            title = "Rating From Me"
        )
        DetailRatingContentCard(
            modifier = modifier,
            personalRating = uiState.content?.personalRating ?: 0.0,
            type = uiState.content?.type ?: ""
        )
        DetailContentCardTitle(
            modifier = modifier,
            title = "My Thoughts On This"
        )
        DetailReviewContentCard(
            modifier = modifier,
            review = uiState.content?.comment ?: "",
            reviewer = uiState.content?.reviewedBy ?: ""
        )
    }
}