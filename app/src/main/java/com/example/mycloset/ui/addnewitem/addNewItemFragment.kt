package com.example.mycloset.ui.addnewitem

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.mycloset.R
import com.example.mycloset.database.Items
import java.io.File
import java.util.*
import com.squareup.picasso.Picasso
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_addnewitem.*


class addNewItemFragment : Fragment(), OnItemSelectedListener {

    companion object {
        fun newInstance(listener: AddItemListener) = addNewItemFragment().apply {
            this.listener=listener
        }
        private const val REQUEST_PHOTO = 0
    }

    var selected = "?"
    private var listener: AddItemListener? = null
    private var newItem: Items = Items()
    private var uuid = UUID.randomUUID()
    private lateinit var photoFile: File
    private lateinit var photoUri: Uri
    private val picasso = Picasso.get()
    private val vm: addNewItemViewModel by lazy {
        ViewModelProvider(this).get(addNewItemViewModel::class.java)
    }

    interface AddItemListener {
        fun onAddItem(items: Items)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_addnewitem, container, false)
        return inflater.inflate(R.layout.fragment_addnewitem, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        navView.visibility = View.GONE
        photoFile = File(context?.applicationContext?.filesDir, "IMG_$uuid.jpg")
        photoUri = FileProvider.getUriForFile(
            requireActivity(),
            "com.example.mycloset.fileprovider",
            photoFile
        )
        new_category.onItemSelectedListener=this
        add_btn.setOnClickListener {
            onDone()
        }
        launchCam.apply {
            val pm = requireActivity().packageManager
            val captureImage = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val resolveActivity =
                pm.resolveActivity(captureImage, PackageManager.MATCH_DEFAULT_ONLY)
            if (resolveActivity == null || !cameraPermission()) {
                isEnabled = false
            }
            launchCam.setOnClickListener {
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                val cameraActivities =
                    pm.queryIntentActivities(captureImage, PackageManager.MATCH_DEFAULT_ONLY)
                for (cameraActivity in cameraActivities) {
                    requireActivity().grantUriPermission(
                        cameraActivity.activityInfo.packageName,
                        photoUri,
                        Intent.FLAG_GRANT_PREFIX_URI_PERMISSION
                    )
                    startActivityForResult(captureImage, REQUEST_PHOTO)
                }
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent) {
            new_category -> selected = parent?.getItemAtPosition(position).toString()
        }
    }

    private fun onDone() {
        val itemName = new_itemname.text.toString()
        newItem.name = itemName
        newItem.category = selected
        newItem.price = newitem_price.getText().toString().toDouble()
        newItem.uuid = uuid
        vm.insert(newItem)
        findNavController().navigateUp()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun cameraPermission(): Boolean {
        val permission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        )
        return permission == PackageManager.PERMISSION_GRANTED
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when {
            resultCode != Activity.RESULT_OK -> return
            requestCode == REQUEST_PHOTO -> {
                requireActivity().revokeUriPermission(
                    photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
                if (photoFile.exists()) {
                    picasso.load(photoUri)
                        .fit()
                        .centerCrop()
                        .into(launchCam)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item:MenuItem):Boolean {
        return when(item.itemId){
            android.R.id.home ->{
                Toast.makeText(context, "ggg", 1).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}