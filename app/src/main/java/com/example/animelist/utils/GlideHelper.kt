package com.example.animelist.utils

import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.animelist.api.CrunchyRoll

object GlideHelper {

    private const val placeholder = "https://cdn2.vectorstock.com/i/1000x1000/17/16/default-avatar-anime-girl-profile-icon-vector-21171716.jpg"
    /*
        The images of this api also need a header to get them from the server.
        Created a helper object to handle adding the header for every Glide call.
     */
    fun getUrlWithHeaders(url: String?): GlideUrl {
        return if (url == null) {
            GlideUrl(placeholder)
        } else {
            GlideUrl(url, LazyHeaders.Builder()
                .addHeader(CrunchyRoll.CLIENT_KEY, CrunchyRoll.KEY_VALUE)
                .build())
        }
    }
}