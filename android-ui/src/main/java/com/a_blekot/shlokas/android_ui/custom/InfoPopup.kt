package com.a_blekot.shlokas.android_ui.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.a_blekot.shlokas.android_ui.theme.Dimens
import com.a_blekot.shlokas.android_ui.theme.Dimens.borderSmall
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingS
import com.a_blekot.shlokas.android_ui.theme.Dimens.radiusS
import com.a_blekot.shlokas.common.data.Locales
import com.a_blekot.shlokas.common.data.Locales.ru
import com.a_blekot.shlokas.common.resources.MR
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_about
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_metodics
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_settings
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_10_1
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_11_1
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_11_2
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_11_3
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_11_4
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_1_1
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_1_2
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_1_3
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_1_4
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_2_1
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_2_2
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_2_3
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_2_4
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_3_1
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_3_2
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_3_3
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_4_1
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_4_2
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_5_1
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_6_1
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_7_1
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_7_2
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_8_1
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_8_2
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_9_1
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_9_2
import com.a_blekot.shlokas.common.resources.resolve
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

@Composable
fun InfoPopup(info: FtueInfo, modifier: Modifier = Modifier, onCompleted: () -> Unit) {
    check(info.isNotEmpty()) {
        "InfoPopup list should not be empty!"
    }
    val page = remember { mutableStateOf(0) }
    val maxPage = info.items.lastIndex

    StandartColumn(
        modifier = modifier
            .background(
                colorScheme.background,
                shape = RoundedCornerShape(radiusS)
            )
            .focusable(true)
            .clickable(true) {}
    ) {
        StandartRow {
            Text(
                text = info.title.resolve(LocalContext.current),
                color = colorScheme.primary,
                style = typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )

            if (page.value == maxPage) {
                IconButton(
                    onClick = onCompleted,
                    modifier = Modifier
                        .size(Dimens.iconSizeL),
                ) {
                    Icon(
                        Icons.Rounded.Close,
                        "close",
                        tint = colorScheme.primary,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        Divider(color = colorScheme.primary, thickness = borderSmall)

        ButtonsRow(page.value, maxPage, titleRes = info.items[page.value].title) { nextPage ->
            page.value = nextPage
        }

        StandartLazyColumn {
            info.items[page.value].image?.drawableResId?.let {
                item {
                    Image(
                        painter = painterResource(it),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = "tutorial",
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }

            itemsIndexed(info.items[page.value].items, key = { i, _ -> i }) { index, text ->
                FtueInfoRow(index, text)
            }
        }
    }
}

@Composable
private fun FtueInfoRow(index: Int, textRes: StringResource, modifier: Modifier = Modifier) {
    StandartRow(
        horizontalArrangement = Arrangement.spacedBy(paddingS),
        verticalAlignment = Alignment.Top,
        modifier = modifier
            .background(
                color = colorScheme.primaryContainer.copy(alpha = 0.3f),
                shape = RoundedCornerShape(radiusS)
            )
            .padding(vertical = paddingS)
    ) {

        Text(
            text = textRes.resolve(LocalContext.current),
            color = colorScheme.onPrimaryContainer,
            style = typography.titleLarge,
        )
    }
}

@Composable
private fun ButtonsRow(
    page: Int,
    maxPage: Int,
    titleRes: StringResource,
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
            text = titleRes.resolve(LocalContext.current),
            color = colorScheme.primary,
            style = typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = modifier.weight(1f)
        )

        Text(
            text = "${page + 1} / ${maxPage + 1}",
            color = colorScheme.primary,
            style = typography.titleLarge
        )

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

fun ftueInfo(locale: String) =
    FtueInfo(
        title = tutorial_about,
        items = listOf(
            ftueInfoItem1(),
            ftueInfoItem2(),
            ftueInfoItem3(),
            ftueInfoItem4(locale),
            ftueInfoItem5(locale),
            ftueInfoItem6(locale),
            ftueInfoItem7(locale),
            ftueInfoItem8(locale),
            ftueInfoItem9_1(locale),
            ftueInfoItem9_2(locale),
            ftueInfoItem10(locale),
            ftueInfoItem11(locale),
        )
    )

fun ftueInfoItem1() =
    FtueInfoItem(
        title = tutorial_metodics,
        image = MR.images.tutorial_1,
        items = listOf(
            tutorial_1_1,
            tutorial_1_2,
            tutorial_1_3,
            tutorial_1_4,
        )
    )

fun ftueInfoItem2() =
    FtueInfoItem(
        title = tutorial_metodics,
        image = MR.images.tutorial_2,
        items = listOf(
            tutorial_2_1,
            tutorial_2_2,
            tutorial_2_3,
            tutorial_2_4,
        )
    )

fun ftueInfoItem3() =
    FtueInfoItem(
        title = tutorial_metodics,
        image = MR.images.tutorial_3,
        items = listOf(
            tutorial_3_1,
            tutorial_3_2,
            tutorial_3_3,
        )
    )

fun ftueInfoItem4(locale: String) =
    FtueInfoItem(
        title = tutorial_metodics,
        image = if (locale == ru) MR.images.tutorial_4_ru else MR.images.tutorial_4_en,
        items = listOf(
            tutorial_4_1,
            tutorial_4_2,
        )
    )

fun ftueInfoItem5(locale: String) =
    FtueInfoItem(
        title = tutorial_metodics,
        image = if (locale == ru) MR.images.tutorial_5_ru else MR.images.tutorial_5_en,
        items = listOf(tutorial_5_1)
    )

fun ftueInfoItem6(locale: String) =
    FtueInfoItem(
        title = tutorial_settings,
        image = if (locale == ru) MR.images.tutorial_6_ru else MR.images.tutorial_6_en,
        items = listOf(tutorial_6_1)
    )

fun ftueInfoItem7(locale: String) =
    FtueInfoItem(
        title = tutorial_settings,
        image = if (locale == ru) MR.images.tutorial_7_ru else MR.images.tutorial_7_en,
        items = listOf(
            tutorial_7_1,
            tutorial_7_2,
        )
    )

fun ftueInfoItem8(locale: String) =
    FtueInfoItem(
        title = tutorial_settings,
        image = if (locale == ru) MR.images.tutorial_8_ru else MR.images.tutorial_8_en,
        items = listOf(
            tutorial_8_1,
            tutorial_8_2,
        )
    )

fun ftueInfoItem9_1(locale: String) =
    FtueInfoItem(
        title = tutorial_settings,
        image = if (locale == ru) MR.images.tutorial_9_1_ru else MR.images.tutorial_9_1_en,
        items = listOf(
            tutorial_9_1,
        )
    )

fun ftueInfoItem9_2(locale: String) =
    FtueInfoItem(
        title = tutorial_settings,
        image = if (locale == ru) MR.images.tutorial_9_2_ru else MR.images.tutorial_9_2_en,
        items = listOf(
            tutorial_9_2,
        )
    )

fun ftueInfoItem10(locale: String) =
    FtueInfoItem(
        title = tutorial_settings,
        image = if (locale == ru) MR.images.tutorial_10_ru else MR.images.tutorial_10_en,
        items = listOf(
            tutorial_10_1,
        )
    )

fun ftueInfoItem11(locale: String) =
    FtueInfoItem(
        title = tutorial_settings,
        image = if (locale == ru) MR.images.tutorial_11_ru else MR.images.tutorial_11_en,
        items = listOf(
            tutorial_11_1,
            tutorial_11_2,
            tutorial_11_3,
            tutorial_11_4,
        )
    )

data class FtueInfo(
    val title: StringResource,
    val items: List<FtueInfoItem> = emptyList(),
) {
    fun isNotEmpty() =
        items.isNotEmpty()
}

data class FtueInfoItem(
    val title: StringResource,
    val image: ImageResource? = null,
    val items: List<StringResource> = emptyList(),
)