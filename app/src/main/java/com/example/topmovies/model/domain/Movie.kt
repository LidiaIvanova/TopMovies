package com.example.topmovies.model.domain

import android.os.Parcel
import android.os.Parcelable
import com.example.topmovies.model.dateToLong
import com.example.topmovies.model.longDateToDate
import java.util.*


data class Movie(
    val id: Int,
    val posterPath: String,
    val voteAverage: Int,
    val title: String,
    val releaseDate: Date?,
    val overview: String,
    val page: Int
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        longDateToDate(parcel.readLong()),
        parcel.readString() ?: "",
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(posterPath)
        parcel.writeInt(voteAverage)
        parcel.writeString(title)
        parcel.writeLong(dateToLong(releaseDate))
        parcel.writeString(overview)
        parcel.writeInt(page)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        const val MOVIE_KEY = "MOVIE_KEY"
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}