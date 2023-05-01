package edu.towson.cos435.thompson.photoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val adapter = ImageAdapter { image ->
            val intent = Intent(this, ImageDetailActivity::class.java)
            intent.putExtra("image", image)
            startActivity(intent)
        }
        binding.recyclerView.adapter = adapter

        viewModel.images.observe(this) { images ->
            adapter.setImages(images)
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
