package com.mrtvrgn.practice.filmsecimi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val filmler = listOf<FilmModel>(
            FilmModel("The Shawshank Redemption", 1994),
            FilmModel("The Godfather", 1972),
            FilmModel("The Godfather: Part II", 1974),
            FilmModel("The Dark Knight", 2008),
            FilmModel("12 Angry Men", 1954),
            FilmModel("Schindler's List", 1993),
            FilmModel(" The Lord of the Rings: The Return of the King", 2003),
            FilmModel("Pulp Fiction", 1994),
            FilmModel("The Good, the Bad and the Ugly", 1966),
            FilmModel("The Lord of the Rings: The Fellowship of the Ring", 2001),
            FilmModel("Fight Club", 1999),
            FilmModel("Forrest Gump", 1994),
            FilmModel("Inception", 2010),
            FilmModel("The Lord of the Rings: The Two Towers", 2002),
            FilmModel("Star Wars: Episode V - The Empire Strikes Back", 1980),
            FilmModel("The Matrix", 1999),
            FilmModel("Goodfellas", 1990),
            FilmModel("One Flew Over the Cuckoo's Nest", 1975),
            FilmModel("Se7en", 1995)
        )


        val recycler: RecyclerView = findViewById<RecyclerView>(R.id.recycler)

        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val clickListener = object : FilmClickInterface {
            override fun onClick() {

                startActivity(Intent(this@MainActivity, SecondActivity::class.java))
            }
        }

        val adapter = FilmAdapter(clickListener)

        adapter.setFilms(filmler)

        recycler.adapter = adapter

        LinearSnapHelper().attachToRecyclerView(recycler)
    }
}

interface FilmClickInterface {
    fun onClick()
}

class FilmAdapter(val clickInterface: FilmClickInterface) : RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {

    var items: List<FilmModel> = emptyList()

    class FilmViewHolder(parentView: View) : RecyclerView.ViewHolder(parentView) {
        val filmAdi = parentView.findViewById<TextView>(R.id.film_adi)
        val filmYili = parentView.findViewById<TextView>(R.id.film_yili)
        val favori = parentView.findViewById<ImageView>(R.id.favori)
        init {
            adapterPosition
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_film, parent, false)
        return FilmViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            clickInterface.onClick()
        }

        holder.filmAdi.text = items[position].name
        holder.filmYili.text = items[position].year.toString()

        holder.favori.isSelected = items[position].isFavorite

        holder.favori.setOnClickListener {
            if (items[position].isFavorite) {
                items[position].isFavorite = false
                holder.favori.isSelected = false
            } else {
                items[position].isFavorite = true
                holder.favori.isSelected = true
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun setFilms(items: List<FilmModel>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun updateFilm(film: FilmModel){
        notifyItemChanged(items.indexOf(film))
    }
}