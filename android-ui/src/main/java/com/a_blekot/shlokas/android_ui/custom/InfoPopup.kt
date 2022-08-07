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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.a_blekot.shlokas.android_ui.theme.Dimens
import com.a_blekot.shlokas.android_ui.theme.Dimens.borderSmall
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingS
import com.a_blekot.shlokas.android_ui.theme.Dimens.radiusS
import com.a_blekot.shlokas.common.resources.MR
import dev.icerock.moko.resources.ImageResource

@Composable
fun InfoPopup(info: FtueInfo = ftueInfo(), modifier: Modifier = Modifier, onCompleted: () -> Unit) {
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
                text = info.title,
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

        ButtonsRow(page.value, maxPage, title = info.items[page.value].title) { nextPage ->
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
private fun FtueInfoRow(index: Int, text: String, modifier: Modifier = Modifier) {
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
//        Text(
//            "${index + 1})",
//            color = colorScheme.onPrimaryContainer,
//            style = typography.titleLarge
//        )
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

fun ftueInfo() =
    FtueInfo(
        title = "О приложении",
        items = listOf(
            ftueInfoItem1(),
            ftueInfoItem2(),
            ftueInfoItem3(),
            ftueInfoItem4(),
            ftueInfoItem5(),
            ftueInfoItem6(),
            ftueInfoItem7(),
            ftueInfoItem8(),
            ftueInfoItem9(),
        )
    )

fun ftueInfoItem1() =
    FtueInfoItem(
        title = "Методика изучения",
        image = MR.images.tutorial_1,
        items = listOf(
            "Первая неделя",
            "Повторяем построчно",
            "10 раз каждую строку",
            "нужно выбрать пункт \"по одной строке\" в настройках (см. дальше)",
        )
    )

fun ftueInfoItem2() =
    FtueInfoItem(
        title = "Методика изучения",
        image = MR.images.tutorial_2,
        items = listOf(
            "Вторая неделя",
            "Повторяем по две строки",
            "1я-2я и 3я-4я",
            "нужно выбрать пункт \"по две строки\" в настройках (см. дальше)",
        )
    )

fun ftueInfoItem3() =
    FtueInfoItem(
        title = "Методика изучения",
        image = MR.images.tutorial_3,
        items = listOf(
            "Третья неделя",
            "Повторяем все четыре строки вместе",
            "нужно выбрать пункт \"весь стих\" в настройках (см. дальше)",
        )
    )

fun ftueInfoItem4() =
    FtueInfoItem(
        title = "Методика изучения",
        image = MR.images.tutorial_4,
        items = listOf(
            "Выберите от 1 до 20 стихов",
            "Их Вы выучите за три недели \uD83D\uDCAA \uD83E\uDD13",
        )
    )

fun ftueInfoItem5() =
    FtueInfoItem(
        title = "Методика изучения",
        image = MR.images.tutorial_5,
        items = listOf(
            "Повторяйте стихи вслух \uD83D\uDD0A",
        )
    )

fun ftueInfoItem6() =
    FtueInfoItem(
        title = "Настройки",
        image = MR.images.tutorial_6,
    )

fun ftueInfoItem7() =
    FtueInfoItem(
        title = "Настройки",
        image = MR.images.tutorial_7,
        items = listOf(
            "Рекомендуется - 10 повторений каждого стиха",
        )
    )

fun ftueInfoItem8() =
    FtueInfoItem(
        title = "Настройки",
        image = MR.images.tutorial_8,
        items = listOf(
            "Соответственно для первой, второй и третьей недели",
        )
    )

fun ftueInfoItem9() =
    FtueInfoItem(
        title = "Настройки",
        image = MR.images.tutorial_9,
        items = listOf(
            "Собщить об ошибке",
            "Пожелания по функционалу",
            "Письмо с критикой \uD83D\uDC7A \nили благодарностью \uD83D\uDE07",
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
    val image: ImageResource? = null,
    val items: List<String> = emptyList(),
)