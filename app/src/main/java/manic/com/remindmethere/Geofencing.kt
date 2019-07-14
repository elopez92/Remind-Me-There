package manic.com.remindmethere

import android.content.Context
import androidx.annotation.NonNull
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.places.PlaceBuffer
import java.util.ArrayList

class Geofencing(context:Context, mClient: GoogleApiClient){

    companion object {
        private val GEOFENCE_RADIUS = 50f // 50 meters
        private val GEOFENCE_TIMEOUT = (24 * 60 * 60 * 1000).toLong() // 24 hours
    }
    private lateinit var mGeofenceList : List<Geofence>

    fun updateGeofencesList(places: PlaceBuffer?) {
        mGeofenceList = ArrayList<Geofence>()
        if (places == null || places.count == 0) return
        for (place in places) {
            // Read the place information from the DB cursor
            val placeUID = place.id
            val placeLat = place.latLng.latitude
            val placeLng = place.latLng.longitude
            // Build a Geofence object
            val geofence = Geofence.Builder()
                .setRequestId(placeUID)
                .setExpirationDuration(GEOFENCE_TIMEOUT)
                .setCircularRegion(placeLat, placeLng, GEOFENCE_RADIUS)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
                .build()
            // Add it to the list
            (mGeofenceList as ArrayList<Geofence>).add(geofence)
        }
    }
}