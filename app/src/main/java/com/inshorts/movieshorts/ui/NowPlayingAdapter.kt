package com.inshorts.movieshorts.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inshorts.movieshorts.R
import com.inshorts.movieshorts.domain.model.Movie

class NowPlayingPreviewAdapter(
    private val items: List<Movie>, private val
    onClick: (Movie) -> Unit
) :
    RecyclerView.Adapter<NowPlayingPreviewAdapter.VH>() {
    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.ivPoster)
        val title: TextView = view.findViewById(R.id.tvTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_now_playing,
                parent, false
            )
        )

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: VH, position: Int) {
        val m = items[position]
        holder.title.text = m.title
        Glide.with(holder.itemView.context).load(imageUrl(m.posterPath)).into(holder.img)
        holder.itemView.setOnClickListener { onClick(m) }

        holder.itemView.setOnClickListener {
            val i = Intent(holder.itemView.context, MovieDetailActivity::class.java)
            i.putExtra("movie", m)
            holder.itemView.context.startActivity(i)
        }
    }

    override fun getItemCount() = items.size

    companion object {
        private fun imageUrl(path: String?) = if (path == null) "" else
            "https://image.tmdb.org/t/p/w500$path"
    }
}