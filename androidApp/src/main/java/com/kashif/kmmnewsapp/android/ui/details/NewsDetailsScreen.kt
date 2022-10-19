package com.kashif.kmmnewsapp.android.ui.details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.google.accompanist.web.WebView
import com.google.accompanist.web.WebViewState
import com.google.accompanist.web.rememberWebViewState
import com.kashif.kmmnewsapp.android.R
import com.kashif.kmmnewsapp.android.ui.components.KmmNewsAPPTopBar
import com.kashif.kmmnewsapp.android.ui.theme.KMMNewsTheme
import com.kashif.kmmnewsapp.domain.domain_model.HeadlineDomainModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Destination
@Composable
fun NewsDetailsScreen(destinationsNavigator: DestinationsNavigator, headline: HeadlineDomainModel) {


    Scaffold(topBar = {
        KmmNewsAPPTopBar(titleRes = R.string.news_details, navigationIcon = {
            IconButton(onClick = { destinationsNavigator.navigateUp() }) {

                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )

            }
        })
    }, floatingActionButton = {
        FloatingActionButton(onClick = {

        }) {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = stringResource(id = R.string.back)
            )
        }
    }, floatingActionButtonPosition = FabPosition.End) { innerPadding ->
        val webviewState = rememberWebViewState(url = headline.url)
        val listState = rememberLazyListState()
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .consumedWindowInsets(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            state = listState,
            contentPadding = PaddingValues(16.dp),
            content = {
                headlineHeader(
                    headline = headline, webviewState = webviewState
                )
            })

    }


}


@SuppressLint("SetJavaScriptEnabled")
fun LazyListScope.headlineHeader(headline: HeadlineDomainModel, webviewState: WebViewState) {


    item {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            model = ImageRequest.Builder(
                LocalContext.current
            ).scale(Scale.FILL).data(headline.urlToImage).crossfade(true).build(),
            contentDescription = headline.title,
            contentScale = ContentScale.FillBounds
        )
    }
    item {

        Text(
            text = headline.title,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
    item {
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Text(
                text = headline.author, style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = headline.source, style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = headline.publishedAt, style = MaterialTheme.typography.bodySmall
            )

        }
    }

    item {
        Text(
            text = headline.description,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)
        )
    }

    item {
        WebView(

            state = webviewState,
            onCreated = { it.settings.javaScriptEnabled = true },
            captureBackPresses = false,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()

        )
    }


}


@Preview
@Composable
fun NewsDetailScreenAppBarPreview() {
    KMMNewsTheme() {
       // NewsDetails(HeadlineDomainModel())
    }

}
