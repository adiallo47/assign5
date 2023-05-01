package edu.towson.cos435.thompson.photoapp

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import javax.sql.DataSource

class ImageAdapter(private val listener: (Image) -> Unit) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    private var images = emptyList<Image>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = images[position]
        holder.bind(image)
        holder.itemView.setOnClickListener { listener(image) }
    }

    override fun getItemCount(): Int {
        return images.size
    }

    fun setImages(images: List<Image>) {
        this.images = images
        notifyDataSetChanged()
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.image)
        private val loadingIndicator: ProgressBar = itemView.findViewById(R.id.loading_indicator)

        fun bind(image: Image) {
            Glide.with(imageView.context)
                .load("https://picsum.photos/id/${image.id}/${imageView.width}/${imageView.height}")
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        loadingIndicator.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        loadingIndicator.visibility = View.GONE
                        return false
                    }
                })
                .into(imageView)
        }
    }
}

