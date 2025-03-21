package id.jsaproject.flognest.ui.components

import android.widget.Spinner
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage

@Composable
fun ContentListCard(
    modifier: Modifier = Modifier,
    posterUrl: String,
    title: String,
    genre: String,
    type: String,
    synopsis: String,
    isWatched: Boolean,
    isFavorite: Boolean,
    personalRating: Double,
    onFavoriteClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onCardClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 5.dp,
                horizontal = 8.dp
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onCardClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEEEEEE),
        )
    ) {
        Row(
            modifier.padding(
                start = 8.dp,
                end = 8.dp,
                top = 8.dp,
                bottom = 8.dp
            )
        ) {
            Column {
                if (posterUrl.isEmpty())
                    Box(
                        modifier = Modifier
                            .width(105.dp)
                            .height(155.dp)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 8.dp,
                                    topEnd = 8.dp,
                                )
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
                        .width(105.dp)
                        .height(155.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 8.dp,
                                topEnd = 8.dp,
                            )
                        )
                )
                Box(
                    modifier = Modifier
                        .width(105.dp)
                        .clip(
                            RoundedCornerShape(
                                bottomStart = 8.dp,
                                bottomEnd = 8.dp
                            )
                        )
                        .background(
                            color = if (isWatched) Color(0xFF69C669) else Color(0xFFEFAD00)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isWatched) "WATCHED" else "WATCHLIST",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        modifier = Modifier
                            .padding(
                                start = 8.dp,
                                top = 4.dp,
                                bottom = 4.dp,
                                end = 8.dp
                            ),
                        textAlign = TextAlign.End
                    )
                }
            }
            Column(
                modifier = modifier.weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .width(220.dp)
                            .padding(
                                start = 8.dp,
                            ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color(0xFFFF4242) else Color(0xFF000000),
                        modifier = Modifier
                            .size(22.dp)
                            .padding(top = 4.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onFavoriteClick()
                            }
                    )
                }
                Row(
                    modifier.padding(
                        start = 8.dp,
                        top = 2.dp,
                        bottom = 2.dp
                    )
                ) {
                    Text(
                        text = type,
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = " · ",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(
                            start = 2.dp,
                            end = 2.dp
                        )
                    )
                    Text(
                        text = genre,
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Text(
                    text = synopsis,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            top = 4.dp,
                            bottom = 4.dp
                        )
                        .height(85.dp),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Personal Rating",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    fontSize = 12.sp,
                    modifier = Modifier.padding(
                        start = 8.dp
                    )
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Personal Rating",
                            tint = Color(0xFFEFAD00),
                            modifier = Modifier
                                .size(22.dp)
                                .padding(
                                    start = 4.dp,
                                )
                        )
                        Text(
                            text = if (isWatched) "$personalRating/10" else "Not Rated",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            fontSize = 14.sp,
                            modifier = Modifier.padding(
                                start = 6.dp,
                                top = 2.dp
                            )
                        )
                    }
                    Spacer(modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.DeleteForever,
                        contentDescription = "Delete",
                        tint = Color(0xFF212121),
                        modifier = Modifier
                            .size(22.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onDeleteClick()
                            }
                    )
                }
            }
        }
    }
}