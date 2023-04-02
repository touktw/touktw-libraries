package com.touktw.design.base.color

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.max

@Immutable
data class ColorScheme(
    val primary: Color = brown40,
    val onPrimary: Color = white,
    val primaryContainer: Color = brown90,
    val onPrimaryContainer: Color = brown10,
    val secondary: Color = dOrange40,
    val onSecondary: Color = white,
    val secondaryContainer: Color = dOrange90,
    val onSecondaryContainer: Color = dOrange10,
    val tertiary: Color = gray40,
    val onTertiary: Color = white,
    val tertiaryContainer: Color = gray90,
    val onTertiaryContainer: Color = gray10,
    val background: Color = white,
    val onBackground: Color = gray90,
    val outline: Color = gray40,
)

fun defaultColor(darkMode: Boolean): ColorScheme =
    if (darkMode) {
        ColorScheme(
            background = gray90
        )
    } else {
        ColorScheme()
    }

@SuppressWarnings("unused")
@Composable
fun ColorChip(
    name: String,
    container: Color,
    onContainer: Color,
    width: Int = 0,
    onPositionedWidth: (width: Int) -> Unit,
) {
    val density = LocalDensity.current
    Box(
        modifier = Modifier
            .then(
                if (width > 0) Modifier.width(with(density) { width.toDp() })
                else Modifier
            )
            .background(color = container)
            .padding(all = 20.dp)
            .onGloballyPositioned {
                onPositionedWidth(it.size.width)
            }
    ) {
        val textStyle = TextStyle.Default.merge(TextStyle(color = onContainer))
        BasicText(
            text = name,
            style = textStyle,
        )
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    name = "Night"
)
@Composable
fun ColorChipPreview() {
    val colorScheme = defaultColor(isSystemInDarkTheme())
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            var width by remember { mutableStateOf(0) }
            ColorChip(
                name = "Primary",
                container = colorScheme.primary,
                onContainer = colorScheme.onPrimary,
                width = width,
            ) {
                width = max(width, it)
            }
            ColorChip(
                name = "PrimaryContainer",
                container = colorScheme.primaryContainer,
                onContainer = colorScheme.onPrimaryContainer,
                width = width,
            ) {
                width = max(width, it)
            }
            ColorChip(
                name = "Secondary",
                container = colorScheme.secondary,
                onContainer = colorScheme.onSecondary,
                width = width,
            ) {
                width = max(width, it)
            }
            ColorChip(
                name = "SecondaryContainer",
                container = colorScheme.secondaryContainer,
                onContainer = colorScheme.onSecondaryContainer,
                width = width,
            ) {
                width = max(width, it)
            }
            ColorChip(
                name = "Tertiary",
                container = colorScheme.tertiary,
                onContainer = colorScheme.onTertiary,
                width = width,
            ) {
                width = max(width, it)
            }
            ColorChip(
                name = "TertiaryContainer",
                container = colorScheme.tertiaryContainer,
                onContainer = colorScheme.onTertiaryContainer,
                width = width
            ) {
                width = max(width, it)
            }
        }
    }
}


