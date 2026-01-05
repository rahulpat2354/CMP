package com.cis.cmp.core.common

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cmp.composeapp.generated.resources.Res
import cmp.composeapp.generated.resources.icon_back_arrow
import com.cis.cmp.core.theme.Black38
import com.cis.cmp.core.theme.Blue53
import com.cis.cmp.core.theme.Fonts.NotoSans
import com.cis.cmp.core.theme.Gray7D
import com.cis.cmp.core.theme.Red2F
import com.cis.cmp.core.theme.White
import com.cis.cmp.core.common.BarOneExtension.customGradientBackground
import org.jetbrains.compose.resources.painterResource

@Composable
fun BackTopAppBar(
    title: String = "",
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable { onBack() }, contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.icon_back_arrow),
                contentDescription = "icon_back",
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Title
        TextMedium(
            title, size = 18, color = White
        )
    }
}

@Composable
fun TextSmall(text: String, modifier: Modifier = Modifier, color: Color = Blue53, size: Int = 12) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            color = color, fontSize = size.sp,
            fontFamily = NotoSans,
            fontWeight = FontWeight.Normal
        )
    )
}

@Composable
fun TextMedium(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Blue53,
    size: Int = 14
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            color = color, fontSize = size.sp,
            fontFamily = NotoSans,
            fontWeight = FontWeight.Medium,
        )
    )
}

@Composable
fun TextLarge(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Blue53,
    size: Int = 14
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            color = color, fontSize = size.sp,
            fontFamily = NotoSans,
            fontWeight = FontWeight.SemiBold,
        )
    )
}

@Composable
fun InputTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    leading: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorText: String = "",
    enabled: Boolean = true,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    fieldTitle: String = ""
) {
    val interaction = remember { MutableInteractionSource() }
    val focused by interaction.collectIsFocusedAsState()
    val borderColor = when {
        !enabled -> White
        isError -> Red2F
        focused -> White
        else -> White
    }
    val textColor = if (enabled) Black38 else Blue53

    Column(modifier = modifier) {
        TextMedium(fieldTitle, color = Blue53, size = 13)
        Spacer(Modifier.height(6.dp))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            singleLine = singleLine,
            maxLines = maxLines,
            textStyle = LocalTextStyle.current.copy(color = textColor),
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            interactionSource = interaction,
            cursorBrush = SolidColor(Color(0xFF1976D2)),
            decorationBox = { innerTextField ->
                // Container + border
                Row(
                    modifier = modifier
                        .defaultMinSize(minHeight = 44.dp)
                        .background(White, shape)
                        .border(1.dp, borderColor, shape)
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Leading slot
                    if (leading != null) {
                        Box(Modifier.padding(end = 8.dp)) { leading() }
                    }

                    // Text & placeholder
                    Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                        if (value.isEmpty() && !placeholder.isNullOrBlank()) {
                            TextSmall(
                                placeholder,
                                color = Gray7D,
                                size = 14
                            )
                        }
                        innerTextField()
                    }

                    // Trailing slot
                    if (trailing != null) {
                        Box(Modifier.padding(start = 8.dp)) { trailing() }
                    }
                }
            }
        )
        if (errorText.isNotEmpty()) { // 👈 show error only if non-empty
            Spacer(Modifier.height(4.dp))
            TextSmall(
                text = errorText,
                color = Red2F,
                size = 12,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

@Composable
fun MorphingButton(
    text: String,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    onClick: () -> Unit
) {
    // BoxWithConstraints gives us maxWidth at runtime
    BoxWithConstraints(modifier = modifier.fillMaxWidth(), // ensures we get maxWidth
        contentAlignment = Alignment.Center) {
        val fullWidth = this.maxWidth // ✅ actually used

        // Animate width from full screen to 48.dp
        val targetWidth by animateDpAsState(
            targetValue = if (isLoading) 48.dp else fullWidth,
            animationSpec = tween(durationMillis = 400),
            label = "buttonWidthAnim"
        )

        // Animate corner radius (12.dp → circular)
        val cornerRadius by animateDpAsState(
            targetValue = if (isLoading) 24.dp else 160.dp,
            animationSpec = tween(durationMillis = 400),
            label = "cornerAnim"
        )

        // Animate text opacity (visible → invisible)
        val textAlpha by animateFloatAsState(
            targetValue = if (isLoading) 0f else 1f,
            animationSpec = tween(durationMillis = 300),
            label = "textAlphaAnim"
        )

        Button(
            onClick = { if (!isLoading) onClick() },
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(),
            shape = RoundedCornerShape(cornerRadius),
            modifier = Modifier
                .height(48.dp)
                .width(targetWidth)
                .customGradientBackground(shape = RoundedCornerShape(cornerRadius))
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = textColor,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(
                    text = text,
                    color = textColor,
                    modifier = Modifier.alpha(textAlpha)
                )
            }
        }
    }
}