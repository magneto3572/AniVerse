import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.generativeai.aniverse.android.ui.utils.dpToPx
import com.generativeai.aniverse.domain.model.RecentRelease
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AutoSlidingSnapCarousel(
    items: List<RecentRelease>,
    modifier: Modifier = Modifier,
    slideDuration: Long = 2000
) {
    val randomItems = remember { items.take(5) }
    var currentPage by remember { mutableIntStateOf(0) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Auto-slide the carousel every 'slideDuration' ms
    LaunchedEffect(Unit) {
        while (true) {
            delay(slideDuration)
            val nextPage = (currentPage + 1) % randomItems.size
            currentPage = nextPage
            coroutineScope.launch {
                listState.animateScrollToItem(nextPage)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        val targetIndex =
                            (listState.firstVisibleItemIndex + (dragAmount / 200).toInt())
                                .coerceIn(0, randomItems.size - 1)
                        currentPage = targetIndex
                    }
                },
            horizontalArrangement = Arrangement.Center
        ) {
            itemsIndexed(randomItems) { _, obj ->
                Box(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .height(400.dp)
                ) {
                    AsyncImage(
                        model = obj.img,
                        contentDescription = obj.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                    )
                }
            }
        }

        GradientBox(
            modifier = Modifier
                .align(Alignment.BottomStart),
            titleString = randomItems[currentPage].name
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            randomItems.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.CenterVertically)
                        .size(if (index == currentPage) 8.dp else 6.dp)
                        .background(
                            color = if (index == currentPage) Color.White else Color.Gray,
                            shape = CircleShape
                        )
                )
            }
        }
    }
}

@Composable
fun GradientBox(modifier: Modifier = Modifier, titleString: String) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.2f),
                        Color.Black.copy(alpha = 0.75f),
                        Color.Black
                    ),
                    startY = 0.dp.dpToPx(),
                    endY = 400.dp.dpToPx()
                )
            )
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp)
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = titleString,
                textAlign = TextAlign.Center,
                maxLines = 2,
                fontSize = 20.sp,
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, start = 16.dp, end = 16.dp)
            )

            AddToWishlistButton(
                modifier = Modifier.width(250.dp).align(Alignment.CenterHorizontally),
                onClick = {
                    Toast.makeText(context, "Dummy Button", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}

@Composable
fun AddToWishlistButton(modifier: Modifier, onClick: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black.copy(alpha = 0.6f)),
        contentPadding = ButtonDefaults.ContentPadding
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            Image(
                painter = painterResource(id = android.R.drawable.ic_input_add),
                contentDescription = "Add Icon",
                modifier = Modifier
                    .size(28.dp)
                    .padding(end = 8.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )

            Text(
                text = "Add to Wishlist",
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}