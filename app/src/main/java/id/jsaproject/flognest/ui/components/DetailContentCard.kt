package id.jsaproject.flognest.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage

@Composable
fun DetailContentCard(
    modifier: Modifier = Modifier,
    posterUrl: String,
    title: String,
    year: String,
    genre: String,
    type: String,
    synopsis: String,
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
            modifier = Modifier
                .padding(8.dp)
        ) {
            if (posterUrl.isEmpty())
                Box(
                    modifier = Modifier
                        .width(145.dp)
                        .height(235.dp)
                        .clip(
                            RoundedCornerShape(7.dp)
                        )
                        .background(
                            color = Color(0xFFDBDBDB)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ImageNotSupported,
                        contentDescription = "Poster Not Found",
                        tint = Color(0xFFFFFFFF),
                        modifier = Modifier
                            .size(65.dp)
                    )
                }
            else SubcomposeAsyncImage (
                model = posterUrl,
                contentDescription = title,
                loading = {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp,
                            color = Color(0xFF4B4B4B)
                        )
                    }
                },
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .width(155.dp)
                    .height(235.dp)
                    .clip(
                        RoundedCornerShape(7.dp)
                    )
            )
            Column {
                Text(
                    text = synopsis,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    fontSize = 14.sp,
                    modifier = Modifier
                        .height(190.dp)
                        .padding(start = 8.dp),
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 1.2.em
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 25.dp,
                            start = 8.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = year,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        fontSize = 14.sp
                    )
                    Text(
                        text = " · ",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        fontSize = 16.sp
                    )
                    Text(
                        text = type,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        fontSize = 14.sp
                    )
                    Text(
                        text = " · ",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        fontSize = 16.sp
                    )
                    Text(
                        text = genre,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}