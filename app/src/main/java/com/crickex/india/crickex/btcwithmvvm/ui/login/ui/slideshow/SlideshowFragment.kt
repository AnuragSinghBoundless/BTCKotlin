package com.crickex.india.crickex.btcwithmvvm.ui.login.ui.slideshow

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.crickex.india.crickex.btcwithmvvm.R
import com.crickex.india.crickex.btcwithmvvm.databinding.FragmentSlideshowBinding
import com.crickex.india.crickex.btcwithmvvm.ui.login.base.BaseActivity
import com.crickex.india.crickex.btcwithmvvm.ui.login.base.BaseFragment
import com.crickex.india.crickex.btcwithmvvm.ui.login.utils.AppUtils

class SlideshowFragment : BaseFragment() {

    private var _binding: FragmentSlideshowBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var imageUri: Uri

    private lateinit var requestCameraPermissionLauncher: ActivityResultLauncher<String>

    private val contract = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        binding.circleImageView.setImageURI(null)
        binding.circleImageView.setImageURI(imageUri)
    }

    companion object {
        var TAG: String = SlideshowFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {


        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerCameraPermission()
        takeClicks()
    }

    fun CallCameraPermission() {
        if (!Status_checkCameraPermission()) {
            requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        } else {
            imageUri = AppUtils.createImageUri(requireContext())!!
            contract.launch(imageUri)
        }
    }

    private fun Status_checkCameraPermission(): Boolean {
        val camera = ActivityCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.CAMERA
        )

        return camera == PackageManager.PERMISSION_GRANTED
    }

    private fun registerCameraPermission() {
        requestCameraPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
                if (granted) {
                    Log.d(TAG, "registerCameraPermission - Camera Permission Granted")
                    //    openCamera()
                } else {
                    Log.d(TAG, "registerCameraPermission - Camera Permission NOT Granted")
                    requestCameraPermission()
                }
            }
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {

                Log.d(TAG, "requestCameraPermission - Camera Permission Granted")
                // openCamera()

                // The permission is granted
                // you can go with the flow that requires permission here
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                // This case means user previously denied the permission
                // So here we can display an explanation to the user
                // That why exactly we need this permission
                Log.d(TAG, "requestCameraPermission - Camera Permission NOT Granted")
                showPermissionAlert(
                    getString(R.string.camera_permission),
                    getString(R.string.camera_permission_denied),
                    getString(R.string.ok_caps),
                    getString(R.string.cancel_caps)
                ) { requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA) }
            }
            else -> {
                // Everything is fine you can simply request the permission

                showPermissionAlert(
                    getString(R.string.camera_permission),
                    getString(R.string.camera_permission_denied),
                    getString(R.string.settings_caps),
                    getString(R.string.cancel_caps)
                ) {
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    val uri = Uri.fromParts(
                        "package",
                        "com.crickex.india.crickex.btcwithmvvm", null
                    )
                    intent.data = uri
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }

            }
        }
    }

    private fun showPermissionAlert(
        title: String,
        message: String,
        ok: String,
        cancel: String,
        function: () -> Unit,
    ) {
        val mDialog = requireActivity().let { Dialog(it) }
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog.setContentView(R.layout.dialog_permission_alert)
        mDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val mTitleTv = mDialog.findViewById<View>(R.id.id_title_tv) as AppCompatTextView
        mTitleTv.text = title

        val mMessageTv = mDialog.findViewById<View>(R.id.id_message_tv) as AppCompatTextView
        mMessageTv.text = message

        val mNoBtn = mDialog.findViewById<View>(R.id.no_btn) as AppCompatTextView
        mNoBtn.text = cancel

        val mYesBtn = mDialog.findViewById<View>(R.id.yes_btn) as AppCompatTextView
        mYesBtn.text = ok

        mYesBtn.setOnClickListener {
            function.invoke()
            mDialog.dismiss()
        }

        mNoBtn.setOnClickListener { mDialog.dismiss() }

        mDialog.setCancelable(true)
        mDialog.show()
        val metrics = resources.displayMetrics
        val width = metrics.widthPixels
        val height = metrics.heightPixels
        mDialog.window!!.setLayout(
            width,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

    }

    private fun takeClicks() {
        binding.cameraSelect.setOnClickListener(clickListner)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private var clickListner = View.OnClickListener {
        showImagePickerDialog(
            getString(R.string.choose_image),
            requireContext(),
            object : BaseActivity.DialogImageInterface {
                override fun onClickGallery() {
                    openGallery()
                }

                override fun onClickCamera() {
                    imageUri = Uri.parse("")
                    CallCameraPermission()

                }
            })
    }

    private fun openGallery() {
        getImageResultGallary.launch("image/*")
    }

    private val getImageResultGallary = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        binding.circleImageView.setImageURI(it)
    }


}


