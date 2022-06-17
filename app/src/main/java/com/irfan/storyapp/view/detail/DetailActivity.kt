package com.irfan.storyapp.view.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.irfan.storyapp.R
import com.irfan.storyapp.adapter.StoryAdapter
import com.irfan.storyapp.data.api.ListStoryItem
import com.irfan.storyapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        setupActionBar()
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = resources.getString(R.string.detail)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupAction() {
        val data = intent.getParcelableExtra<ListStoryItem>(StoryAdapter.STORY)

        if (data != null) {
            Glide.with(this).load(data.photoUrl).into(binding.imgStory)

            binding.apply {
                tvUser.text = data.name
                tvDate.text = data.createdAt
                tvDescription.text = data.description
            }
        }
    }
}