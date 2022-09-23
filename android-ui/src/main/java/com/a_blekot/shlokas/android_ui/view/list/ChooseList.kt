package com.a_blekot.shlokas.android_ui.view.list

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.a_blekot.shlokas.android_ui.custom.SmallColumn
import com.a_blekot.shlokas.android_ui.theme.Dimens.borderS
import com.a_blekot.shlokas.android_ui.theme.Dimens.buttonHeight
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingM
import com.a_blekot.shlokas.android_ui.theme.Dimens.radiusM
import com.a_blekot.shlokas.android_ui.theme.dialogBgColor
import com.a_blekot.shlokas.common.data.ListId
import com.a_blekot.shlokas.common.list_api.ListPresentation

@Composable
fun ChooseList(
    availableLists: List<ListPresentation>,
    modifier: Modifier = Modifier,
    onSelected: (type: ListId) -> Unit,
    onSkip: () -> Unit,
) {
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
        SmallColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    colorScheme.background,
                    shape = RoundedCornerShape(radiusM)
                )
                .padding(paddingM)
                .focusable(true)
                .clickable(true) {},
            verticalArrangement = Arrangement.spacedBy(paddingM)
        ) {
            availableLists.forEach { list ->
                ListButton(list.isSelected, list.title) {
                    onSelected(list.type)
                }
            }
        }
    }
}

@Composable
private fun ListButton(
    isSelected: Boolean,
    title: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(buttonHeight)
            .background(
                color = if (isSelected) colorScheme.secondaryContainer else Color.Transparent,
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
            text = title,
            maxLines = 1,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = colorScheme.onPrimaryContainer
        )
    }
}