package com.a_blekot.shlokas.android_ui.view.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.a_blekot.shlokas.android_ui.custom.*
import com.a_blekot.shlokas.android_ui.theme.Dimens
import com.a_blekot.shlokas.android_ui.theme.Dimens.iconSizeL
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingM
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingS
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingXS
import com.a_blekot.shlokas.android_ui.theme.Dimens.paddingZero
import com.a_blekot.shlokas.android_ui.theme.textFieldColors
import com.a_blekot.shlokas.common.data.Locales.en
import com.a_blekot.shlokas.common.data.Locales.ru
import com.a_blekot.shlokas.common.data.Locales.uk
import com.a_blekot.shlokas.common.data.Week
import com.a_blekot.shlokas.common.data.Week.*
import com.a_blekot.shlokas.common.resources.MR.strings.label_autoplay
import com.a_blekot.shlokas.common.resources.MR.strings.label_donate
import com.a_blekot.shlokas.common.resources.MR.strings.label_feedback
import com.a_blekot.shlokas.common.resources.MR.strings.label_four_lines
import com.a_blekot.shlokas.common.resources.MR.strings.label_locale_en
import com.a_blekot.shlokas.common.resources.MR.strings.label_locale_ru
import com.a_blekot.shlokas.common.resources.MR.strings.label_locale_uk
import com.a_blekot.shlokas.common.resources.MR.strings.label_one_line
import com.a_blekot.shlokas.common.resources.MR.strings.label_pause
import com.a_blekot.shlokas.common.resources.MR.strings.label_pause_placeholder
import com.a_blekot.shlokas.common.resources.MR.strings.label_rate_us
import com.a_blekot.shlokas.common.resources.MR.strings.label_repeat_mode
import com.a_blekot.shlokas.common.resources.MR.strings.label_repeats
import com.a_blekot.shlokas.common.resources.MR.strings.label_repeats_placeholder
import com.a_blekot.shlokas.common.resources.MR.strings.label_select_locale
import com.a_blekot.shlokas.common.resources.MR.strings.label_select_tts_voice
import com.a_blekot.shlokas.common.resources.MR.strings.label_share_app
import com.a_blekot.shlokas.common.resources.MR.strings.label_show_close_player_dialog
import com.a_blekot.shlokas.common.resources.MR.strings.label_show_tutorial
import com.a_blekot.shlokas.common.resources.MR.strings.label_two_lines
import com.a_blekot.shlokas.common.resources.MR.strings.label_with_sanskrit
import com.a_blekot.shlokas.common.resources.MR.strings.label_with_translation
import com.a_blekot.shlokas.common.resources.resolve
import com.a_blekot.shlokas.common.settings_api.SettingsComponent
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsView(component: SettingsComponent) {
    val state = component.flow.subscribeAsState().value
    val infoIsVisible = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(horizontal = paddingS)
        ) {
            item {
                val repeats = remember { mutableStateOf(TextFieldValue(text = state.repeats.toString())) }

                OutlinedTextField(
                    value = repeats.value,
                    onValueChange = {
                        repeats.value = it
                        component.setRepeats(it.text.toIntOrNull() ?: 1)
                    },
                    textStyle = typography.titleLarge,
                    maxLines = 1,
                    label = { Text(text = label_repeats.resolve(context)) },
                    colors = textFieldColors(),
                    placeholder = { Text(text = label_repeats_placeholder.resolve(context)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Repeat,
                            tint = colorScheme.primary,
                            contentDescription = "Repeat"
                        )
                    },
                    modifier = Modifier.fillMaxWidth().padding(top = paddingM)
                )
            }

            item {
                val pause = remember { mutableStateOf(TextFieldValue(text = state.pause.toString())) }

                OutlinedTextField(
                    value = pause.value,
                    onValueChange = {
                        pause.value = it
                        component.setPause(it.text.toLongOrNull() ?: 1)
                    },
                    textStyle = typography.titleLarge,
                    maxLines = 1,
                    label = { Text(text = label_pause.resolve(context)) },
                    colors = textFieldColors(),
                    placeholder = { Text(text = label_pause_placeholder.resolve(context)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.PauseCircleOutline,
                            tint = colorScheme.primary,
                            contentDescription = "Pause"
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Weeks(state.week) {
                    component.setWeek(it.ordinal)
                }
            }

            item {
                StandartRow(
                    padding = paddingZero,
                    horizontalArrangement = Arrangement.spacedBy(paddingM),
                ) {
                    StandartCheckBox(state.isAutoplay) {
                        component.setAutoplay(it)
                    }

                    Text(
                        text = label_autoplay.resolve(context),
                        style = typography.titleLarge,
                        color = colorScheme.primary,
                        maxLines = 1,
                    )
                }
            }

            item {
                StandartRow(
                    padding = paddingZero,
                    horizontalArrangement = Arrangement.spacedBy(paddingM),
                ) {
                    StandartCheckBox(state.showClosePlayerDialog) {
                        component.setShowClosePlayerDialog(it)
                    }

                    Text(
                        text = label_show_close_player_dialog.resolve(context),
                        style = typography.titleLarge,
                        color = colorScheme.primary,
                    )
                }
            }

            item {
                StandartRow(
                    padding = paddingZero,
                    horizontalArrangement = Arrangement.spacedBy(paddingM),
                ) {
                    StandartCheckBox(state.withSanskrit) {
                        component.setWithSanskrit(it)
                    }

                    Text(
                        text = label_with_sanskrit.resolve(context),
                        style = typography.titleLarge,
                        color = colorScheme.primary,
                        maxLines = 1,
                    )
                }
            }

            item {
                StandartRow(
                    padding = paddingZero,
                    horizontalArrangement = Arrangement.spacedBy(paddingM),
                ) {
                    StandartCheckBox(state.withTranslation) {
                        component.setWithTranslation(it)
                    }

                    Text(
                        text = label_with_translation.resolve(context),
                        style = typography.titleLarge,
                        color = colorScheme.primary,
                        maxLines = 1,
                    )
                }
            }

            item {
                StandartRow(
                    padding = paddingZero,
                    horizontalArrangement = Arrangement.spacedBy(paddingM),
                    modifier = Modifier.clickable { component.selectTtsVoice() }
                ) {

                    Icon(
                        Icons.Rounded.VolumeUp,
                        "Text to speech settings",
                        tint = colorScheme.primary,
                        modifier = Modifier.size(iconSizeL)
                    )

                    Text(
                        text = label_select_tts_voice.resolve(context),
                        style = typography.titleLarge,
                        color = colorScheme.primary,
                        maxLines = 1,
                    )
                }
            }

            item {
                Divider(color = colorScheme.primary, thickness = Dimens.borderS)
            }

            item {
                Locale(state.locale) {
                    component.setLocale(it)
                }
            }

            if (state.locale.isNotBlank()) {
                item {
                    StandartRow(
                        horizontalArrangement = Arrangement.spacedBy(paddingM),
                        modifier = Modifier.clickable { component.donations() }.padding(top = paddingM)
                    ) {
                        Icon(
                            Icons.Rounded.VolunteerActivism,
                            "donations",
                            tint = colorScheme.primary,
                            modifier = Modifier.size(iconSizeL)
                        )

                        Text(
                            text = label_donate.resolve(context),
                            style = typography.titleLarge,
                            color = colorScheme.primary,
                            maxLines = 1,
                        )
                    }
                }

                item {
                    StandartRow(
                        horizontalArrangement = Arrangement.spacedBy(paddingM),
                        modifier = Modifier.clickable { component.sendEmail() }
                    ) {

                        Icon(
                            Icons.Rounded.Email,
                            "Email",
                            tint = colorScheme.primary,
                            modifier = Modifier.size(iconSizeL)
                        )

                        Text(
                            text = label_feedback.resolve(context),
                            style = typography.titleLarge,
                            color = colorScheme.primary,
                            maxLines = 1,
                        )
                    }
                }

                item {
                    StandartRow(
                        horizontalArrangement = Arrangement.spacedBy(paddingM),
                        modifier = Modifier.clickable { component.shareApp() }
                    ) {

                        Icon(
                            Icons.Rounded.Share,
                            "Share",
                            tint = colorScheme.primary,
                            modifier = Modifier.size(iconSizeL)
                        )

                        Text(
                            text = label_share_app.resolve(context),
                            style = typography.titleLarge,
                            color = colorScheme.primary,
                            maxLines = 1,
                        )
                    }
                }

                item {
                    StandartRow(
                        horizontalArrangement = Arrangement.spacedBy(paddingM),
                        modifier = Modifier.clickable { component.rateUs() }
                    ) {

                        Icon(
                            Icons.Rounded.Star,
                            "Rate us",
                            tint = colorScheme.primary,
                            modifier = Modifier.size(iconSizeL)
                        )

                        Text(
                            text = label_rate_us.resolve(context),
                            style = typography.titleLarge,
                            color = colorScheme.primary,
                            maxLines = 1,
                        )
                    }
                }

                item {
                    StandartRow(
                        horizontalArrangement = Arrangement.spacedBy(paddingM),
                        modifier = Modifier.clickable {
                            infoIsVisible.value = true
                            component.onShowTutorial()
                        }
                    ) {
                        Icon(
                            Icons.Rounded.Info,
                            "Info",
                            tint = colorScheme.primary,
                            modifier = Modifier.size(iconSizeL)
                        )

                        Text(
                            text = label_show_tutorial.resolve(context),
                            style = typography.titleLarge,
                            color = colorScheme.primary,
                            maxLines = 1,
                        )
                    }
                }
            }
        }

        if (infoIsVisible.value) {
            InfoPopup(ftueInfo(state.locale),
                modifier = Modifier.fillMaxSize(),
                onSkip = { infoIsVisible.value = false },
                onComplete = {
                    infoIsVisible.value = false
                    component.onTutorialCompleted()
                }
            )
        }
    }
}

@Composable
private fun Weeks(week: Week, modifier: Modifier = Modifier, onChanged: (Week) -> Unit) {
    val context = LocalContext.current

    SmallColumn(
        verticalArrangement = Arrangement.spacedBy(paddingZero),
        modifier = modifier
    ) {

        Text(
            text = label_repeat_mode.resolve(context),
            style = typography.titleLarge,
            color = colorScheme.primary,
        )

        StandartRow(
            padding = paddingZero,
            horizontalArrangement = Arrangement.spacedBy(paddingS, alignment = Alignment.Start)
        ) {
            RadioButton(
                selected = week == FIRST,
                onClick = { onChanged(FIRST) }
            )
            Text(
                text = label_one_line.resolve(context),
                style = typography.titleLarge,
                color = colorScheme.primary,
                modifier = Modifier.clickable { onChanged(FIRST) }
            )
        }

        StandartRow(
            padding = paddingZero,
            horizontalArrangement = Arrangement.spacedBy(paddingS, alignment = Alignment.Start)
        ) {
            RadioButton(
                selected = week == SECOND,
                onClick = { onChanged(SECOND) },
            )
            Text(
                text = label_two_lines.resolve(context),
                style = typography.titleLarge,
                color = colorScheme.primary,
                modifier = Modifier.clickable { onChanged(SECOND) }
            )
        }

        StandartRow(
            padding = paddingZero,
            horizontalArrangement = Arrangement.spacedBy(paddingS, alignment = Alignment.Start)
        ) {
            RadioButton(
                selected = week == THIRD,
                onClick = { onChanged(THIRD) }
            )
            Text(
                text = label_four_lines.resolve(context),
                style = typography.titleLarge,
                color = colorScheme.primary,
                modifier = Modifier.clickable { onChanged(THIRD) }
            )
        }
    }
}

@Composable
private fun Locale(locale: String, modifier: Modifier = Modifier, onChanged: (String) -> Unit) {
    val context = LocalContext.current

    SmallColumn(
        verticalArrangement = Arrangement.spacedBy(paddingZero),
        modifier = modifier
    ) {

        Text(
            text = label_select_locale.resolve(context),
            style = typography.titleLarge,
            color = colorScheme.primary,
        )

        StandartRow(
            padding = paddingZero,
            horizontalArrangement = Arrangement.spacedBy(paddingS, alignment = Alignment.Start)
        ) {
            RadioButton(
                selected = locale == en,
                onClick = { onChanged(en) }
            )
            Text(
                text = label_locale_en.resolve(context),
                style = typography.titleLarge,
                color = colorScheme.primary,
                modifier = Modifier.clickable { onChanged(en) }
            )
        }

        StandartRow(
            padding = paddingZero,
            horizontalArrangement = Arrangement.spacedBy(paddingS, alignment = Alignment.Start)
        ) {
            RadioButton(
                selected = locale == uk,
                onClick = { onChanged(uk) },
            )
            Text(
                text = label_locale_uk.resolve(context),
                style = typography.titleLarge,
                color = colorScheme.primary,
                modifier = Modifier.clickable { onChanged(uk) }
            )
        }

        StandartRow(
            padding = paddingZero,
            horizontalArrangement = Arrangement.spacedBy(paddingS, alignment = Alignment.Start)
        ) {
            RadioButton(
                selected = locale == ru,
                onClick = { onChanged(ru) },
            )
            Text(
                text = label_locale_ru.resolve(context),
                style = typography.titleLarge,
                color = colorScheme.primary,
                modifier = Modifier.clickable { onChanged(ru) }
            )
        }

        Divider(color = colorScheme.primary, thickness = Dimens.borderS)
    }
}
