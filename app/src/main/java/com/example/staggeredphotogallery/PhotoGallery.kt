package com.example.staggeredphotogallery

import android.content.Context
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun PhotoGalleryScreen(photos: List<PhotoItem>) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Valorant Agents",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )

        // Grid of photos
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 120.dp), // Adaptive grid for staggered effect
            contentPadding = PaddingValues(15.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(photos) { photo ->
                PhotoItemView(photo, context)
            }
        }
    }
}

@Composable
fun PhotoItemView(photo: PhotoItem, context: Context) {
    var isExpanded by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(targetValue = if (isExpanded) 1.2f else 1f, label = "")

    val randomHeightFactor = remember { Random.nextFloat() * 0.5f + 0.75f } // Varying heights (0.75x to 1.25x)
    val resId = getDrawableResId(context, photo.fileName)

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(6.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
            .clickable { isExpanded = !isExpanded }
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = resId),
                contentDescription = photo.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(randomHeightFactor) // Assigns varying height ratios
                    .graphicsLayer(scaleX = scale, scaleY = scale)
            )
            Text(
                text = photo.title,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                textAlign = TextAlign.Center
            )
        }
    }

    LaunchedEffect(isExpanded) {
        if (isExpanded) {
            delay(2000)
            isExpanded = false
        }
    }
}


fun getDrawableResId(context: Context, fileName: String): Int {
    return context.resources.getIdentifier(fileName, "drawable", context.packageName)
}


