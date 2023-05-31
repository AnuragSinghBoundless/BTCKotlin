package com.crickex.india.crickex.btcwithmvvm.ui.login.ui.gallery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.crickex.india.crickex.btcwithmvvm.R
import com.crickex.india.crickex.btcwithmvvm.databinding.FragmentGalleryBinding
import com.crickex.india.crickex.btcwithmvvm.ui.login.api.RetrofitHelper
import com.crickex.india.crickex.btcwithmvvm.ui.login.api.ServerServices
import com.crickex.india.crickex.btcwithmvvm.ui.login.base.BaseFragment
import com.crickex.india.crickex.btcwithmvvm.ui.login.model.viewGr.ViewGrModelItem
import com.crickex.india.crickex.btcwithmvvm.ui.login.repo.CompanyLoginRepo
import com.crickex.india.crickex.btcwithmvvm.ui.login.ui.MapsActivity
import com.crickex.india.crickex.btcwithmvvm.ui.login.ui.gallery.adapter.ViewGrAdapter

import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.AppUtils
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.Constants
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.States

import com.crickex.india.crickex.btcwithmvvm.ui.login.view_modelFactory.LoginFactory
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class GalleryFragment : BaseFragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var searchedText = ""
    var billingFrm = ""
    lateinit var galleryViewModel: GalleryViewModel
    var listGr = ArrayList<ViewGrModelItem>()

    companion object {
        fun getInstance(): GalleryFragment {
            return GalleryFragment()
        }

        val TAG: String = GalleryFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(eventType: String) {
        searchedText = eventType

        if (binding.rdBTC.isChecked || binding.rdJSL.isChecked) {
            callViewGrApi(searchedText)
        } else {
            showDialogSuccessOrError(
                getString(R.string.error),
                "Please select at least one billing firm",
                Constants.ERROR_LOTTIE
            )
        }

    }

    private fun callViewGrApi(searchedText: String) {
        showLoader()
        galleryViewModel.callViewGrDetatils(searchedText, billingFrm)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EventBus.getDefault().register(this)
        callObserver()
        clicks()
    }

    private fun callObserver() {
        val loginService = RetrofitHelper.getInstance().create(ServerServices::class.java)
        val repo = CompanyLoginRepo(loginService)
        galleryViewModel = ViewModelProvider(this, LoginFactory(repo))[GalleryViewModel::class.java]

        galleryViewModel.grData.observe(viewLifecycleOwner) {
            when (it) {
                is States.FAILURE -> {
                    dismissLoader()
                    showDialogSuccessOrError(
                        getString(R.string.error),
                        it.errorMessage,
                        Constants.ERROR_LOTTIE
                    )

                }
                is States.LOADING -> {


                }
                is States.SUCCESS -> {
                    dismissLoader()
                    it.data?.let { it1 ->
                        listGr.clear()
                        listGr.addAll(it1)
                        callBindRvGR()
                    }


                }
                else -> {}
            }
        }
    }

    private fun callBindRvGR() {
        AppUtils.mainRVParms(requireContext(), binding.rvViewGR)
        val viewGrAdapter = ViewGrAdapter(requireContext(), listGr)
        binding.rvViewGR.adapter = viewGrAdapter
    }

    private fun clicks() {

        binding.rdGrp.setOnCheckedChangeListener { p0, p1 ->
            if (p1 == R.id.rdBTC) {
                billingFrm = "1"
                callViewGrApi(searchedText)

            } else {
                billingFrm = "4"
                callViewGrApi(searchedText)

            }
        }



        binding.txtSearch.setOnClickListener {
            //showSearchGr()
            startActivity(Intent(requireContext(), MapsActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        EventBus.getDefault().unregister(this)

    }
}