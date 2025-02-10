package com.example.staggeredphotogallery

import android.content.res.XmlResourceParser
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.xmlpull.v1.XmlPullParser

data class PhotoItem(val title: String, val fileName: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val photos = loadPhotosFromXml()

        setContent {
            PhotoGalleryScreen(photos)
        }
    }

    private fun loadPhotosFromXml(): List<PhotoItem> {
        val photos = mutableListOf<PhotoItem>()
        val parser: XmlResourceParser = resources.getXml(R.xml.photos)
        var title = ""
        var fileName = ""

        while (parser.eventType != XmlPullParser.END_DOCUMENT) {
            when (parser.eventType) {
                XmlPullParser.START_TAG -> {
                    when (parser.name) {
                        "title" -> title = parser.nextText()
                        "file" -> fileName = parser.nextText()
                    }
                }
                XmlPullParser.END_TAG -> {
                    if (parser.name == "photo") {
                        photos.add(PhotoItem(title, fileName))
                    }
                }
            }
            parser.next()
        }
        return photos
    }
}
