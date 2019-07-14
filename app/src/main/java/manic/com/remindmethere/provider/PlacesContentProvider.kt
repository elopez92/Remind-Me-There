package manic.com.remindmethere.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import java.lang.UnsupportedOperationException

class PlacesContentProvider : ContentProvider() {

    private lateinit var mPlaceDbHelper : PlaceDbHelper
    private val DATABASE_NAME = "places.db"
    private val DATABASE_VERSION = 1

    companion object{
        val PLACES = 100
        val PLACES_WITH_ID = 10
        private val sUriMatcher : UriMatcher = buildUriMatcher()
        fun buildUriMatcher(): UriMatcher{
            var uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            //Add URI matches
            uriMatcher.addURI(PlaceContract.AUTHORITY, PlaceContract.PATH_PLACES, PLACES)
            uriMatcher.addURI(PlaceContract.AUTHORITY, PlaceContract.PATH_PLACES + "/#", PLACES_WITH_ID)
            return uriMatcher
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Implement this to handle requests to delete one or more rows")
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues): Uri? {
        TODO("Implement this to handle requests to insert a new row.")
    }

    override fun onCreate(): Boolean {
        var context : Context = context
        mPlaceDbHelper = PlaceDbHelper(context, DATABASE_NAME, null,  DATABASE_VERSION)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {

        val db : SQLiteDatabase = mPlaceDbHelper.readableDatabase

        var match = sUriMatcher.match(uri)
        var retCursor: Cursor

        when(match) {
            PLACES -> retCursor = db.query(PlaceContract.Companion.PlaceEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder)
            else -> throw  UnsupportedOperationException("Unknown uri: " + uri)
        }
        retCursor.setNotificationUri(context.contentResolver, uri);
        return retCursor;
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }
}
