package com.a_blekot.shlokas.android_ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.TabRowDefaults.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.a_blekot.shlokas.android_ui.theme.Dimens
import com.a_blekot.shlokas.android_ui.theme.Dimens.borderSmall
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingS
import com.a_blekot.shlokas.android_ui.theme.Dimens.radiusSmall

@Composable
fun InfoPopup(info: FtueInfo = ftueInfo(), modifier: Modifier = Modifier, onClose: () -> Unit) {
    check(info.isNotEmpty()) {
        "InfoPopup list should not be empty!"
    }
    val page = remember { mutableStateOf(0) }
    val maxPage = info.items.lastIndex

    StandartColumn(
        modifier = modifier
            .background(
                colorScheme.background.copy(alpha = 0.94f),
                shape = RoundedCornerShape(radiusSmall)
            )
    ) {
        StandartRow {
            Text(
                text = info.title,
                color = colorScheme.primary,
                style = typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .size(Dimens.iconSizeL)
                    .background(
                        color = colorScheme.primary,
                        shape = CircleShape
                    ),
            ) {
                Icon(
                    Icons.Rounded.Close,
                    "close",
                    tint = colorScheme.onPrimary,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }


        Divider(color = colorScheme.primary, thickness = borderSmall)

        ButtonsRow(page.value, maxPage, title = info.items[page.value].title) { nextPage ->
            page.value = nextPage
        }

        StandartLazyColumn {
            itemsIndexed(info.items[page.value].items, key = { i, _ -> i }) { index, text ->
                FtueInfoRow(index, text)
            }
        }
    }
}

@Composable
private fun FtueInfoRow(index: Int, text: String, modifier: Modifier = Modifier) {
    StandartRow(
        horizontalArrangement = Arrangement.spacedBy(paddingS),
        verticalAlignment = Alignment.Top,
        modifier = modifier
            .background(
                color = colorScheme.primaryContainer.copy(alpha = 0.3f),
                shape = RoundedCornerShape(radiusSmall)
            )
            .padding(vertical = paddingS)
    ) {
        Text(
            "${index + 1})",
            color = colorScheme.onPrimaryContainer,
            style = typography.titleLarge
        )

        Text(
            text = text,
            color = colorScheme.onPrimaryContainer,
            style = typography.titleLarge,
        )
    }
}

@Composable
private fun ButtonsRow(
    page: Int,
    maxPage: Int,
    title: String,
    modifier: Modifier = Modifier,
    onNextPage: (Int) -> Unit
) {
    StandartRow {
        IconButton(
            onClick = { onNextPage(page - 1) },
            enabled = page > 0,
            modifier = Modifier.size(Dimens.iconSizeL),
        ) {
            Icon(
                Icons.Rounded.ArrowBackIos,
                "back",
                tint = colorScheme.primary,
                modifier = Modifier.fillMaxSize().alpha(if (page > 0) 1f else 0.5f)
            )
        }

        Text(
            text = title,
            color = colorScheme.primary,
            style = typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = modifier.weight(1f)
        )

//        Text(
//            text = "${page + 1} / ${maxPage + 1}",
//            color = colorScheme.primary,
//            style = typography.titleLarge
//        )

        IconButton(
            onClick = { onNextPage(page + 1) },
            enabled = page < maxPage,
            modifier = Modifier.size(Dimens.iconSizeL),
        ) {
            Icon(
                Icons.Rounded.ArrowForwardIos,
                "forward",
                tint = colorScheme.primary,
                modifier = Modifier.fillMaxSize().alpha(if (page < maxPage) 1f else 0.5f)
            )
        }
    }
}

fun ftueInfo() =
    FtueInfo(
        title = "О приложении",
        items = listOf(
            ftueInfoItem1(),
            ftueInfoItem1(),
            ftueInfoItem1(),
        )
    )

fun ftueInfoItem1() =
    FtueInfoItem(
        title = "Настройки",
        items = listOf(
            "Количестов повторов для каждого стиха",
            "Номер недели",
            "Количестов повторов для каждого стиха",
            "Номер недели",
            "Количестов повторов для каждого стиха",
            "Номер недели",
            "Количестов повторов для каждого стиха",
            "Номер недели",
        )
    )

data class FtueInfo(
    val title: String = "",
    val items: List<FtueInfoItem> = emptyList(),
) {
    fun isNotEmpty() =
        items.isNotEmpty()
}

data class FtueInfoItem(
    val title: String,
    val items: List<String>
)