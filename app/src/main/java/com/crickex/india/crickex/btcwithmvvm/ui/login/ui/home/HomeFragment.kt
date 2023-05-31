package com.crickex.india.crickex.btcwithmvvm.ui.login.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.crickex.india.crickex.btcwithmvvm.R
import com.crickex.india.crickex.btcwithmvvm.databinding.FragmentHomeBinding
import com.crickex.india.crickex.btcwithmvvm.ui.login.api.RetrofitHelper
import com.crickex.india.crickex.btcwithmvvm.ui.login.api.ServerServices
import com.crickex.india.crickex.btcwithmvvm.ui.login.base.BaseFragment
import com.crickex.india.crickex.btcwithmvvm.ui.login.model.viewDemand.DemandTypeModel
import com.crickex.india.crickex.btcwithmvvm.ui.login.repo.CompanyLoginRepo
import com.crickex.india.crickex.btcwithmvvm.ui.login.room.MyApplication
import com.crickex.india.crickex.btcwithmvvm.ui.login.ui.home.adapter.AdapterViewDemands
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.AppPreferences
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.AppUtils
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.Constants
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.States
import com.crickex.india.crickex.btcwithmvvm.ui.login.viewModel.LoginViewModel
import com.crickex.india.crickex.btcwithmvvm.ui.login.view_modelFactory.LoginFactory
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var loginViewModel: LoginViewModel
    lateinit var homeViewModel: HomeViewModel
    lateinit var appPreferences: AppPreferences
    var message = ""
    var versionStatus = ""
    var DemdCategory1 = ""
    var DemdCategory2 = ""
    var DemdCategory3 = ""
    var DemdCategory4 = ""
    var DemdCategory5 = ""
    var DemdCategory6 = ""
    var DemdCategory7 = ""

    var DemdCategoryParticular1 = ""
    var DemdCategoryParticular2 = ""
    var DemdCategoryParticular3 = ""
    var DemdCategoryParticular4 = ""
    var DemdCategoryParticular5 = ""
    var DemdCategoryParticular6 = ""
    var DemdCategoryParticular7 = ""

    var TotalDemdAmt1 = 0.0
    var TotalDemdAmt2 = 0.0
    var TotalDemdAmt3 = 0.0
    var TotalDemdAmt4 = 0.0
    var TotalDemdAmt5 = 0.0
    var TotalDemdAmt6 = 0.0
    var TotalDemdAmt7 = 0.0

    var tenHold: Double = 0.0
    var confirm: Double = 0.0
    var balance: Double = 0.0
    var status = "0"
    var listDailyDemand = ArrayList<DemandTypeModel>()
    lateinit var adapterDemand: AdapterViewDemands

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun getInstance(): HomeFragment {
            return HomeFragment()
        }

        val TAG: String = HomeFragment::class.java.simpleName
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appPreferences = AppPreferences(requireContext())
        EventBus.getDefault().register(this)
        callObserver()
        callDriverLoginApi()



        takeClick()

    }

    private fun callDailyDemand() {
        showLoader()
        homeViewModel.callDailyDemand(status, appPreferences.getBillingFirm()!!)
    }

    private fun takeClick() {
        binding.onHold.setOnClickListener {

            if (appPreferences.getBillingFirm() == null) {
                showDialogSuccessOrError(
                    getString(R.string.error),
                    "Please set billing Firm first",
                    Constants.ERROR_LOTTIE
                )
                return@setOnClickListener
            }

            showDemandDialog(
                "Hold & Tentative Demand",
                "Total Demand : $tenHold", "Tentative : $TotalDemdAmt2", "On Hold : $TotalDemdAmt4"
            )
        }
        binding.onConfirmed.setOnClickListener {
            if (appPreferences.getBillingFirm() == null) {
                showDialogSuccessOrError(
                    getString(R.string.error),
                    "Please set billing Firm first",
                    Constants.ERROR_LOTTIE
                )
                return@setOnClickListener
            }
            showDemandDialog(
                "Confirm & Approved Demands",
                "Total Demand : $confirm",
                "Confirm : $TotalDemdAmt3",
                "Approved : $TotalDemdAmt5"
            )
        }
        binding.paidBalance.setOnClickListener {
            if (appPreferences.getBillingFirm() == null) {
                showDialogSuccessOrError(
                    getString(R.string.error),
                    "Please set billing Firm first",
                    Constants.ERROR_LOTTIE
                )
                return@setOnClickListener
            }
            showDemandDialog(
                "Total Paid & Reversed",
                "Paid : $TotalDemdAmt6",
                "Balance : $balance",
                "Reversed : $TotalDemdAmt7"
            )
        }
    }

    private fun callTotalDemand() {
        showLoader()
        if (appPreferences.getBillingFirm() != null) {
            homeViewModel.callTotalDemands(
                appPreferences.getBillingFirm()!!
            )
        }

    }

    private fun callObserver() {
        val loginService = RetrofitHelper.getInstance().create(ServerServices::class.java)
        val repo = CompanyLoginRepo(loginService)
        loginViewModel = ViewModelProvider(this, LoginFactory(repo))[LoginViewModel::class.java]
        loginViewModel.loginEmp.observe(viewLifecycleOwner) {


            when (it) {
                is States.FAILURE -> {
                    showDialogSuccessOrError(
                        getString(R.string.error),
                        it.errorMessage,
                        Constants.ERROR_LOTTIE
                    )

                }
                is States.LOADING -> {


                }
                is States.SUCCESS -> {

                    it.data?.let { it1 ->
                        dismissLoader()
                        message = it1[0].LoginStatus.toString()
                        versionStatus = it1[0].VersionStatus.toString()

                        if (versionStatus.equals("0", ignoreCase = true) || versionStatus.equals(
                                "3",
                                ignoreCase = true
                            )
                        ) {
                            showDialogSuccessOrError(
                                getString(R.string.error),
                                getString(R.string.auth_error_message),
                                Constants.ERROR_LOTTIE
                            )
                        } else if (versionStatus.equals("2", ignoreCase = true)) {
                            showDialogSuccessOrError(
                                getString(R.string.version_update),
                                getString(R.string.new_version_error_msg),
                                Constants.ERROR_LOTTIE
                            )
                        }

                        if (message.equals("1", ignoreCase = true)) {
                            if (appPreferences.getPopupStatus().equals("1")) {
                                showDialogSuccessOrError(
                                    getString(R.string.success),
                                    getString(R.string.auth_message),
                                    Constants.SUCCESS_LOTTIE
                                )
                                appPreferences.saveSinglePopupStatus("0")
                            }

                        } else if (message.equals("2", ignoreCase = true) || message.equals(
                                "3",
                                ignoreCase = true
                            )
                        ) {


                            showDialogSuccessOrError(
                                getString(R.string.error),
                                getString(R.string.unauth_message),
                                Constants.ERROR_LOTTIE
                            )
                        }
                    }

                    if (appPreferences.getBillingFirm() != null) {
                        callTotalDemand()
                    }

                }
            }


        }

        homeViewModel = ViewModelProvider(this, LoginFactory(repo))[HomeViewModel::class.java]
        homeViewModel.totalData.observe(viewLifecycleOwner) {

            when (it) {
                is States.FAILURE -> {
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
                        DemdCategory1 = it1[0].DemdCategory.toString()
                        DemdCategory2 = it1[1].DemdCategory.toString()
                        DemdCategory3 = it1[2].DemdCategory.toString()
                        DemdCategory4 = it1[3].DemdCategory.toString()
                        DemdCategory5 = it1[4].DemdCategory.toString()
                        DemdCategory6 = it1[5].DemdCategory.toString()
                        DemdCategory7 = it1[6].DemdCategory.toString()

                        DemdCategoryParticular1 = it1[0].DemdCategoryParticular
                        DemdCategoryParticular2 = it1[1].DemdCategoryParticular
                        DemdCategoryParticular3 = it1[2].DemdCategoryParticular
                        DemdCategoryParticular4 = it1[3].DemdCategoryParticular
                        DemdCategoryParticular5 = it1[4].DemdCategoryParticular
                        DemdCategoryParticular6 = it1[5].DemdCategoryParticular
                        DemdCategoryParticular7 = it1[6].DemdCategoryParticular

                        TotalDemdAmt1 = it1[0].TotalDemdAmt
                        TotalDemdAmt2 = it1[1].TotalDemdAmt
                        TotalDemdAmt3 = it1[2].TotalDemdAmt
                        TotalDemdAmt4 = it1[3].TotalDemdAmt
                        TotalDemdAmt5 = it1[4].TotalDemdAmt
                        TotalDemdAmt6 = it1[5].TotalDemdAmt
                        TotalDemdAmt7 = it1[6].TotalDemdAmt

                        tenHold = TotalDemdAmt2 + TotalDemdAmt4
                        confirm = TotalDemdAmt3 + TotalDemdAmt5 + TotalDemdAmt6
                        balance = TotalDemdAmt3 + TotalDemdAmt5
                    }
                    if (appPreferences.getBillingFirm() != null) {
                        callDailyDemand()
                    }
                }
            }
            //  dismissLoader()


        }

        homeViewModel.dailyDemandList.observe(viewLifecycleOwner) {
            dismissLoader()

            when (it) {
                is States.FAILURE -> {
                    showDialogSuccessOrError(
                        getString(R.string.error),
                        it.errorMessage,
                        Constants.ERROR_LOTTIE
                    )

                }
                is States.LOADING -> {


                }
                is States.SUCCESS -> {
                    it.data?.let { it1 ->
                        dismissLoader()
                        listDailyDemand.clear()
                        listDailyDemand.addAll(it1)

                        setRvDemand()
                    }

                }
            }


        }
    }

    private fun setRvDemand() {
        AppUtils.mainRVParms(requireContext(), binding.rvDemand)
        adapterDemand = AdapterViewDemands(requireContext(), listDailyDemand)
        binding.rvDemand.adapter = adapterDemand
    }

    private fun callDriverLoginApi() {
        showLoader()

        if (appPreferences.getUserName() != null && appPreferences.getPassword() != null) {
            loginViewModel.callEmpLoin(
                appPreferences.getUserName()!!,
                appPreferences.getPassword()!!,
                AppUtils.callDeviceImei(requireContext()),
                AppUtils.deviceModel(requireContext()),
                "2"
            )
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(eventType: String) {
        when (eventType) {
            Constants.BILLING_FIRM_EVENT -> {
                callTotalDemand()
                //callDailyDemand()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        EventBus.getDefault().unregister(this)
    }

}