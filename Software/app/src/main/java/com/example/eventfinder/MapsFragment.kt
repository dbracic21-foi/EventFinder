package com.example.eventfinder

import android.content.Context
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
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
import com.google.android.gms.maps.model.LatLngBounds

class MapsFragment : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var myMap: GoogleMap
    private lateinit var binding: FragmentMapsBinding
    private var polygonPoints = mutableListOf<LatLng>()
    private var polyline: Polyline? = null
    private var polygon: Polygon? = null
    private var cornerMarkers = mutableListOf<Marker>()
    private var isDrawing = false

    private val TAG = "MapsFragment"

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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        myMap = googleMap

        myMap.setOnMapClickListener { point ->
            if (isDrawing) {
                addPolygonPoint(point)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Handle back button press
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startPolygonDrawing() {
        isDrawing = true
        polygonPoints.clear()

        // Clear existing polygon, polyline, and corner markers
        polygon?.remove()
        polyline?.remove()
        clearCornerMarkers()
    }

    private fun getCityNameFromLatLng(latLng: LatLng): String? {
        val geocoder = Geocoder(this@MapsFragment)
        try {
            val addressList: List<Address>? =
                geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

            if (addressList != null && addressList.isNotEmpty()) {
                return addressList[0].locality // You can customize this to retrieve other address components if needed
            }
        } catch (e: IOException) {
            e.printStackTrace()
            // Handle the exception, e.g., log or display an error message
        }
        return null
    }

    private fun isPointInPolygon(point: LatLng): Boolean {
        if (polygonPoints.size < 3) {
            // A polygon must have at least 3 vertices
            return false
        }

        var inside = false
        val x = point.longitude
        val y = point.latitude

        var j = polygonPoints.size - 1
        for (i in polygonPoints.indices) {
            val xi = polygonPoints[i].longitude
            val yi = polygonPoints[i].latitude
            val xj = polygonPoints[j].longitude
            val yj = polygonPoints[j].latitude

            val intersect =
                (yi > y) != (yj > y) && (x < (xj - xi) * (y - yi) / (yj - yi) + xi)
            if (intersect) {
                inside = !inside
            }

            j = i
        }

        return inside
    }

    private fun getAllPointsWithinPolygon(): List<LatLng> {
        val bounds = getPolygonBounds() ?: return emptyList()

        val pointsWithinBounds = mutableListOf<LatLng>()

        for (lat in (bounds.southwest.latitude * 10).toInt()..(bounds.northeast.latitude * 10).toInt() step 1) {
            for (lng in (bounds.southwest.longitude * 10).toInt()..(bounds.northeast.longitude * 10).toInt() step 1) {
                val point = LatLng(lat / 10.0, lng / 10.0)
                if (isPointInPolygon(point)) {
                    pointsWithinBounds.add(point)
                }
            }
        }

        return pointsWithinBounds
    }

    private fun getPolygonBounds(): LatLngBounds? {
        if (polygonPoints.size < 3) {
            // A polygon must have at least 3 vertices
            return null
        }

        var minLat = Double.MAX_VALUE
        var maxLat = Double.MIN_VALUE
        var minLng = Double.MAX_VALUE
        var maxLng = Double.MIN_VALUE

        for (point in polygonPoints) {
            minLat = minOf(minLat, point.latitude)
            maxLat = maxOf(maxLat, point.latitude)
            minLng = minOf(minLng, point.longitude)
            maxLng = maxOf(maxLng, point.longitude)
        }

        return LatLngBounds(LatLng(minLat, minLng), LatLng(maxLat, maxLng))
    }

    private fun endPolygonDrawing() {
        isDrawing = false

        // Draw the final polygon
        drawPolygon()

        // Clear existing polygons, polylines, and corner markers
        polyline?.remove()
        clearCornerMarkers()

        // Retrieve city names within the polygon
        val selectedCityNames = mutableListOf<String>()
        for (point in getAllPointsWithinPolygon()) {
            val cityName = getCityNameFromLatLng(point)
            if (cityName != null) {
                // Do something with the cityName, e.g., add it to a list
                selectedCityNames.add(cityName)
                Log.d(TAG, "Selected City: $cityName")
            }
        }

        // Save the list of selected city names
        saveCityNames(selectedCityNames)
    }

    private fun saveCityNames(cityNames: List<String>) {
        val sharedPreferences =
            getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Convert the list of city names to a comma-separated string and save it
        val cityNamesString = cityNames.joinToString(",")
        editor.putString("cityNames", cityNamesString)
        editor.apply()
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
                .fillColor(Color.argb(128, 28, 200, 255))

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
