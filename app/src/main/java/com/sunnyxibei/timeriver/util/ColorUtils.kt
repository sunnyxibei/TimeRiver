/*
 *   Copyright 2018 Google LLC
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package com.sunnyxibei.timeriver.util

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Color
import androidx.annotation.*
import androidx.annotation.IntRange
import androidx.core.graphics.ColorUtils
import androidx.core.math.MathUtils
import androidx.palette.graphics.Palette
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Utility methods for working with colors.
 */
object ColorUtils {
    const val IS_LIGHT = 0
    const val IS_DARK = 1
    const val LIGHTNESS_UNKNOWN = 2

    /**
     * Set the alpha component of `color` to be `alpha`.
     */
    @CheckResult
    @ColorInt
    fun modifyAlpha(
        @ColorInt color: Int,
        @IntRange(from = 0, to = 255) alpha: Int
    ): Int {
        return color and 0x00ffffff or (alpha shl 24)
    }

    /**
     * Set the alpha component of `color` to be `alpha`.
     */
    @CheckResult
    @ColorInt
    fun modifyAlpha(
        @ColorInt color: Int,
        @FloatRange(from = 0.0, to = 1.0) alpha: Float
    ): Int {
        return modifyAlpha(color, (255f * alpha).toInt())
    }

    /**
     * Checks if the most populous color in the given palette is dark
     *
     *
     * Annoyingly we have to return this Lightness 'enum' rather than a boolean as palette isn't
     * guaranteed to find the most populous color.
     */
    @Lightness
    fun isDark(palette: Palette?): Int {
        val mostPopulous: Palette.Swatch =
            getMostPopulousSwatch(palette)
                ?: return LIGHTNESS_UNKNOWN
        return if (isDark(mostPopulous.rgb)) IS_DARK else IS_LIGHT
    }

    fun getMostPopulousSwatch(palette: Palette?): Palette.Swatch? {
        var mostPopulous: Palette.Swatch? = null
        if (palette != null) {
            for (swatch in palette.swatches) {
                if (mostPopulous == null || swatch.population > mostPopulous.population) {
                    mostPopulous = swatch
                }
            }
        }
        return mostPopulous
    }

    /**
     * Determines if a given bitmap is dark. This extracts a palette inline so should not be called
     * with a large image!!
     *
     *
     * Note: If palette fails then check the color of the central pixel
     */
    fun isDark(bitmap: Bitmap): Boolean {
        return isDark(
            bitmap,
            bitmap.width / 2,
            bitmap.height / 2
        )
    }

    /**
     * Determines if a given bitmap is dark. This extracts a palette inline so should not be called
     * with a large image!! If palette fails then check the color of the specified pixel
     */
    fun isDark(bitmap: Bitmap, backupPixelX: Int, backupPixelY: Int): Boolean {
        // first try palette with a small color quant size
        val palette: Palette = Palette.from(bitmap).maximumColorCount(3).generate()
        return if (palette.swatches.size > 0) {
            isDark(palette) == IS_DARK
        } else {
            // if palette failed, then check the color of the specified pixel
            isDark(
                bitmap.getPixel(
                    backupPixelX,
                    backupPixelY
                )
            )
        }
    }

    /**
     * Check if a color is dark (convert to XYZ & check Y component)
     */
    fun isDark(@ColorInt color: Int): Boolean {
        return ColorUtils.calculateLuminance(color) < 0.5
    }

    /**
     * Calculate a variant of the color to make it more suitable for overlaying information. Light
     * colors will be lightened and dark colors will be darkened
     *
     * @param color the color to adjust
     * @param isDark whether `color` is light or dark
     * @param lightnessMultiplier the amount to modify the color e.g. 0.1f will alter it by 10%
     * @return the adjusted color
     */
    @ColorInt
    fun scrimify(
        @ColorInt color: Int,
        isDark: Boolean,
        @FloatRange(from = 0.0, to = 1.0) lightnessMultiplier: Float
    ): Int {
        var lightnessMultiplier = lightnessMultiplier
        val hsl = FloatArray(3)
        ColorUtils.colorToHSL(color, hsl)
        if (!isDark) {
            lightnessMultiplier += 1f
        } else {
            lightnessMultiplier = 1f - lightnessMultiplier
        }
        hsl[2] = MathUtils.clamp(hsl[2] * lightnessMultiplier, 0f, 1f)
        return ColorUtils.HSLToColor(hsl)
    }

    @ColorInt
    fun scrimify(
        @ColorInt color: Int,
        @FloatRange(from = 0.0, to = 1.0) lightnessMultiplier: Float
    ): Int {
        return scrimify(color, isDark(color), lightnessMultiplier)
    }

    /**
     * Queries the theme of the given `context` for a theme color.
     *
     * @param context the context holding the current theme.
     * @param attrResId the theme color attribute to resolve.
     * @return the theme color
     */
    @ColorInt
    fun getThemeColor(context: Context, @AttrRes attrResId: Int): Int {
        val a = context.obtainStyledAttributes(null, intArrayOf(attrResId))
        return try {
            a.getColor(0, Color.MAGENTA)
        } finally {
            a.recycle()
        }
    }

    /**
     * Whether the current configuration is a dark theme i.e. in Night configuration.
     */
    fun isDarkTheme(context: Context): Boolean {
        return (context.resources.configuration.uiMode
                and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(
        IS_LIGHT,
        IS_DARK,
        LIGHTNESS_UNKNOWN
    )
    annotation class Lightness
}