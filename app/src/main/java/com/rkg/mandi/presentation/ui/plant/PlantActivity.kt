package com.rkg.mandi.presentation.ui.plant

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.rkg.mandi.R
import com.rkg.mandi.databinding.PlantActivityBinding
import com.rkg.mandi.presentation.ui.BaseActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PlantActivity : BaseActivity<PlantActivityBinding>(R.layout.plant_activity) {

    private lateinit var cameraExecutor: ExecutorService

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

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview)
            } catch (e: Exception) {

            }

        }, ContextCompat.getMainExecutor(this))
    }
}