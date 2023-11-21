package com.a_blekot.shlokas.android_ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.input.nestedscroll.nestedScroll
//import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.unit.dp
import com.a_blekot.shlokas.android_ui.Spacer

@Composable
fun BottomSheetCard(
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
//            .nestedScroll(rememberNestedScrollInteropConnection())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            BottomSheetDragMarker()
            Spacer(14.dp)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = bottomSheetShape())
                .background(
                    MaterialTheme.colorScheme.background,
                    shape = bottomSheetShape()
                )
                .navigationBarsPadding()
        ) {
            content()
        }
    }
}

private fun bottomSheetShape() = RoundedCornerShape(
    topStart = 16.dp,
    topEnd = 16.dp,
)

@Composable
private fun ColumnScope.BottomSheetDragMarker() {
    Box(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 8.dp)
            .width(40.dp)
            .height(5.dp)
            .clip(shape = RoundedCornerShape(5.dp))
            .background(Color(170, 170, 187))
    )
}
