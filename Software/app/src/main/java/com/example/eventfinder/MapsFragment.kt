package com.example.eventfinder

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentActivity
import com.example.eventfinder.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class MapsFragment : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var myMap: GoogleMap
    private lateinit var binding: FragmentMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)

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
    }

    override fun onMapReady(googleMap: GoogleMap) {
        myMap = googleMap
    }
}
