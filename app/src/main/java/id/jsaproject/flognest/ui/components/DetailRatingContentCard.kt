package id.jsaproject.flognest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun getRatingDescription(rating: Double, type: String): String {
    return when (rating) {
        0.0 -> "Not Rated"
        in 1.0..3.9 -> "Very Bad"
        in 4.0..5.4 -> "Almost Bad"
        in 5.5..6.9 -> "Mediocre"
        in 7.0..8.9 ->
            if (type == "Movie" || type == "Short Film") "Good Movie!"
            else if (type == "Series" || type == "TV Series" || type == "Web Series") "Great Series!"
            else "Good Quality!"
        in 9.0..10.0 ->
            if (type == "Movie" || type == "Short Film") "Absolute Cinema!"
            else if (type == "Series" || type == "TV Series" || type == "Web Series") "Epic Series!"
            else "Masterpiece!"
        else -> "Invalid Rating"
    }
}

fun getShortOpinion(rating: Double, type: String): String {
    return when (rating) {
        0.0 -> "N/A"
        in 1.0..3.9 -> getShortMessage(type, "really bad and definitely not recommended.")
        in 4.0..5.4 -> getShortMessage(type, "isn’t great and probably not worth your time.")
        in 5.5..6.9 -> getShortMessage(type, "is okay, mildly entertaining.")
        in 7.0..8.9 -> getShortMessage(type, "is really good! You should probably watch this.")
        in 9.0..10.0 -> getShortMessage(type, "is a true masterpiece! You really must watch this!")
        else -> "Invalid rating."
    }
}

private fun getShortMessage(type: String, message: String): String {
    val base = if (type == "Movie") "This movie" else if (type == "Series" || type == "TV Series" || type == "Web Series") "This series" else "This content"
    return "$base $message"
}



@Composable
fun DetailRatingContentCard(
    modifier: Modifier = Modifier,
    personalRating: Double,
    type: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 5.dp,
                horizontal = 8.dp
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEEEEEE),
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Is It Worth Watching?",
                    color = Color(0xFF000000),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    ),
                    modifier = modifier.padding(
                        top = 8.dp,
                        bottom = 8.dp,
                        start = 8.dp,
                        end = 8.dp
                    )
                )
                Text(
                    text = getShortOpinion(
                        personalRating,
                        type
                    ),
                    color = Color(0xFF000000),
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                    ),
                    modifier = modifier.padding(
                        top = 8.dp,
                        start = 8.dp,
                        end = 8.dp
                    )
                        .width(225.dp)
                )
            }
            Spacer(modifier.weight(1f))
            RatingWidget(
                modifier = modifier,
                personalRating = personalRating,
                type = type
            )
        }
    }
}

@Composable
fun RatingWidget(
    modifier: Modifier = Modifier,
    personalRating: Double,
    type: String
) {
    Column {
        Box(
            modifier = modifier
                .width(145.dp)
                .height(40.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 8.dp,
                        topEnd = 8.dp
                    )
                )
                .background(
                    color = Color(0xFFEFAD00)
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.Star,
                    contentDescription = "Rating Icon",
                    tint = Color(0xFF000000),
                    modifier = modifier.padding(
                        top = 8.dp,
                        bottom = 8.dp,
                    )
                )
                Text(
                    "$personalRating",
                    color = Color(0xFF000000),
                    style = TextStyle(
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = modifier.padding(
                        top = 8.dp,
                        bottom = 8.dp,
                    )
                )
            }
        }
        Box(
            modifier = modifier
                .width(145.dp)
                .height(40.dp)
                .clip(
                    RoundedCornerShape(
                        bottomStart = 8.dp,
                        bottomEnd = 8.dp
                    )
                )
                .background(
                    color = Color(0xFF000000)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                getRatingDescription(
                    personalRating,
                    type
                ),
                color = Color(0xFFFFFFFF),
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                ),
                modifier = modifier.padding(
                    top = 8.dp,
                    bottom = 8.dp,
                )
            )
        }
    }
}