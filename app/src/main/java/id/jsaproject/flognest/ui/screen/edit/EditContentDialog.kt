package id.jsaproject.flognest.ui.screen.edit

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.jsaproject.flognest.ContentState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.jsaproject.flognest.ui.modifiers.verticalColumnScrollbar
import id.jsaproject.flognest.ui.viewmodel.ContentViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditContentDialog(
    onDismissRequest: () -> Unit,
    onContentUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    contentState: ContentState,
    contentViewModel: ContentViewModel = hiltViewModel()
) {
    val uiState by contentViewModel.updateContentUiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val isScrollable = scrollState.maxValue > 0

    var ratingText by remember { mutableStateOf("") }
    var commentText by remember { mutableStateOf("") }
    var reviewerText by remember { mutableStateOf("") }
    var isTryUpdateContent by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        contentViewModel.getDetailContent(contentState.id)
        delay(200)
        if (!contentState.isWatchlist) {
            ratingText = uiState.existingRating.toString()
            commentText = uiState.existingReview
        }
    }

    if (isTryUpdateContent) {
        LaunchedEffect(Unit) {
            if (uiState.isUpdateDataLoading) {
                Toast.makeText(context, "Updating...", Toast.LENGTH_SHORT).show()
            }
            delay(500)
            if (uiState.isContentUpdated) {
                onContentUpdate()
                onDismissRequest()
                Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show()
                contentViewModel.resetAddContentUiState()
            }
            isTryUpdateContent = false
        }
    }

    BasicAlertDialog(
        onDismissRequest = { onDismissRequest() },
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFFFFFFF)),
        ) {
            Column(
                modifier = modifier.padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 8.dp,
                    bottom = 12.dp
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = if (contentState.isWatchlist) "Already Watched?" else "What's New?",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        ),
                    )
                    Spacer(modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        modifier = modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 8.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onDismissRequest()
                            }
                    )
                }
                Column(
                    modifier
                        .padding(
                            top = 18.dp,
                            bottom = 18.dp
                        )
                        .then(
                            if (isScrollable) modifier.verticalColumnScrollbar(scrollState)
                            else modifier
                        )
                        .verticalScroll(scrollState)
                ) {
                    Text(
                        text = if (contentState.isWatchlist) "Rating" else "New Rating",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        ),
                    )
                    OutlinedTextField(
                        value = ratingText,
                        onValueChange = { newValue ->
                            ratingText = changeRatingValue(newValue)
                        },
                        label = {
                            Text(
                                text = "Scale From 1.0 - to 10.0",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            )
                        },
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = modifier.height(8.dp))
                    Text(
                        text = if (contentState.isWatchlist) "Review" else "New Thoughts",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        ),
                    )
                    OutlinedTextField(
                        value = commentText,
                        onValueChange = {
                            commentText = it
                        },
                        label = {
                            Text(
                                text = "Here's what I think...",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            )
                        },
                        modifier = modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .padding(end = 8.dp)
                    )
                    Spacer(modifier = modifier.height(8.dp))
                    AnimatedVisibility(visible = contentState.isWatchlist) {
                        Column {
                            Text(
                                text = "Reviewer",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                ),
                            )
                            OutlinedTextField(
                                value = reviewerText,
                                onValueChange = {
                                    reviewerText = it
                                },
                                label = {
                                    Text(
                                        text = "What's your name?",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        )
                                    )
                                },
                                modifier = modifier
                                    .fillMaxWidth()
                                    .padding(end = 8.dp)
                            )
                        }
                    }
                }
                Button(
                    onClick = {
                        inputValidation(
                            title = "",
                            isWatchlist = contentState.isWatchlist,
                            rating = ratingText,
                            comment = commentText,
                            reviewer = reviewerText,
                            onValidationSuccess = {
                                contentViewModel.updateContent(
                                    contentId = contentState.id,
                                    newRating = ratingText.toDouble(),
                                    newReview = commentText,
                                    newReviewer = reviewerText
                                )
                            },
                            context = context
                        )
                        contentViewModel.updateContent(
                            contentId = contentState.id,
                            newRating = ratingText.toDouble(),
                            newReview = commentText,
                            newReviewer = reviewerText
                        )
                        isTryUpdateContent = true
                    },
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF000000)
                    ),
                    modifier = modifier
                        .fillMaxWidth()
                        .height(56.dp),
                ) {
                    Text(
                        text = if (contentState.isWatchlist) "Already Watched" else "Update",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        ),
                        modifier = modifier.padding(end = 8.dp)
                    )
                }
            }
        }
    }
}

fun inputValidation(
    title: String,
    isWatchlist: Boolean,
    rating: String,
    comment: String,
    reviewer: String,
    onValidationSuccess: () -> Unit,
    context: Context
) {
    when {
        isWatchlist -> {
            if (rating.isEmpty()) {
                Toast.makeText(context, "Please Give $title A Rating.", Toast.LENGTH_SHORT).show()
                return
            }
            if (comment.isEmpty()) {
                Toast.makeText(context, "Please Share An Opinion About $title.", Toast.LENGTH_SHORT).show()
                return
            }
            if (reviewer.isEmpty()) {
                Toast.makeText(context, "Please Enter The Reviewer's Name.", Toast.LENGTH_SHORT).show()
                return
            }
            onValidationSuccess()
        }
        else -> {
            if (rating.isEmpty()) {
                Toast.makeText(context, "Please Give $title A Rating.", Toast.LENGTH_SHORT).show()
                return
            }
            if (comment.isEmpty()) {
                Toast.makeText(context, "Please Share An Opinion About $title.", Toast.LENGTH_SHORT).show()
                return
            }
            onValidationSuccess()
        }
    }
}



fun changeRatingValue(value: String): String {
    var updatedValue = value.replace(",", ".")
    updatedValue = updatedValue.filter { it.isDigit() || it == '.' }
    if (updatedValue == "0") {
        updatedValue = ""
    }
    if (updatedValue == ".") {
        updatedValue = "1"
    }
    val parts = updatedValue.split(".")
    updatedValue = if (parts.size > 2) {
        parts[0] + "." + parts[1].takeWhile { it.isDigit() }
    } else {
        updatedValue
    }
    if (updatedValue.startsWith("0") && updatedValue.length > 1 && updatedValue[1] != '.') {
        updatedValue = updatedValue.substring(1)
    }
    if (updatedValue.length >= 3) {
        val numValue = updatedValue.toDoubleOrNull()

        if (numValue != null) {
            if (numValue >= 100) {
                updatedValue = (numValue / 100).toString()
            }

            if (updatedValue.toDoubleOrNull() != null && updatedValue.toDouble() > 10.0) {
                updatedValue = "10.0"
            }

            updatedValue = updatedValue.take(4)
        }
    }
    if (updatedValue.contains(".")) {
        val parts = updatedValue.split(".")
        if (parts.size > 1) {
            updatedValue = parts[0] + "." + parts[1].take(1)
        }
    }
    return updatedValue
}