package edu.towson.cos435.thompson.photoapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class ImageDetailActivity : AppCompatActivity() {
    private val viewModel: ImageDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityImageDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val image = intent.getParcelableExtra<Image>("image")!!
        viewModel.setImage(image)

        Glide.with(binding.image)
            .load("https://picsum.photos/id/${image.id}/800/600")
            .into(binding.image)

        supportActionBar?.title = image.author
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
