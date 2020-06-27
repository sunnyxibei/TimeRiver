package com.sunnyxibei.timeriver.ui.fragment

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Typeface
import android.os.Bundle
import android.text.format.DateUtils
import android.text.style.TypefaceSpan
import android.view.View
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.core.text.scale
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.sunnyxibei.timeriver.R
import com.sunnyxibei.timeriver.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.*

class ListFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnLongClickListener {
            val extras: Navigator.Extras = FragmentNavigatorExtras(image_background to "image_background")
            val descFragmentDirections: NavDirections = DescFragmentDirections.actionGlobalDescFragment()
            findNavController().navigate(descFragmentDirections.actionId, null, null, extras)
            true
        }
        val kaiShu = Typeface.createFromAsset(requireContext().assets, "font/YingZhangKaiShu.ttf")
        tv_title.text = buildSpannedString {
            append("距离")
            scale(2.0f) {
                inSpans(TypefaceSpan("default")) {
                    append("2022/6/30")
                }
            }
            append("祥生云境交房")
        }
        tv_title.typeface = kaiShu
        tv_day.typeface = kaiShu
        tv_remain_day.text =
            "${(Date("2022/06/30").time - (System.currentTimeMillis())) / DateUtils.DAY_IN_MILLIS}"
        val scaleXHolder = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.1f, 1.25f)
        val scaleYHolder = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.1f, 1.25f)
        ObjectAnimator.ofPropertyValuesHolder(image_background, scaleXHolder, scaleYHolder).apply {
            duration = 10000L
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = ObjectAnimator.INFINITE
        }.start()
    }

}