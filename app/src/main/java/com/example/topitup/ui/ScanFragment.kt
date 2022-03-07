package com.example.topitup.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.topitup.R
import com.example.topitup.databinding.FragmentScanBinding


class ScanFragment : Fragment(R.layout.fragment_scan) {
    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        val view = binding.root

        setHasOptionsMenu(true)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        /* val viewFinder = Preview.Builder()
            .setTargetRotation(Surface.ROTATION_0)
            .build()
            .also { it.setSurfaceProvider(binding.previewView.surfaceProvider) }

        viewFinder. captureImage { cameraKitImage ->
            // Get the Bitmap from the captured shot and use it to make the API call
            getCardDetails(cameraKitImage.bitmap)


        }

        fun getCardDetails(bitmap: Bitmap) {
            val image = FirebaseVisionImage.fromBitmap(bitmap)
            val firebaseVisionTextDetector = FirebaseVision.getInstance().cloudTextRecognizer

            firebaseVisionTextDetector.processImage(image)
                .addOnSuccessListener {
                    val words = it.text.split("\n")
                    for (word in words) {
                        Log.e("Camera word", word)
                        //REGEX for detecting a credit card
                        if (word.replace(" ", "").matches(Regex("^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})\$")))
                            Log.e("Camera word", word)
                        //Find a better way to do this
                        if (word.contains("/")) {
                            for (year in word.split(" ")) {
                                if (year.contains("/"))
                                    Log.e("Camera year", year)
                            }
                        }
                    }
                }
                .addOnFailureListener {
                    Log.d("Camera", "Sorry, something went wrong!")
                }
        }

*/
    }

    override fun onStart() {
        super.onStart()
        startCamera()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.calculator_fragment, menu)
    }*/

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity())

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview
                )

            } catch (exc: Exception) {
                Log.e("TAG", "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireActivity()))
    }
}

