package com.example.eventfinder

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentActivity
import com.example.eventfinder.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import java.io.IOException

class MapsFragment : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var myMap: GoogleMap
    private lateinit var binding: FragmentMapsBinding
    private val polygonPoints = mutableListOf<LatLng>()
    private var isDrawing = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)

        val btnPolygon = findViewById<Button>(R.id.btn_polygon)


        binding.mapSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { location ->
                    if (location.isNotEmpty()) {
                        val geocoder = Geocoder(this@MapsFragment)
                        try {
                            val addressList: List<Address>? = geocoder.getFromLocationName(location, 1)

                            if (addressList != null && addressList.isNotEmpty()) {
                                val address: Address = addressList[0]
                                val latLng: LatLng = LatLng(address.latitude, address.longitude)

                                myMap.addMarker(
                                    MarkerOptions()
                                        .position(latLng)
                                        .title(location)
                                )
                                myMap.animateCamera(
                                    CameraUpdateFactory.newLatLngZoom(latLng, 10F)
                                )
                            } else {
                                // Handle the case where the addressList is empty or null
                            }
                        } catch (e: IOException) {
                            e.printStackTrace()
                            // Handle the exception, e.g., log or display an error message
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle changes to the query text
                return true
            }
        })

        btnPolygon.setOnClickListener {
            startPolygonDrawing()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        myMap = googleMap

        myMap.setOnMapClickListener { point ->
            if (isDrawing) {
                addPolygonPoint(point)
            }
        }
    }

    private fun startPolygonDrawing() {
        isDrawing = true
        polygonPoints.clear()

        // Clear existing polygons
        myMap.clear()
    }

    private fun addPolygonPoint(point: LatLng) {
        polygonPoints.add(point)
        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.outline_flag)

        myMap.addMarker(
            MarkerOptions()
                .position(point)
                .title("tocka")
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
        )
        // Draw the polygon on the map
        drawPolygon()

        // You can perform additional actions here, such as updating UI or storing points
    }

    private fun drawPolygon() {
        // Clear existing polygons
        myMap.clear()

        // Draw the polygon with existing points
        if (polygonPoints.size >= 3) {
            val polygonOptions = PolygonOptions()
                .addAll(polygonPoints)
                .strokeColor(Color.BLACK)
                .fillColor(Color.WHITE)

            myMap.addPolygon(polygonOptions)
        }
    }
}
