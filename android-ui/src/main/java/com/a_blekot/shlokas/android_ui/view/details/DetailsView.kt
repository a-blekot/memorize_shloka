package com.a_blekot.shlokas.android_ui.view.details

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.a_blekot.shlokas.android_ui.custom.StandartCheckBox
import com.a_blekot.shlokas.android_ui.custom.StandartLazyColumn
import com.a_blekot.shlokas.android_ui.custom.StandartRow
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingM
import com.a_blekot.shlokas.android_ui.theme.textFieldColors
import com.a_blekot.shlokas.common.details_api.DetailsComponent
import com.a_blekot.shlokas.common.details_api.DetailsState
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.arkivanov.decompose.value.MutableValue

@Composable
fun DetailsView(component: DetailsComponent) {
    val state = component.flow.subscribeAsState()

    StandartLazyColumn(
        itemPadding = paddingM,
        modifier = Modifier.background(colorScheme.background)
    ) {

        item { ButtonsRow(component, state.value.hasChanges) }

        state.value.config.run {
            item { ShlokaTitle(shloka.title, component) }
//            item { ShlokaSanskrit(shloka.sanskrit, component) }
//            item { ShlokaWords(shloka.words, component) }
//            item { ShlokaTranslation(shloka.translation, component) }
            item { ShlokaSelected(isSelected, component) }

            chunks.forEachIndexed { i, it ->
                item { ChunkView(i, it, component) }
            }
        }
    }
}

@Composable
private fun ShlokaSelected(isSelected: Boolean, component: DetailsComponent, modifier: Modifier = Modifier) =
    StandartRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(paddingM, alignment = Alignment.CenterHorizontally),
    ) {
        Text(
            "is selected",
            color = colorScheme.primary,
            style = typography.titleLarge,
        )

        StandartCheckBox(isSelected) {
            component.setSelected(!it)
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShlokaTitle(originalTitle: String, component: DetailsComponent, modifier: Modifier = Modifier) {
    val title = remember { mutableStateOf(TextFieldValue(text = originalTitle)) }

    OutlinedTextField(
        value = title.value,
        onValueChange = {
            title.value = it
            component.setTitle(it.text)
        },
        textStyle = typography.headlineLarge,
        label = { Text(text = "Title") },
        maxLines = 1,
        colors = textFieldColors(),
        placeholder = { Text(text = "Shloka title") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Badge,
                tint = colorScheme.primary,
                contentDescription = "title"
            )
        },
        modifier = modifier.fillMaxWidth().padding(horizontal = 4.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShlokaSanskrit(originalSanskrit: String, component: DetailsComponent, modifier: Modifier = Modifier) {
    val sanskrit = remember { mutableStateOf(TextFieldValue(text = originalSanskrit)) }

    OutlinedTextField(
        value = sanskrit.value,
        onValueChange = {
            sanskrit.value = it
            component.setSanskrit(it.text)
        },
        textStyle = typography.bodyLarge,
        label = { Text(text = "Sanskrit") },
        colors = textFieldColors(),
        placeholder = { Text(text = "Shloka sanskrit") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Info,
                tint = colorScheme.primary,
                contentDescription = "Sanskrit"
            )
        },
        modifier = modifier.fillMaxWidth().padding(horizontal = 4.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShlokaWords(originalWords: String, component: DetailsComponent, modifier: Modifier = Modifier) {
    val words = remember { mutableStateOf(TextFieldValue(text = originalWords)) }

    OutlinedTextField(
        value = words.value,
        onValueChange = {
            words.value = it
            component.setWords(it.text)
        },
        textStyle = typography.bodyLarge,
        label = { Text(text = "Words") },
        colors = textFieldColors(),
        placeholder = { Text(text = "Shloka words translation") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Info,
                tint = colorScheme.primary,
                contentDescription = "Words"
            )
        },
        modifier = modifier.fillMaxWidth().padding(horizontal = 4.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShlokaTranslation(originalTranslation: String, component: DetailsComponent, modifier: Modifier = Modifier) {
    val translation = remember { mutableStateOf(TextFieldValue(text = originalTranslation)) }

    OutlinedTextField(
        value = translation.value,
        onValueChange = {
            translation.value = it
            component.setTranslation(it.text)
        },
        textStyle = typography.bodyLarge,
        label = { Text(text = "Translation") },
        colors = textFieldColors(),
        placeholder = { Text(text = "Shloka translation") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Info,
                tint = colorScheme.primary,
                contentDescription = "Translation"
            )
        },
        modifier = modifier.fillMaxWidth().padding(horizontal = 4.dp)
    )
}

@Composable
private fun ButtonsRow(component: DetailsComponent, hasChanges: Boolean, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        val pickFileLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { fileUri ->
            if (fileUri != null) {
                fileUri.path?.let { component.setFilePath(it) }
//                /storage/9016-4EF8/Music/Bhaktivaibhava/SB 1.1.1.mp3
            }
        }

        val context = LocalContext.current
        val requestPermissionLauncher =
            rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    pickFileLauncher.launch("audio/*")
                } else {
                    Toast.makeText(context, "Need READ_EXTERNAL_STORAGE permission!", Toast.LENGTH_LONG).show()
                }
            }

        IconButton(
            onClick = { requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE) },
            modifier = modifier.size(48.dp),
        ) {
            Icon(
                Icons.Rounded.FileOpen,
                "file open",
                tint = colorScheme.primary,
                modifier = Modifier.fillMaxSize()
            )
        }

        val infiniteTransition = rememberInfiniteTransition()
        val alpha by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = if (hasChanges) 0.5f else 1.0f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        IconButton(
            enabled = hasChanges,
            onClick = { component.saveChanges() },
            modifier = modifier
                .size(48.dp)
                .alpha(alpha),
        ) {
            Icon(
                Icons.Rounded.Save,
                "save changes",
                tint = colorScheme.primary,
                modifier = Modifier.fillMaxSize()
            )
        }

        IconButton(
            onClick = { component.play() },
            modifier = Modifier.size(48.dp),
        ) {
            Icon(
                Icons.Rounded.PlayCircle,
                "play",
                tint = colorScheme.primary,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00FF00)
@Composable
fun DetailsViewPreview() {
    DetailsView(DetailsComponentStub)
}

object DetailsComponentStub : DetailsComponent {
    override val flow = MutableValue(DetailsState())
}