package com.example.moviedbdemo.model

import com.google.gson.annotations.SerializedName
/*
    Model class: latest response movie model
    Example json response
    {
        "adult": true,
        "backdrop_path": null,
        "belongs_to_collection": null,
        "budget": 0,
        "genres": [],
        "homepage": "",
        "id": 969811,
        "imdb_id": null,
        "original_language": "en",
        "original_title": "Heartbreaking Wives Cuckold You",
        "overview": "",
        "popularity": 0.0,
        "poster_path": null,
        "production_companies": [],
        "production_countries": [],
        "release_date": "",
        "revenue": 0,
        "runtime": 0,
        "spoken_languages": [],
        "status": "Released",
        "tagline": "",
        "title": "Heartbreaking Wives Cuckold You",
        "video": false,
        "vote_average": 0.0,
        "vote_count": 0
    }
 */
class MovieModel {
    @SerializedName("id")
    var movieId: Int = 0

    @SerializedName("homepage")
    var homepage: String = ""

    @SerializedName("original_title")
    var originalTitle: String = ""

    @SerializedName("poster_path")
    var posterPath: String = ""
}