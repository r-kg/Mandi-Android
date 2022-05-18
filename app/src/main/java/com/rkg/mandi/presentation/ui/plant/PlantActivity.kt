package com.rkg.mandi.presentation.ui.plant

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.rkg.mandi.R
import com.rkg.mandi.databinding.PlantActivityBinding
import com.rkg.mandi.presentation.ui.BaseActivity
import com.rkg.mandi.presentation.utils.setOnSingleClickListener
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PlantActivity : BaseActivity<PlantActivityBinding>(R.layout.plant_activity) {

    private lateinit var cameraExecutor: ExecutorService
    private var imageCapture: ImageCapture? = null

    private var flashState: FlashState = FlashState.ON
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val denied = permissions.filter { (_, granted) -> !granted }
            if (denied.isEmpty()) {
                startCamera()
            } else {
                denied.firstNotNullOf { (permission, _) ->
                    when (permission) {
                        Manifest.permission.CAMERA -> {
                            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                                // show camera denied warning
                            } else {
                                // show Camera never ask warning
                            }
                        }
                        Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
                            // show External storage deny warning
                        }
                        else -> {
                        }
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestCameraPermission()
        cameraExecutor = Executors.newSingleThreadExecutor()
        initViews()
    }

    private fun initViews() {
        binding.apply {
            btnClose.setOnSingleClickListener { finish() }
            btnCapture.setOnSingleClickListener { capturePhoto() }
            btnFlip.setOnSingleClickListener { flipCamera() }
            btnFlash.setOnSingleClickListener { changeFlashMode() }
        }
    }

    private fun changeFlashMode() {
        flashState = when (flashState) {
            FlashState.OFF -> FlashState.ON
            FlashState.ON -> FlashState.AUTO
            FlashState.AUTO -> FlashState.OFF
        }

        binding.btnFlash.setImageDrawable(getDrawable(flashState.icon))
        bindImageCapture()
    }

    private fun flipCamera() {
        cameraSelector =
            if (cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA) CameraSelector.DEFAULT_BACK_CAMERA else CameraSelector.DEFAULT_FRONT_CAMERA
        startCamera()
    }

    private fun bindImageCapture() {
        imageCapture = ImageCapture.Builder()
            .setFlashMode(flashState.flashMode)
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()
    }


    private fun requestCameraPermission() {
        val cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val writeStoragePermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (cameraPermission == PackageManager.PERMISSION_GRANTED &&
            writeStoragePermission == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            cameraPermissionLauncher.launch(
                mutableListOf(Manifest.permission.CAMERA).apply {
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                        add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }
                }.toTypedArray()
            )
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.previewView.surfaceProvider)
                }

            bindImageCapture()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {

            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun capturePhoto() {
        val imageCapture = imageCapture ?: return

        val name = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Mandi")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                }
            }
        )
    }

    enum class FlashState(
        val flashMode: Int,
        @DrawableRes val icon: Int
    ) {
        ON(ImageCapture.FLASH_MODE_ON, R.drawable.ic_flash_on),
        OFF(ImageCapture.FLASH_MODE_OFF, R.drawable.ic_flash_off),
        AUTO(ImageCapture.FLASH_MODE_AUTO, R.drawable.ic_flash_auto);
    }
}