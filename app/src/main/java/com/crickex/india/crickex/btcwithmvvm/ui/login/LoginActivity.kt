package com.crickex.india.crickex.btcwithmvvm.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.crickex.india.crickex.btcwithmvvm.R
import com.crickex.india.crickex.btcwithmvvm.databinding.ActivityLoginBinding
import com.crickex.india.crickex.btcwithmvvm.ui.login.api.RetrofitHelper
import com.crickex.india.crickex.btcwithmvvm.ui.login.api.ServerServices
import com.crickex.india.crickex.btcwithmvvm.ui.login.base.BaseActivity
import com.crickex.india.crickex.btcwithmvvm.ui.login.repo.CompanyLoginRepo
import com.crickex.india.crickex.btcwithmvvm.ui.login.room.MyApplication
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.AppPreferences
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.AppUtils
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.Constants
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.States
import com.crickex.india.crickex.btcwithmvvm.ui.login.viewModel.LoginViewModel
import com.crickex.india.crickex.btcwithmvvm.ui.login.view_modelFactory.LoginFactory
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit


class LoginActivity : BaseActivity() {


    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    private lateinit var appPreferences: AppPreferences
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        appPreferences = AppPreferences(this)

        if (appPreferences.isLoggedIn()) {
            val i = Intent(this, HomeScreen::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(i)
            finish()
        }

        auth = FirebaseAuth.getInstance()

        callClickListner()
        subscribeViewModel()

    }

    private fun subscribeViewModel() {
        val loginService = RetrofitHelper.getInstance().create(ServerServices::class.java)
        val repo = CompanyLoginRepo(loginService)
        loginViewModel = ViewModelProvider(this, LoginFactory(repo))[LoginViewModel::class.java]
        loginViewModel.loginEmp.observe(this) {
            binding.buttonLoader.cancelAnimation()
            binding.buttonLoader.visibility = View.GONE
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
                        when (it1[0].LoginStatus) {
                            1 -> {
                                appPreferences.saveEmpCode(it1[0].EmpCode.toString())
                                appPreferences.saveSinglePopupStatus("1")
                                startActivity(Intent(this@LoginActivity, HomeScreen::class.java))
                                finishAffinity()

                                //create login session....
                                appPreferences.createLoginSession(
                                    binding.userName.text.toString(),
                                    binding.password.text.toString()
                                )

                            }
                            2 -> {
                                showDialogSuccessOrError(
                                    getString(R.string.error),
                                    getString(R.string.wrong_user_pass),
                                    Constants.ERROR_LOTTIE
                                )
                                binding.login.visibility = View.VISIBLE
                            }
                            3 -> {
                                showDialogSuccessOrError(
                                    getString(R.string.error),
                                    getString(R.string.unauth_message),
                                    Constants.ERROR_LOTTIE
                                )
                                binding.login.visibility = View.VISIBLE
                            }
                        }
                    }

                }
            }

        }

        loginViewModel.login.observe(this) {
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
                        if (it1[0].MsgOutPut == 1) {
                            binding.companyLoginLayout.visibility = View.GONE
                            binding.employeeLoginLayout.visibility = View.VISIBLE
                        } else {
                            showDialogSuccessOrError(
                                getString(R.string.error),
                                getString(R.string.unauth_message),
                                Constants.ERROR_LOTTIE
                            )
                        }

                    }


                }
            }


        }
    }


    private fun callClickListner() {
        binding.login.setOnClickListener {

            if (binding.companyLoginLayout.isVisible) {
                if (binding.companyUserName.text.toString().isEmpty()) {
                    AppUtils.errorShakeEditBox(
                        this,
                        binding.companyUserName,
                        "Please enter your user name"
                    )
                    return@setOnClickListener
                }
                showLoader()
                AppUtils.noErrorFoundForEditBox(binding.companyUserName)
                loginViewModel.callCompanyLogin(binding.companyUserName.text.toString(), "1")

            } else {


                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber("+91" + binding.userName.text.toString())       // Phone number to verify
                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                    .setActivity(this)                 // Activity (for callback binding)
                    .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)


                /*if (binding.userName.text.toString()
                        .isEmpty() || !AppUtils.checkUserName(binding.userName.text.toString())
                ) {
                    AppUtils.errorShakeEditBox(this, binding.userName, "Enter correct user name")
                    return@setOnClickListener
                }
                if (binding.password.text.toString().isEmpty()) {
                    AppUtils.errorShakeEditBox(this, binding.password, "Enter correct password")
                    return@setOnClickListener
                }


                AppUtils.noErrorFoundForEditBox(binding.userName)
                AppUtils.noErrorFoundForEditBox(binding.password)
                binding.buttonLoader.visibility = View.VISIBLE
                binding.login.visibility = View.GONE
                binding.buttonLoader.playAnimation()
                loginViewModel.callEmpLoin(
                    binding.userName.text.toString(),
                    binding.password.text.toString(),
                    AppUtils.callDeviceImei(this),
                    AppUtils.deviceModel(this),
                    "1"
                )
                appPreferences.saveUserName(binding.userName.text.toString())
                appPreferences.savePassword(binding.password.text.toString())*/
            }
        }

    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.


            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Log.d("ERROR",e.message.toString())
            } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                // reCAPTCHA verification attempted with null Activity
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {

            val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                verificationId, binding.password.text.toString()
            )
            signInWithPhoneAuthCredential(credential)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()

                } else {

                }
            }
    }


}