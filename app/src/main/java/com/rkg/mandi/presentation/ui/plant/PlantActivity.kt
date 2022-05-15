package com.rkg.mandi.presentation.ui.plant

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.rkg.mandi.R
import com.rkg.mandi.databinding.PlantActivityBinding
import com.rkg.mandi.presentation.ui.BaseActivity

class PlantActivity : BaseActivity<PlantActivityBinding>(R.layout.plant_activity) {

    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val denied = permissions.filter { (_, granted) -> !granted }
            if (denied.isEmpty()) {
                // start Camera
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
    }

    private fun requestCameraPermission() {
        val cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val writeStoragePermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (cameraPermission == PackageManager.PERMISSION_GRANTED &&
            writeStoragePermission == PackageManager.PERMISSION_GRANTED
        ) {
            //start Camera
        } else {
            cameraPermissionLauncher.launch(
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            )
        }
    }
}