package manic.com.remindmethere

import android.content.pm.PackageManager
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Places
import com.google.android.gms.location.places.ui.PlacePicker
import manic.com.remindmethere.provider.PlaceContract
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
                LoaderManager.LoaderCallbacks<Cursor>{

    private val TAG = "MainActivity"

    private val PERMISSION_REQUEST_FINE_LOCATION = 11;
    private val PLACE_PICKER_REQUEST = 1;

    //private lateinit var placesClient : PlacesClient;
    private lateinit var mClient: GoogleApiClient;
    private lateinit var mGeofenceClient: GeofencingClient;
    private lateinit var mGeofencing: Geofencing;
    private lateinit var mPlacesAdapter: PlaceAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Places.initialize(applicationContext, getString(R.string.api_key));
        //placesClient = Places.createClient(this);

        mClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .addApi(Places.GEO_DATA_API)
            .enableAutoManage(this, this)
            .build();

        mGeofencing = Geofencing(this, mClient);

    }

    fun onAddPlaceButtonClicked(view: View){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, getString(R.string.need_location_permission), Toast.LENGTH_LONG).show()
            return;
        }
        try {
            var builder = PlacePicker.IntentBuilder();
            var i = builder.build(this);
            startActivityForResult(i, PLACE_PICKER_REQUEST);
        } catch (e: GooglePlayServicesRepairableException) {
            Log.e(TAG, String.format("GooglePlayServices Not Available [%s]", e.message));
        }catch (e: GooglePlayServicesNotAvailableException) {
            Log.e(TAG, String.format("GooglePlayServices Not Available [%s]", e.message));
        } catch (e: Exception) {
            Log.e(TAG, String.format("PlacePicker Exception: %s", e.message));
        }

    }

    fun refreshPlacesData() {
        var uri = PlaceContract.Companion.PlaceEntry.CONTENT_URI;
        var data = contentResolver.query(uri, null, null, null, null);

        if (data == null || data.count == 0) return;
        var guids: ArrayList<String> = ArrayList();

        while(data.moveToNext()){
            guids.add(data.getString(data.getColumnIndex(PlaceContract.Companion.PlaceEntry.COLUMN_PLACE_ID)))
            }
        var placeResult = Places.GeoDataApi.getPlaceById(mClient, guids.toArray(arrayOfNulls<String>(guids.size)).toString())
        placeResult.setResultCallback { places ->
            mPlacesAdapter.swapPlaces(places);
            mGeofencing.updateGeofencesList(places);
        }
    }

    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<Cursor> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoadFinished(p0: Loader<Cursor>, p1: Cursor?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoaderReset(p0: Loader<Cursor>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnected(p0: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
