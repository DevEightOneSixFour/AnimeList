package com.example.animelist.utils

import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.animelist.api.CrunchyRoll

object GlideHelper {

    /*
        The images of this api also need a header to get them from the server.
        Created a helper object to handle adding the header for every Glide call.
     */
    fun getUrlWithHeaders(url: String): GlideUrl {
        return GlideUrl(url, LazyHeaders.Builder()
            .addHeader(CrunchyRoll.CLIENT_KEY, CrunchyRoll.KEY_VALUE)
            .build())
    }
}