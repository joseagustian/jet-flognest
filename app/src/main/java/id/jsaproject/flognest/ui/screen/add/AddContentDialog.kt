package id.jsaproject.flognest.ui.screen.add

import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.jsaproject.flognest.contentTypes
import id.jsaproject.flognest.database.model.Content
import id.jsaproject.flognest.genres
import id.jsaproject.flognest.ui.modifiers.verticalColumnScrollbar
import id.jsaproject.flognest.ui.viewmodel.ContentViewModel
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContentDialog(
    onDismissRequest: () -> Unit,
    onContentAdded: () -> Unit,
    navigateToTMDBPage: () -> Unit,
    modifier: Modifier = Modifier,
    contentViewModel: ContentViewModel = hiltViewModel()
) {
    val uiState by contentViewModel.addContentUiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val scrollState = rememberScrollState()
    val isScrollable = scrollState.maxValue > 0

    val dropdownGenreState = rememberScrollState()

    var contentId by remember { mutableStateOf("") }
    var titleText by remember { mutableStateOf("") }
    var posterUrlText by remember { mutableStateOf("") }
    var contentType by remember { mutableStateOf("") }
    var synopsisText by remember { mutableStateOf("") }
    var genreText by remember { mutableStateOf("") }
    var yearReleaseText by remember { mutableStateOf("") }
    var isWatched by remember { mutableStateOf(false) }
    var ratingText by remember { mutableStateOf("") }
    var commentText by remember { mutableStateOf("") }
    var reviewerText by remember { mutableStateOf("") }
    var isTryAddedContent by remember { mutableStateOf(false) }

    var dropdownTypeExpanded by remember { mutableStateOf(false) }
    var dropdownTypeTextFieldSize by remember { mutableStateOf(Size.Zero) }
    var dropdownGenreExpanded by remember { mutableStateOf(false) }
    var dropdownGenreTextFieldSize by remember { mutableStateOf(Size.Zero) }

    if (isTryAddedContent) {
        LaunchedEffect(Unit) {
            if (uiState.isAddDataLoading) {
                Toast.makeText(context, "Adding $contentType...", Toast.LENGTH_SHORT).show()
            }
            delay(500)
            if (uiState.isContentAdded) {
                onDismissRequest()
                onContentAdded()
                Toast.makeText(context, "$contentType Successfully Added!", Toast.LENGTH_SHORT).show()
                contentViewModel.resetAddContentUiState()
            }
            if (uiState.isContentExist) {
                Toast.makeText(context, "$titleText Already Exists!", Toast.LENGTH_SHORT).show()
                contentViewModel.resetAddContentUiState()
            }
            isTryAddedContent = false
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
                        text = "Add Movie/Series",
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
                        .height(400.dp)
                        .then(
                            if (isScrollable) {
                                Modifier.verticalColumnScrollbar(scrollState)
                            } else {
                                Modifier
                            }
                        )
                        .verticalScroll(scrollState)
                ) {
                    Text(
                        text = "Title",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        ),
                    )
                    OutlinedTextField(
                        value = titleText,
                        onValueChange = {
                            titleText = it
                        },
                        label = {
                            Text(
                                text = "Movie/Series Title",
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
                    Spacer(modifier.height(8.dp))
                    Text(
                        text = "Poster URL",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        ),
                    )
                    OutlinedTextField(
                        value = posterUrlText,
                        onValueChange = {
                            posterUrlText = it
                        },
                        label = {
                            Text(
                                text = "Poster URL",
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
                    Spacer(modifier.height(4.dp))
                    Row {
                        Text(
                            text = "Find poster URL",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = Color(0xFF959595)
                            ),
                        )
                        Spacer(modifier.width(4.dp))
                        Text(
                            text = "here",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = Color(0xFF5896FF)
                            ),
                            textDecoration = TextDecoration.Underline,
                            modifier = modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onDismissRequest()
                                navigateToTMDBPage()
                            }
                        )
                    }
                    Spacer(modifier.height(8.dp))
                    Text(
                        text = "Type",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        ),
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            enabled = false,
                            value = contentType,
                            onValueChange = {
                                contentType = it
                            },
                            label = {
                                Text(
                                    text = "Select Type",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                )
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = if (dropdownTypeExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                    contentDescription = "Close",
                                    modifier = modifier
                                        .padding(end = 8.dp)
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null
                                        ) {
                                            dropdownTypeExpanded = !dropdownTypeExpanded
                                        }
                                )
                            },
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(end = 8.dp)
                                .onGloballyPositioned { coordinates ->
                                    dropdownTypeTextFieldSize = coordinates.size.toSize()
                                }
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    dropdownTypeExpanded = !dropdownTypeExpanded
                                },
                            colors = TextFieldDefaults.colors(
                                disabledTextColor = Color.Black,
                                disabledTrailingIconColor = Color.Black,
                                disabledPlaceholderColor = Color.Black,
                                disabledIndicatorColor = Color.Black,
                                disabledLabelColor = Color.Black,
                                disabledContainerColor = Color.White
                            )
                        )
                        DropdownMenu(
                            expanded = dropdownTypeExpanded,
                            onDismissRequest = {
                                dropdownTypeExpanded = false
                            },
                            modifier = modifier
                                .width(with(LocalDensity.current) { dropdownTypeTextFieldSize.width.toDp() })
                        ) {
                            contentTypes.forEach { type ->
                                DropdownMenuItem(
                                    onClick = {
                                        contentType = type
                                        dropdownTypeExpanded = false
                                    },
                                    text = {
                                        Text(
                                            text = type,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }
                    Spacer(modifier.height(8.dp))
                    Text(
                        text = "Synopsis",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        ),
                    )
                    OutlinedTextField(
                        value = synopsisText,
                        onValueChange = {
                            synopsisText = it
                        },
                        label = {
                            Text(
                                text = "Synopsis",
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
                    Spacer(modifier.height(8.dp))
                    Row {
                        Column {
                            Text(
                                text = "Genre",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                ),
                            )
                            Box(
                                modifier = modifier
                                    .width(180.dp)
                            ) {
                                OutlinedTextField(
                                    enabled = false,
                                    value = genreText,
                                    onValueChange = {
                                        genreText = it
                                    },
                                    label = {
                                        Text(
                                            text = "Select Genre",
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp
                                            )
                                        )
                                    },
                                    trailingIcon = {
                                        Icon(
                                            imageVector = if (dropdownGenreExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                            contentDescription = "Close",
                                            modifier = modifier
                                                .padding(end = 8.dp)
                                                .clickable(
                                                    interactionSource = remember { MutableInteractionSource() },
                                                    indication = null
                                                ) {
                                                    dropdownGenreExpanded = !dropdownGenreExpanded
                                                }
                                        )
                                    },
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .padding(end = 8.dp)
                                        .onGloballyPositioned { coordinates ->
                                            dropdownGenreTextFieldSize = coordinates.size.toSize()
                                        }
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null
                                        ) {
                                            dropdownGenreExpanded = !dropdownGenreExpanded
                                        },
                                    colors = TextFieldDefaults.colors(
                                        disabledTextColor = Color.Black,
                                        disabledTrailingIconColor = Color.Black,
                                        disabledPlaceholderColor = Color.Black,
                                        disabledIndicatorColor = Color.Black,
                                        disabledLabelColor = Color.Black,
                                        disabledContainerColor = Color.White
                                    )
                                )
                                DropdownMenu(
                                    scrollState = dropdownGenreState,
                                    expanded = dropdownGenreExpanded,
                                    onDismissRequest = {
                                        dropdownGenreExpanded = false
                                    },
                                    modifier = modifier
                                        .verticalColumnScrollbar(dropdownGenreState)
                                        .width(with(LocalDensity.current) { dropdownGenreTextFieldSize.width.toDp() })
                                        .heightIn(max = 150.dp)
                                ) {
                                    genres.forEach { genre ->
                                        DropdownMenuItem(
                                            onClick = {
                                                genreText = genre
                                                dropdownGenreExpanded = false
                                            },
                                            text = {
                                                Text(
                                                    text = genre,
                                                    style = MaterialTheme.typography.bodyMedium.copy(
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 14.sp
                                                    )
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier.width(4.dp))
                        Column {
                            Text(
                                text = "Release Year",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                ),
                            )
                            OutlinedTextField(
                                value = yearReleaseText,
                                onValueChange = {
                                    yearReleaseText = it
                                },
                                label = {
                                    Text(
                                        text = "Year",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        )
                                    )
                                },
                                modifier = modifier
                                    .padding(end = 8.dp)
                                    .width(125.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        }
                    }
                    Spacer(modifier.height(8.dp))
                    Row {
                        Column {
                            Text(
                                text = "Already Watched?",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                ),
                            )
                            Spacer(modifier.height(8.dp))
                            Button(
                                onClick = {
                                    isWatched = !isWatched
                                },
                                shape = RoundedCornerShape(6.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isWatched) Color(0xFF4CAF50) else Color(
                                        0xFF383838
                                    )
                                ),
                                modifier = modifier
                                    .width(175.dp)
                                    .height(56.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = if (isWatched) Icons.Default.Check else Icons.Default.BookmarkAdd,
                                        contentDescription = "Is Watched",
                                        modifier = modifier
                                            .padding(end = 8.dp)
                                    )
                                    Text(
                                        text = if (isWatched) "Watched" else "Watchlist",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        ),
                                        modifier = modifier.padding(end = 8.dp)
                                    )
                                }
                            }
                        }
                        Spacer(modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Rating",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                ),
                            )
                            OutlinedTextField(
                                enabled = isWatched,
                                value = ratingText,
                                onValueChange = { newValue ->
                                    ratingText = changeRatingValue(newValue)
                                },
                                label = {
                                    Text(
                                        text = if (isWatched) "1.0 - 10.0" else "",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        )
                                    )
                                },
                                modifier = modifier
                                    .padding(end = 8.dp)
                                    .width(125.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        }
                    }
                    Spacer(modifier.height(8.dp))
                    Text(
                        text = "Review",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        ),
                    )
                    OutlinedTextField(
                        enabled = isWatched,
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
                    Text(
                        text = "Reviewer",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        ),
                    )
                    OutlinedTextField(
                        enabled = isWatched,
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
                Spacer(modifier.height(4.dp))
                Button(
                    onClick = {
                        inputValidation(
                            title = titleText,
                            type = contentType,
                            synopsis = synopsisText,
                            genre = genreText,
                            yearRelease = yearReleaseText,
                            isWatched = isWatched,
                            rating = ratingText,
                            comment = commentText,
                            reviewer = reviewerText,
                            context = context
                        ) {
                            contentId = "${titleText.replace(" ", "-").lowercase()}-${contentType.replace(" ", "-").lowercase()}"
                            val body = Content(
                                id = contentId,
                                title = titleText,
                                posterUrl = posterUrlText,
                                type = contentType,
                                synopsis = synopsisText,
                                yearRelease = yearReleaseText,
                                genre = genreText,
                                isWatched = isWatched,
                                personalRating = if (!isWatched) 0.0 else ratingText.toDouble(),
                                comment = if (!isWatched) "" else commentText,
                                reviewedBy = if (!isWatched) "" else reviewerText,
                                isFavorite = false
                            )
                            contentViewModel.insertContent(body)
                            isTryAddedContent = true
                        }
                    },
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF000000)
                    ),
                    modifier = modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = "Save",
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
    type: String,
    synopsis: String,
    genre: String,
    yearRelease: String,
    isWatched: Boolean,
    rating: String,
    comment: String,
    reviewer: String,
    context: Context,
    onValidationSuccess: () -> Unit
) {
    when {
        title.isEmpty() -> {
            Toast.makeText(context, "Please Enter A Title For The Content.", Toast.LENGTH_SHORT).show()
        }
        type.isEmpty() -> {
            Toast.makeText(context, "Please Select A Type.", Toast.LENGTH_SHORT).show()
        }
        synopsis.isEmpty() -> {
            Toast.makeText(context, "Please Provide A Synopsis For The $title.", Toast.LENGTH_SHORT).show()
        }
        genre.isEmpty() -> {
            Toast.makeText(context, "Please Select At Least One Genre.", Toast.LENGTH_SHORT).show()
        }
        yearRelease.isEmpty() -> {
            Toast.makeText(context, "Please Enter The Release Year Of The $type.", Toast.LENGTH_SHORT).show()
        }
        isWatched && (rating.isEmpty() || comment.isEmpty() || reviewer.isEmpty()) -> {
            when {
                rating.isEmpty() -> Toast.makeText(context, "Please Provide A Rating For $title.", Toast.LENGTH_SHORT).show()
                comment.isEmpty() -> Toast.makeText(context, "Please Write An Opinion About $title.", Toast.LENGTH_SHORT).show()
                reviewer.isEmpty() -> Toast.makeText(context, "Please Provide The Reviewer's Name.", Toast.LENGTH_SHORT).show()
            }
        }
        else -> {
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