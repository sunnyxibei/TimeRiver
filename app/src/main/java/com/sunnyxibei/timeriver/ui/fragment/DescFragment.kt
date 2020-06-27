package com.sunnyxibei.timeriver.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.sunnyxibei.timeriver.R
import com.sunnyxibei.timeriver.base.BaseFragment
import com.sunnyxibei.timeriver.ui.widget.ElasticDragDismissFrameLayout
import kotlinx.android.synthetic.main.fragment_desc.*
import timber.log.Timber

class DescFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_desc

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        draggable_frame.addListener(object : ElasticDragDismissFrameLayout.ElasticDragDismissCallback() {
            override fun onDragDismissed() {
                Timber.d("DescFragment, onDragDismissed")
                findNavController().popBackStack()
            }
        })
    }

}