package com.irfan.storyapp.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.irfan.storyapp.R
import com.irfan.storyapp.adapter.LoadingStateAdapter
import com.irfan.storyapp.adapter.StoryAdapter
import com.irfan.storyapp.data.datastore.SessionPreferences
import com.irfan.storyapp.databinding.ActivityHomeBinding
import com.irfan.storyapp.view.map.StoryMapsActivity
import com.irfan.storyapp.view.setting.SettingActivity
import com.irfan.storyapp.view.upload.UploadActivity


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: StoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupAction()
    }

    private fun setupAction() {
        getData()

        binding.refresh.setOnRefreshListener {
            getData()
            binding.refresh.isRefreshing = false
        }

        binding.fabUpload.setOnClickListener {
            val intent = Intent(this, UploadActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getData() {
        binding.rvStories.layoutManager = LinearLayoutManager(this)

        adapter = StoryAdapter()

        homeViewModel.getUserToken().observe(this) { tkn ->
            tkn.token.let {
                homeViewModel.getStories(it).observe(this) { paging ->
                    adapter.submitData(lifecycle, paging)
                }
            }
        }

        adapter.addLoadStateListener {
            if (adapter.itemCount > 1) {
                binding.apply {
                    imgEmpty.visibility = View.INVISIBLE
                    tvEmpty.visibility = View.INVISIBLE
                }
            }
            binding.progress.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE
        }

        binding.rvStories.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.actionbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_setting -> {
                val i = Intent(this, SettingActivity::class.java)
                startActivity(i)
                true
            }
            R.id.menu_map -> {
                val i = Intent(this, StoryMapsActivity::class.java)
                startActivity(i)
                true
            }
            else -> true
        }
    }

    private fun setupViewModel() {
        homeViewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(SessionPreferences.getInstance(dataStore))
        )[HomeViewModel::class.java]
    }
}