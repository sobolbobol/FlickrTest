package com.sobolcorp.flickrtest

import com.sobolcorp.flickrtest.network.models.PhotosResponse
import org.junit.Test
import com.google.gson.Gson
import org.junit.Assert


class PostSearchViewModelTest {
    private val JSON_RESPONSE = "{\"photos\":{\"page\":2,\"pages\":50,\"perpage\":20,\"total\":1000" +
            ",\"photo\":[{\"id\":\"28264895417\",\"owner\":\"140346801@N03\",\"secret\":\"f92c044a4" +
            "5\",\"server\":\"1827\",\"farm\":2,\"title\":\"From Internet Camera Manasquan (B0:C5:5" +
            "4:26:AC:2E), 2018\\/07\\/01 14:04:57D\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0}]" +
            "},\"stat\":\"ok\"}\n"

    private var gson = Gson()

    @Test
    fun shouldDesearialize() {
        val photos = gson.fromJson<PhotosResponse>(JSON_RESPONSE, PhotosResponse::class.java)
        Assert.assertEquals(photos.photos.photo.size, 1)
        Assert.assertEquals(photos.stat, "ok")
    }
}