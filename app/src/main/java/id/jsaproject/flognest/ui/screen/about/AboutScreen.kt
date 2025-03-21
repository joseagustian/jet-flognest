package id.jsaproject.flognest.ui.screen.about

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import id.jsaproject.flognest.R

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 14.dp
            )
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.flognest_logo_wbg),
            contentDescription = "Flognest Logo",
            modifier = modifier
                .size(200.dp)
        )
        Text(
            text = "Flognest is your go-to app for tracking movies, TV shows, and web content. " +
                    "Easily organize your watchlist, rate, and review what you've watched, and update them anytime. " +
                    "You also can filter your collection by title, favorite status, genre, " +
                    "and more to keep everything in order.",
            textAlign = TextAlign.Justify,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 1.2.em,
            modifier = modifier.fillMaxWidth()
        )
        Spacer(modifier = modifier.height(45.dp))
        Text(
            "Developer Profile",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    vertical = 5.dp,
                ),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFEEEEEE),
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Spacer(modifier.height(5.dp))
            Row(
                modifier =  modifier.padding(8.dp)
            ) {
                Box(
                    modifier = modifier
                        .size(135.dp)
                        .clip(
                            shape = RoundedCornerShape(6.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dev_logo),
                        contentDescription = "Developer Photo",
                        modifier = modifier.size(120.dp)
                    )
                }
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = modifier.padding(
                        start = 8.dp
                    )
                ) {
                    Text(
                        text = "Jose Agustian",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier.height(20.dp))
                    Text(
                        text = "Email",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 1.2.em
                    )
                    Text(
                        text = "joseagustian37@gmail.com",
                        fontSize = 12.sp,
                    )
                    Spacer(modifier.height(15.dp))
                    Text(
                        text = "GitHub",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 1.em
                    )
                    Text(
                        text = "https://github.com/joseagustian",
                        fontSize = 12.sp,
                    )
                }
            }
        }
        Spacer(modifier = modifier.height(65.dp))
        Text(
            "App Version",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 1.em
        )
        Text(
            text = "Version 1.0 (2025)",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
}