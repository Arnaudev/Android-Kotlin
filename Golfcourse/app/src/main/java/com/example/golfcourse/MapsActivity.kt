package com.example.golfcourse


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import kotlinx.android.synthetic.main.info_window.view.*
import org.json.JSONArray


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val TAG = "mapsActivity"

    // Declare a variable for the cluster manager.
    private lateinit var clusterManager: ClusterManager<MyClusterItem>

    private fun setUpClusterer() {
        // Position the map.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(51.503186, -0.126446), 10f))

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        clusterManager = ClusterManager(this, mMap)


        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraIdleListener(clusterManager)
        mMap.setOnMarkerClickListener(clusterManager)

        // Add cluster items (markers) to the cluster manager.
        addItems()
    }


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
        loadData()

        setUpClusterer()
    }

   private fun addItems() {

        // Set some lat/lng coordinates to start with.
        var lat = 51.5145160
        var lng = -0.1270060

        // Add ten cluster items in close proximity, for purposes of this example.
        for (i in 0..30) {
            val offset = i / 60.0
            lat += offset
            lng += offset
            val offsetItem =
                MyClusterItem(lat, lng, "Title $i", "Snippet $i")
            clusterManager.addItem(offsetItem)
        }
    }


    private fun loadData() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://ptm.fi/materials/golfcourses/golf_courses.json"

        // create JSON request object
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                // JSON loaded successfully
                var golf_courses: JSONArray
                var course_types: Map<String, Float> = mapOf(
                    "?" to BitmapDescriptorFactory.HUE_VIOLET,
                    "Etu" to BitmapDescriptorFactory.HUE_BLUE,
                    "Kulta" to BitmapDescriptorFactory.HUE_GREEN,
                    "Kulta/Etu" to BitmapDescriptorFactory.HUE_YELLOW
                )
                golf_courses = response.getJSONArray("courses")
// loop through all objects
                for (i in 0 until golf_courses.length()) {
                    // get course data
                    val course = golf_courses.getJSONObject(i)
                    val lat = course["lat"].toString().toDouble()
                    val lng = course["lng"].toString().toDouble()
                    val coord = LatLng(lat, lng)
                    val type = course["type"].toString()
                    val title = course["course"].toString()
                    val address = course["address"].toString()
                    val phone = course["phone"].toString()
                    val email = course["email"].toString()
                    val web_url = course["web"].toString()

                    if (course_types.containsKey(type)) {
                        var m = mMap.addMarker(
                            MarkerOptions()
                                .position(coord)
                                .title(title)
                                .icon(
                                    BitmapDescriptorFactory
                                        .defaultMarker(
                                            course_types.getOrDefault(
                                                type,
                                                BitmapDescriptorFactory.HUE_RED
                                            )
                                        )
                                )
                        )
                        // pass data to marker via Tag
                        val list = listOf(address, phone, email, web_url)
                        m.setTag(list)
                    } else {
                        Log.d(TAG, "This course type does not exist in evaluation $type")
                    }
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(65.5, 26.0), 5.0F))
            },
            { error ->
                // Error loading JSON
            }

        )
        // Add the request to the RequestQueue
        queue.add(jsonObjectRequest)
        // Add custom info window adapter
        mMap.setInfoWindowAdapter(CustomInfoWindowAdapter())

    }


    internal inner class CustomInfoWindowAdapter : GoogleMap.InfoWindowAdapter {
        private val contents: View = layoutInflater.inflate(R.layout.info_window, null)


        override fun getInfoWindow(marker: Marker?): View? {
            return null
        }

        override fun getInfoContents(marker: Marker): View {
            // UI elements
            val titleTextView = contents.titleTextView
            val addressTextView = contents.addressTextView
            val phoneTextView = contents.phoneTextView
            val emailTextView = contents.emailTextView
            val webTextView = contents.webTextView
            // title
            titleTextView.text = marker.title.toString()
            // get data from Tag list
            if (marker.tag is List<*>) {
                val list: List<String> = marker.tag as List<String>
                addressTextView.text = list[0]
                phoneTextView.text = list[1]
                emailTextView.text = list[2]
                webTextView.text = list[3]
            }
            return contents
        }
    }
}



