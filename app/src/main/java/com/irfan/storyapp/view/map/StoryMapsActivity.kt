package com.irfan.storyapp.view.map

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.irfan.storyapp.R
import com.irfan.storyapp.data.datastore.SessionPreferences
import com.irfan.storyapp.databinding.ActivityStoryMapsBinding


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class StoryMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityStoryMapsBinding
    private lateinit var mapViewModel: StoryMapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStoryMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setupViewModel()
        setupActionBar()
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = resources.getString(R.string.maps)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupViewModel() {
        mapViewModel = ViewModelProvider(
            this,
            StoryMapViewModelFactory(SessionPreferences.getInstance(dataStore))
        )[StoryMapViewModel::class.java]
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        val indonesia = LatLng(0.143136, 118.7371783)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(indonesia, 3f))

        mapViewModel.getUserToken().observe(this) {
            mapViewModel.getMapStory(it.token)
        }

        mapViewModel.mapStory.observe(this) { mapStory ->
            for (i in mapStory) {
                if ((i.lat != null) && (i.lon != null)) {
                    val marker = LatLng(i.lat, i.lon)
                    mMap.addMarker(
                        MarkerOptions().position(marker)
                            .title(i.name)
                            .snippet(i.description)
                    )
                }
            }
        }
        setMapStyle()
    }

    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success) {
                Toast.makeText(this, "Style Parsing Failed", Toast.LENGTH_SHORT).show()
            }
        } catch (exception: Resources.NotFoundException) {
            Toast.makeText(this, "Can't find style. Error: $exception", Toast.LENGTH_SHORT).show()
        }
    }
}