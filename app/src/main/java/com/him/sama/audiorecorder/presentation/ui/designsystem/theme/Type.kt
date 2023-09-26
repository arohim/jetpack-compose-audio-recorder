package com.him.sama.audiorecorder.presentation.ui.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.him.sama.audiorecorder.R

val MyFontFamily = FontFamily(
    Font(R.font.opensans_light, FontWeight.Light),
    Font(R.font.opensans_medium, FontWeight.Medium),
    Font(R.font.opensans_semibold, FontWeight.SemiBold),
    Font(R.font.opensans_bold, FontWeight.Bold)
)

val DafundDefaultTextStyle = TextStyle(
    fontFamily = MyFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    lineHeight = 24.sp
)

val Typography = Typography()
val Typography.H1Hero: TextStyle
    get() = DafundDefaultTextStyle.copy(
        fontSize = 32.sp,
        lineHeight = 40.sp,
        fontWeight = FontWeight.SemiBold
    )

val Typography.H2Thick: TextStyle
    get() = DafundDefaultTextStyle.copy(
        fontSize = 28.sp,
        lineHeight = 36.sp,
        fontWeight = FontWeight.SemiBold
    )

val Typography.H2Medium: TextStyle
    get() = DafundDefaultTextStyle.copy(
        fontSize = 28.sp,
        lineHeight = 36.sp,
        fontWeight = FontWeight.Medium
    )

val Typography.H3Thick: TextStyle
    get() = DafundDefaultTextStyle.copy(
        fontSize = 26.sp,
        lineHeight = 36.sp,
        fontWeight = FontWeight.SemiBold
    )

val Typography.H3Medium: TextStyle
    get() = DafundDefaultTextStyle.copy(
        fontSize = 26.sp,
        lineHeight = 36.sp,
        fontWeight = FontWeight.Medium
    )

val Typography.H4Thick: TextStyle
    get() = DafundDefaultTextStyle.copy(
        fontSize = 24.sp,
        lineHeight = 32.sp,
        fontWeight = FontWeight.SemiBold
    )

val Typography.H4Medium: TextStyle
    get() = DafundDefaultTextStyle.copy(
        fontSize = 24.sp,
        lineHeight = 32.sp,
        fontWeight = FontWeight.Medium
    )

val Typography.H5Thick: TextStyle
    get() = DafundDefaultTextStyle.copy(
        fontSize = 20.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.SemiBold
    )

val Typography.H5Medium: TextStyle
    get() = DafundDefaultTextStyle.copy(
        fontSize = 20.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.Medium
    )

val Typography.SubHeadThick: TextStyle
    get() = DafundDefaultTextStyle.copy(
        fontSize = 18.sp,
        lineHeight = 25.sp,
        fontWeight = FontWeight.SemiBold
    )

val Typography.SubHeadMedium: TextStyle
    get() = DafundDefaultTextStyle.copy(
        fontSize = 18.sp,
        lineHeight = 25.sp,
        fontWeight = FontWeight.Medium
    )

val Typography.BodyThick: TextStyle
    get() = DafundDefaultTextStyle.copy(
        fontSize = 16.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeight.SemiBold
    )

val Typography.Body: TextStyle
    get() = DafundDefaultTextStyle.copy(
        fontSize = 16.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeight.Medium
    )

val Typography.LabelThick: TextStyle
    get() = DafundDefaultTextStyle.copy(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.SemiBold
    )

val Typography.Label: TextStyle
    get() = DafundDefaultTextStyle.copy(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Medium
    )

val Typography.RemarkThick: TextStyle
    get() = DafundDefaultTextStyle.copy(
        fontSize = 12.sp,
        lineHeight = 17.sp,
        fontWeight = FontWeight.SemiBold
    )

val Typography.Remark: TextStyle
    get() = DafundDefaultTextStyle.copy(
        fontSize = 12.sp,
        lineHeight = 17.sp,
        fontWeight = FontWeight.Medium
    )

val Typography.CTAThick: TextStyle
    get() = DafundDefaultTextStyle.copy(
        fontSize = 16.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeight.SemiBold
    )

val Typography.CTARemark: TextStyle
    get() = DafundDefaultTextStyle.copy(
        fontSize = 16.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeight.Medium
    )
