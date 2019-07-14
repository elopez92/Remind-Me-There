package manic.com.remindmethere.provider

import android.net.Uri
import android.provider.BaseColumns

class PlaceContract {

    open class KBaseColumns {
        val _ID = "_id";
    }

    companion object{
        val AUTHORITY = "manic.com.remindmethere";
        val BASE_CONTENT_URI : Uri = Uri.parse("content://" + AUTHORITY);
        val PATH_PLACES = "places";

        class PlaceEntry : BaseColumns {
            companion object : KBaseColumns() {
                val CONTENT_URI : Uri = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PLACES).build();
                val TABLE_NAME = "places";
                val COLUMN_PLACE_ID = "placeID";
            }
        }
    }
}