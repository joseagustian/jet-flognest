package id.jsaproject.flognest.ui.screen.poster

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun TheMovieDBWebViewScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    var isWebViewLoading by remember { mutableStateOf(true) }
    var isWebViewReady by remember { mutableStateOf(false) }
    var posterUrl by remember { mutableStateOf("https://www.themoviedb.org/") }
    var webViewRef by remember { mutableStateOf<WebView?>(null) }
    var context = LocalContext.current
    var isGuideDialogVisible by remember { mutableStateOf(false) }

    when {
        isGuideDialogVisible -> {
            SearchPosterURLGuideDialog(
                modifier = modifier,
                onDismissRequest = { isGuideDialogVisible = false }
            )
        }
    }

    Column(
        modifier = modifier
        .fillMaxSize()
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 10.dp,
                    bottom = 10.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                contentDescription = "Back",
                modifier = modifier
                    .size(22.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onBack()
                    }
            )
            Spacer(modifier = modifier.weight(1f))
            Box(
                modifier = modifier
                    .clip(
                        shape = RoundedCornerShape(50.dp)
                    )
                    .background(Color(0xFFEEEEEE))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier
                        .padding(
                            top = 5.dp,
                            bottom = 5.dp
                        )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back Web Page",
                        modifier = modifier
                            .size(32.dp)
                            .padding(
                                start = 10.dp,
                                end = 5.dp,
                            )
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                webViewRef?.goBack()
                            }
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Box(
                            modifier = modifier
                                .width(220.dp)
                                .clip(
                                    shape = RoundedCornerShape(50.dp)
                                )
                                .background(Color(0xFFE2E2E2))
                                .padding(
                                    start = 10.dp,
                                    end = 10.dp,
                                    top = 5.dp,
                                    bottom = 5.dp
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    posterUrl,
                                    fontSize = 12.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontWeight = FontWeight.Bold,
                                    modifier = modifier
                                        .width(165.dp)
                                )
                                Icon(
                                    imageVector = Icons.Filled.ContentCopy,
                                    contentDescription = "Forward Web Page",
                                    modifier = modifier
                                        .size(24.dp)
                                        .padding(
                                            start = 10.dp,
                                        )
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null
                                        ) {
                                            val clipboardManager =
                                                context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                            val clipData =
                                                ClipData.newPlainText("Poster URL", posterUrl)
                                            clipboardManager.setPrimaryClip(clipData)
                                            Toast.makeText(
                                                context,
                                                "URL Copied To Clipboard",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                )
                            }
                        }
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Forward Web Page",
                        modifier = modifier
                            .size(32.dp)
                            .padding(
                                start = 5.dp,
                                end = 10.dp
                            )
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                webViewRef?.goForward()
                            }
                    )
                }
            }
            Spacer(modifier = modifier.weight(1f))
            Box(
                modifier = modifier
                    .size(38.dp)
                    .clip(
                        shape = CircleShape
                    )
                    .background(Color(0xFFEFEFEF))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        isGuideDialogVisible = true
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.QuestionMark,
                    contentDescription = "Guide",
                    modifier = modifier
                        .padding(8.dp)
                )
            }
        }
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isWebViewReady) {
                AndroidView(
                    factory = { context ->
                        WebView(context).apply {
                            webViewRef = this
                            clipToOutline = true
                            webViewClient = object : WebViewClient() {
                                override fun onPageFinished(view: WebView?, url: String?) {
                                    isWebViewLoading = false
                                    super.onPageFinished(view, url)
                                    posterUrl = url ?: ""
                                }
                            }
                            settings.javaScriptEnabled = true
                            settings.loadWithOverviewMode = true
                            settings.useWideViewPort = true
                            settings.setSupportZoom(true)
                            loadUrl(posterUrl)
                        }
                    },
                    modifier = modifier.fillMaxSize()
                )
            }

            LaunchedEffect(Unit) {
                isWebViewReady = true
            }

            if (isWebViewLoading) {
                CircularProgressIndicator()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchPosterURLGuideDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit
) {
    val steps = listOf(
        "Search for the movie or series on the website.",
        "Select the movie or series.",
        "Go to the 'Media' tab at the top of the page.",
        "Click on 'Poster'.",
        "Choose the poster you like.",
        "Copy the URL from the address bar."
    )

    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White),
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "How to Find a Poster URL",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        ),
                    )
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onDismissRequest()
                            }
                    )
                }

                steps.forEachIndexed { index, step ->
                    Text(
                        text = "${index + 1}. $step",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        ),
                    )
                }
            }
        }
    }
}
