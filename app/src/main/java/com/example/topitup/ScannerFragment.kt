package com.example.topitup

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.topitup.databinding.ScannerFragmentBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import org.w3c.dom.Text
import java.nio.ByteBuffer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

typealias LumaListener = (luma: Double) -> Unit
typealias TextListener = () -> Unit

class ScannerFragment : Fragment(R.layout.scanner_fragment) {
    private var _binding: ScannerFragmentBinding? = null
    private val binding get() = _binding!!

    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null

    companion object {
        private const val TAG = "CameraXApp"
    }

    private lateinit var cameraExecutor: ExecutorService

    private lateinit var safeContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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
        binding.btnScan.setOnClickListener { startCamera() }

        /*recognizeTextOnDevice(InputImage.fromBitmap(croppedBitmap, 0)).addOnCompleteListener {
            imageProxy.close()
        }*/
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.scanner_fragment, menu)
    }

    //TODO: Remove this. For testing and understanding
    private class LuminosityAnalyzer(private val listener: LumaListener) : ImageAnalysis.Analyzer {

        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()    // Rewind the buffer to zero
            val data = ByteArray(remaining())
            get(data)   // Copy the buffer into a byte array
            return data // Return the byte array
        }

        override fun analyze(image: ImageProxy) {

            val buffer = image.planes[0].buffer
            val data = buffer.toByteArray()
            val pixels = data.map { it.toInt() and 0xFF }
            val luma = pixels.average()

            listener(luma)

            image.close()
        }
    }

    private class TextAnalyser(/*private val listener: TextListener*/) : ImageAnalysis.Analyzer {

        //val textListener = listener
        private val textRecognizer =
            TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()    // Rewind the buffer to zero
            val data = ByteArray(remaining())
            get(data)   // Copy the buffer into a byte array
            return data // Return the byte array
        }

        override fun analyze(imageProxy: ImageProxy) {

            val mediaImage = imageProxy.image
            if (mediaImage != null) {
                val image =
                    InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                Log.d("CameraX", "Analysing with TextAnalyser....")
                val result = textRecognizer.process(image)
                    .addOnSuccessListener { visionText ->
                        // Task completed successfully
                        // ...
                        Log.e(TAG, visionText.text)

                        //textListener()
                    }
                    .addOnFailureListener { e ->
                        // Task failed with an exception
                        // ...
                        Log.e(TAG, e.localizedMessage)
                    }
                    .addOnCompleteListener {
                        //imageProxy.close()
                        Log.e(TAG, "Closed imageProxy")
                        imageProxy.close()
                    }
                imageProxy.close()
            }
        }
    }

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

            val imageAnalyzer = ImageAnalysis.Builder()
                .setTargetRotation(Surface.ROTATION_0)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                /* .also {
                     it.setAnalyzer(cameraExecutor, LuminosityAnalyzer { luma ->
                         Log.d(TAG, "Average luminosity: $luma")
                     })
                 }*/
                .also {
                    it.setAnalyzer(cameraExecutor, TextAnalyser())
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
/*
    private fun recognizeTextOnDevice(
        image: InputImage
    ): Task<Text> {
        // Pass image to an ML Kit Vision API
        return detector.process(image)
            .addOnSuccessListener { visionText ->
                // Task completed successfully
                result.value = visionText.text
            }
            .addOnFailureListener { exception ->
                // Task failed with an exception
                Log.e(TAG, "Text recognition error", exception)
                val message = getErrorMessage(exception)
                message?.let {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            }
    }*/

}
