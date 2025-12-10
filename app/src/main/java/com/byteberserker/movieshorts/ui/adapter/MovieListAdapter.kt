package com.byteberserker.movieshorts.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.byteberserker.movieshorts.R
import com.byteberserker.movieshorts.domain.model.Movie
import com.byteberserker.movieshorts.ui.activity.MovieDetailActivity

class MovieListAdapter(
    private val movies: MutableList<Movie> = mutableListOf()
) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val poster: ImageView = view.findViewById(R.id.ivPoster)
        val title: TextView = view.findViewById(R.id.tvTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_grid, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.title.text = movie.title
        
        val posterUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
        Glide.with(holder.itemView.context)
            .load(posterUrl)
            .centerCrop()
            .into(holder.poster)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, MovieDetailActivity::class.java)
            intent.putExtra("movie", movie)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = movies.size

    fun addMovies(newMovies: List<Movie>) {
        val startPosition = movies.size
        movies.addAll(newMovies)
        notifyItemRangeInserted(startPosition, newMovies.size)
    }
}
