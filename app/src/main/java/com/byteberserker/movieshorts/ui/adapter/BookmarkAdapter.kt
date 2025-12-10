package com.byteberserker.movieshorts.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.byteberserker.movieshorts.R
import com.byteberserker.movieshorts.domain.model.Movie

class BookmarkAdapter(
    private val movies: MutableList<Movie> = mutableListOf(),
    private val onMovieClick: (Movie) -> Unit
) : RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {

    inner class BookmarkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val poster: ImageView = view.findViewById(R.id.ivPoster)
        val title: TextView = view.findViewById(R.id.tvTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_grid, parent, false)
        return BookmarkViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        val movie = movies[position]
        holder.title.text = movie.title
        
        val posterUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
        Glide.with(holder.itemView.context)
            .load(posterUrl)
            .centerCrop()
            .into(holder.poster)

        holder.itemView.setOnClickListener {
            onMovieClick(movie)
        }
    }

    override fun getItemCount() = movies.size

    fun setMovies(newMovies: List<Movie>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    fun removeMovie(movieId: Int) {
        val index = movies.indexOfFirst { it.id == movieId }
        if (index != -1) {
            movies.removeAt(index)
            notifyItemRemoved(index)
        }
    }
}
