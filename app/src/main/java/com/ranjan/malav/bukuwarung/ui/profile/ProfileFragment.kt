package com.ranjan.malav.bukuwarung.ui.profile

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.ranjan.malav.bukuwarung.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var currentPhotoPath: String
    private lateinit var profilePic: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        val firstName = root.findViewById<TextInputLayout>(R.id.first_name)
        val lastName = root.findViewById<TextInputLayout>(R.id.last_name)
        val email = root.findViewById<TextInputLayout>(R.id.email)
        profilePic = root.findViewById(R.id.profile_image)
        val saveButton = root.findViewById<FloatingActionButton>(R.id.profile_save)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val sharedPrefEditor = sharedPref?.edit()

        sharedPref?.also {
            firstName.editText?.setText(it.getString("user_first_name", ""))
            lastName.editText?.setText(it.getString("user_last_name", ""))
            email.editText?.setText(it.getString("user_email", ""))
            val picUri = it.getString("user_pic_uri", "")
            if (!picUri.isNullOrEmpty()) {
                currentPhotoPath = picUri
                profilePic.setImageURI(Uri.parse(picUri))
            }
        }

        saveButton.setOnClickListener {
            sharedPrefEditor?.also {
                it.putString("user_first_name", firstName.editText?.text.toString())
                it.putString("user_last_name", lastName.editText?.text.toString())
                it.putString("user_email", email.editText?.text.toString())
                it.putString("user_pic_uri", currentPhotoPath)
            }
            sharedPrefEditor?.apply()
            Toast.makeText(context, "Successfully saved", Toast.LENGTH_SHORT).show()
        }

        profilePic.setOnClickListener {
            // For now just launching the gallery to choose and image
            // Can give option to take picture from the camera as well
            dispatchTakePictureIntent()
        }

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == Activity.RESULT_OK) {
                profilePic.setImageURI(Uri.parse(currentPhotoPath))
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = activity?.getExternalFilesDir("images/")
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun dispatchTakePictureIntent() {
        try {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                // Ensure that there's a camera activity to handle the intent
                takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        null
                    }
                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            requireContext(),
                            "com.ranjan.malav.bukuwarung.fileprovider",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    }
                }
            }
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }
}