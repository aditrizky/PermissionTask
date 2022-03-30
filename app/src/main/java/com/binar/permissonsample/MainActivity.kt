package com.binar.permissonsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var imageButton: Button
    private lateinit var imageView: ImageView
    private lateinit var permissionButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageButton = findViewById(R.id.load_image_button)
        imageView = findViewById(R.id.imageView)
        permissionButton=findViewById(R.id.req_permisson)

        imageButton.setOnClickListener {
            loadImage()
            val snackbar = Snackbar.make(it,"",Snackbar.LENGTH_INDEFINITE)
            snackbar.setAction("dismis"){
                snackbar.dismiss()
            }
            snackbar.show()
        }

        permissionButton.setOnClickListener {
            chekPermission()
        }


    }
    private fun loadImage (){
        Glide.with(this)
            .load("https://img.icons8.com/plasticine/2x/flower.png")
            .circleCrop()
            .into(imageView)

    }

    private fun chekPermission (){
        val permissionCheck = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)

        if(permissionCheck == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"permission location diijinkan",Toast.LENGTH_SHORT).show()
            getLongLat()
        }else{
            Toast.makeText(this,"pemission location ditolak",Toast.LENGTH_LONG).show()
            requestLocattionPermission()
        }
    }

    @SuppressLint("MissingPermission")
    fun getLongLat(){
        val locationManager= applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val location: Location? = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        Toast.makeText(this,"Lat ${location?.latitude}Long: ${location?.longitude}",Toast.LENGTH_LONG).show()
    }

    fun requestLocattionPermission(){
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),201)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            201 -> {
                if (grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"allow telah dipilih",Toast.LENGTH_LONG).show()
                    getLongLat()
                }else{
                    Toast.makeText(this,"Deny telah dipilih",Toast.LENGTH_LONG).show()
                }
            }else ->{
                Toast.makeText(this,"bukan request yang dikirim",Toast.LENGTH_LONG).show()

        }        }

    }



}