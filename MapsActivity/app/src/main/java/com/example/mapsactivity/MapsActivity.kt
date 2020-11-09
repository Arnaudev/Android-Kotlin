package com.example.mapsactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    override fun onMarkerClick(marker: Marker?): Boolean {
     Toast.makeText(this,marker!!.title,Toast.LENGTH_LONG).show()
        return true
    }

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Helsinki
        val helsinki = LatLng(60.1756, 24.9342)
        mMap.addMarker(MarkerOptions().position(helsinki).title("This is Helsinki the capital of Finland"))

        // Add a marker in Paris
        val paris = LatLng(48.8534, 2.3488)
        mMap.addMarker(MarkerOptions().position(paris).title("Capital of France"))

        // Add a marker in Aland
        val aland = LatLng(60.1978055, 20.164778000000013)
        mMap.addMarker(MarkerOptions().position(aland).title("Aland islands"))

        // Add a marker in Strasbourg
        val stras = LatLng(48.5833, 7.75)
        mMap.addMarker(MarkerOptions().position(stras).title("Strasbourg popular for its christmas market"))

        // Add a marker in Champs
        val champs = LatLng(48.85, 2.6)
        mMap.addMarker(MarkerOptions().position(champs).title("home"))


        // Add a marker in dynamo and move the camera
        val dynamo = LatLng(62.2416223, 25.7597309)
        mMap.addMarker(MarkerOptions().position(dynamo).title("Dynamo at piippukatu"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dynamo, 3F))
        // Zoom
        mMap.uiSettings.isZoomControlsEnabled = true
        // click listener
        mMap.setOnMarkerClickListener(this)

        
    }
}