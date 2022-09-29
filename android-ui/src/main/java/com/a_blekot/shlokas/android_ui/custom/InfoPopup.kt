package com.a_blekot.shlokas.android_ui.custom

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.DoneOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.TabRowDefaults.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.a_blekot.shlokas.android_ui.theme.Dimens
import com.a_blekot.shlokas.android_ui.theme.Dimens.borderS
import com.a_blekot.shlokas.android_ui.theme.Dimens.buttonHeight
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingS
import com.a_blekot.shlokas.android_ui.theme.Dimens.radiusM
import com.a_blekot.shlokas.android_ui.theme.Dimens.radiusS
import com.a_blekot.shlokas.android_ui.theme.dialogBgColor
import com.a_blekot.shlokas.common.data.Locales.ru
import com.a_blekot.shlokas.common.resources.MR
import com.a_blekot.shlokas.common.resources.MR.strings.label_show_later
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
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_about
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_metodics
import com.a_blekot.shlokas.common.resources.MR.strings.tutorial_settings
import com.a_blekot.shlokas.common.resources.resolve
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

@Composable
fun InfoPopup(info: FtueInfo, modifier: Modifier = Modifier, onSkip: () -> Unit, onComplete: () -> Unit) {
    check(info.isNotEmpty()) {
        "InfoPopup list should not be empty!"
    }
    val page = remember { mutableStateOf(0) }
    val maxPage = info.items.lastIndex

    BackHandler {
        onSkip.invoke()
    }

    Box(
        modifier = modifier
            .background(dialogBgColor)
            .focusable(true)
            .clickable(true) {},
        contentAlignment = Alignment.Center
    ) {
        StandartColumn(
            modifier = Modifier
                .fillMaxSize(0.9f)
                .background(
                    colorScheme.background,
                    shape = RoundedCornerShape(radiusM)
                )
                .focusable(true)
                .clickable(true) {}
        ) {
            Text(
                text = resolveString(info.title),
                color = colorScheme.primary,
                style = typography.headlineLarge,
                textAlign = TextAlign.Center,
            )

            Divider(color = colorScheme.primary, thickness = borderS)

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

            if (page.value == maxPage) {
                Spacer(modifier = Modifier.height(paddingS))
                CloseButton(onComplete)
                Spacer(modifier = Modifier.height(paddingS))
            }

            Spacer(modifier = Modifier.weight(1f))

            Spacer(modifier = Modifier.height(paddingS))
            SkipButton(modifier = Modifier.fillMaxWidth(0.8f), onSkip)
            Spacer(modifier = Modifier.height(paddingS))
        }
    }
}

@Composable
private fun SkipButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .height(buttonHeight)
            .background(
                color = colorScheme.secondaryContainer,
                shape = RoundedCornerShape(radiusM)
            )
            .border(
                width = borderS,
                color = colorScheme.primary,
                shape = RoundedCornerShape(radiusM)
            )
            .clickable { onClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = resolveString(label_show_later),
            color = colorScheme.onPrimaryContainer,
            style = typography.headlineSmall,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun CloseButton(onClick: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(Dimens.iconSizeXL)
            .scale(scale)
            .background(
                color = colorScheme.secondaryContainer,
                shape = RoundedCornerShape(radiusM)
            ),
    ) {
        Icon(
            Icons.Rounded.DoneOutline,
            "completed",
            tint = colorScheme.onSecondaryContainer,
            modifier = Modifier.fillMaxSize()
        )
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
            text = resolveString(textRes),
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
            text = resolveString(titleRes),
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

        val infiniteTransition = rememberInfiniteTransition()
        val scale by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.4f,
            animationSpec = infiniteRepeatable(
                animation = tween(700, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        IconButton(
            onClick = { onNextPage(page + 1) },
            enabled = page < maxPage,
            modifier = Modifier
                .size(Dimens.iconSizeL)
                .scale(scale)
            ,
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