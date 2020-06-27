package com.sunnyxibei.timeriver.ui.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.sunnyxibei.timeriver.R
import com.sunnyxibei.timeriver.base.BaseFragment
import com.sunnyxibei.timeriver.ui.widget.ElasticDragDismissFrameLayout
import kotlinx.android.synthetic.main.fragment_desc.*
import timber.log.Timber

class DescFragment : BaseFragment() {

    private val drawableIntArray = intArrayOf(
        R.drawable.img_holder_1,
        R.drawable.img_holder_2,
        R.drawable.img_holder_3,
        R.drawable.img_holder_4,
        R.drawable.img_holder_5
    )

    private val stringArray: Array<String> by lazy {
        requireContext().resources.getStringArray(R.array.poem_array)
    }

    override fun getLayoutId(): Int = R.layout.fragment_desc

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image_background.setImageResource(drawableIntArray.random())
        tv_desc.run {
            text = stringArray.random()
            typeface = Typeface.createFromAsset(requireContext().assets, "font/YingZhangKaiShu.ttf")
            animate().alpha(1f)
        }
        draggable_frame.addListener(object : ElasticDragDismissFrameLayout.ElasticDragDismissCallback() {
            override fun onDragDismissed() {
                Timber.d("DescFragment, onDragDismissed")
                findNavController().popBackStack()
            }
        })
    }

}