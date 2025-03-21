package id.jsaproject.flognest.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.jsaproject.flognest.ui.modifiers.verticalColumnScrollbar

@Composable
fun DetailReviewContentCard(
    modifier: Modifier = Modifier,
    review: String,
    reviewer: String,
) {
    val scrollState = rememberScrollState()
    val isScrollable = scrollState.maxValue > 0
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
        Column(
            modifier = modifier
                .fillMaxHeight()
                .then(
                    if (isScrollable) {
                        Modifier.verticalColumnScrollbar(scrollState)
                    } else {
                        Modifier
                    }
                )
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = review,
                color = Color(0xFF000000),
                style = TextStyle(
                    fontSize = 14.sp,
                ),
                modifier = modifier
                    .padding(
                        top = 8.dp,
                        bottom = 8.dp,
                        start = 8.dp,
                        end = 8.dp
                    )
            )
            Spacer(
                modifier = modifier.weight(1f)
            )
            Text(
                text = "Reviewed by: $reviewer",
                color = Color(0xFF000000),
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                ),
                modifier = modifier
                    .padding(
                        top = 8.dp,
                        bottom = 8.dp,
                        start = 8.dp,
                        end = 8.dp
                    )
            )
        }
    }
}