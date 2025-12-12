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

class SearchAdapter(
    private val movies: MutableList<Movie> = mutableListOf(),
    private val onMovieClick: (Movie) -> Unit
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    inner class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val poster: ImageView = view.findViewById(R.id.ivPoster)
        val title: TextView = view.findViewById(R.id.tvTitle)
        val releaseYear: TextView = view.findViewById(R.id.tvReleaseYear)
        val overview: TextView = view.findViewById(R.id.tvOverview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_result, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val movie = movies[position]
        
        holder.title.text = movie.title
        holder.overview.text = movie.overview
        
        // Extract year from release date
        val year = movie.releaseDate?.take(4) ?: "N/A"
        holder.releaseYear.text = year
        
        val posterUrl = "https://image.tmdb.org/t/p/w200${movie.posterPath}"
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
}
