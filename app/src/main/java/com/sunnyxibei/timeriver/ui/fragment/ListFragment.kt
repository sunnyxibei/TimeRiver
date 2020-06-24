package com.sunnyxibei.timeriver.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.sunnyxibei.timeriver.R
import com.sunnyxibei.timeriver.base.BaseFragment
import com.sunnyxibei.timeriver.extension.setOnLimitClickListener
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnLimitClickListener {
            val extras: Navigator.Extras =
                FragmentNavigatorExtras(image_destination to "image_destination")
            val descFragmentDirections: NavDirections = DescFragmentDirections.actionGlobalDescFragment()
            findNavController().navigate(descFragmentDirections.actionId, null, null, extras)
        }
    }

}