package com.example.eventfinder

import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.eventfinder.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polygon
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import java.io.IOException

class MapsFragment : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var myMap: GoogleMap
    private lateinit var binding: FragmentMapsBinding
    private var polygonPoints = mutableListOf<LatLng>()
    private var polyline: Polyline? = null
    private var polygon: Polygon? = null
    private var cornerMarkers = mutableListOf<Marker>()
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

                            if (addressList != null) {
                                val address: Address = addressList[0]
                                val latLng = LatLng(address.latitude, address.longitude)

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
            if (isDrawing) {
                // End polygon drawing
                endPolygonDrawing()
                btnPolygon.setBackgroundResource(R.drawable.rounded_btn)
            } else {
                // Start polygon drawing
                startPolygonDrawing()
                btnPolygon.setBackgroundResource(R.drawable.baseline_stop_circle_24)
            }
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

        // Clear existing polygon, polyline, and corner markers
        polygon?.remove()
        polyline?.remove()
        clearCornerMarkers()
    }

    private fun endPolygonDrawing() {
        isDrawing = false

        // Draw the final polygon
        drawPolygon()

        // Clear existing polygons, polylines, and corner markers
        polyline?.remove()
        clearCornerMarkers()
    }

    private fun addPolygonPoint(point: LatLng) {
        polygonPoints.add(point)

        // Add marker at the current point (unclickable)
        val marker = myMap.addMarker(
            MarkerOptions()
                .position(point)
                .title("Corner")
        )
        if (marker != null) {
            marker.tag = "corner"
        } // Add a tag to identify the marker type

        if (marker != null) {
            cornerMarkers.add(marker)
        }

        // Set click listener that does nothing
        myMap.setOnMarkerClickListener { true }

        // Draw polyline with existing points
        drawPolyline()

        // Draw the polygon with existing points
        drawPolygon()

        // You can perform additional actions here, such as updating UI or storing points
    }

    private fun drawPolyline() {
        // Draw the polyline with existing points
        if (polygonPoints.size >= 2) {
            polyline?.remove()

            val polylineOptions = PolylineOptions()
                .addAll(polygonPoints)
                .color(Color.BLACK)
                .width(5f)

            polyline = myMap.addPolyline(polylineOptions)
        }
    }

    private fun drawPolygon() {
        // Draw the polygon with existing points
        if (polygonPoints.size >= 3) {
            polygon?.remove()
            clearCornerMarkers()

            val polygonOptions = PolygonOptions()
                .addAll(polygonPoints)
                .strokeColor(Color.BLACK)
                .fillColor(Color.argb(128,28,200,255))

            polygon = myMap.addPolygon(polygonOptions)

            // Add markers at each corner (unclickable)
            for (point in polygonPoints) {
                val cornerMarker = myMap.addMarker(
                    MarkerOptions()
                        .position(point)
                        .title("Corner")
                )
                if (cornerMarker != null) {
                    cornerMarker.tag = "corner"
                } // Add a tag to identify the marker type
                if (cornerMarker != null) {
                    cornerMarkers.add(cornerMarker)
                }
            }
        }
    }

    private fun clearCornerMarkers() {
        for (marker in cornerMarkers) {
            marker.remove()
        }
        cornerMarkers.clear()
    }
}
