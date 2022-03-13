package com.example.topitup

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.topitup.databinding.ScannerFragmentBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
//import java.time.Year
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class ScannerFragment : Fragment(R.layout.scanner_fragment) {
    private var _binding: ScannerFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "CameraXScan"
    }

    private lateinit var cameraExecutor: ExecutorService

    private lateinit var safeContext: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ScannerFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        setHasOptionsMenu(true)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraExecutor = Executors.newSingleThreadExecutor()
        //binding.btnScan.setOnClickListener { startCamera() }
        //binding.btnSave.setOnClickListener { saveToHistory() }
        startCamera()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.scanner_fragment, menu)
    }

    private class TextAnalyser(private val ocrTextView: TextView,
                               private val cardNumberTextView: TextView,
                               private val cardNameTextView: TextView,
                               private val expiryTextView: TextView,
                               ) : ImageAnalysis.Analyzer {

        private val textRecognizer =
            TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        @androidx.camera.core.ExperimentalGetImage
        override fun analyze(imageProxy: ImageProxy) {

            val mediaImage = imageProxy.image
            if (mediaImage != null) {
                val image =
                    InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                Log.d("CameraX", "Analysing with TextAnalyser....")
                val result = textRecognizer.process(image)
                    .addOnSuccessListener { visionText ->
                        // Task completed successfully
                        Log.e(TAG, visionText.text)
                        ocrTextView.text = visionText.text
                        val words = visionText.text.split("\n")
                        for (word in words) {
                            Log.e(TAG, "Recognised words: $word")
                            //REGEX for detecting a credit card number
                            if (word.replace(" ", "")
                                    .matches(Regex("^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})\$"))) {
                                Log.e(TAG, "Card Number: $word")
                                cardNumberTextView.text = word
                            }
                            //find the expiry
                            if (word.contains("/")) {
                                Log.e(TAG, "Expiry: $word")
                                expiryTextView.text = word
                            }
                            if (word.matches(Regex("^[A-Za-z]{2,} [A-Za-z]{2,}"))) {
                                Log.d(TAG, "Card Holder: $word")
                                cardNameTextView.text = word
                            }
                        }
                        imageProxy.close()
                    }
                    .addOnFailureListener { e ->
                        // Task failed with an exception
                        // ...
                        Log.e(TAG, e.localizedMessage!!)
                    }
                    .addOnCompleteListener {
                        //imageProxy.close()
                        Log.e(TAG, "Closed imageProxy")
                        imageProxy.close()
                    }

            }
            imageProxy.close()
        }

    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity())
        val ocrTextView = binding.ocrTextView
        val cardNumberTextView = binding.cardNumTextView
        val cardNameTextView = binding.cardNameTextView
        val expiryTextView = binding.expiryTextView

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            val imageAnalyzer = ImageAnalysis.Builder()
                .setTargetRotation(Surface.ROTATION_0)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, TextAnalyser(ocrTextView,
                        cardNumberTextView,
                        cardNameTextView,
                        expiryTextView)
                    )
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageAnalyzer
                )

            } catch (exc: Exception) {
                Log.e("TAG", "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireActivity()))
    }



}
