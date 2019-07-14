package manic.com.remindmethere

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.places.PlaceBuffer

class PlaceAdapter : RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

    private lateinit var mPlaces: PlaceBuffer

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        if (mPlaces == null) {
            return 0;
        }
        return mPlaces.count();
    }

    fun swapPlaces(places: PlaceBuffer) {
        this.mPlaces = places;
        if(mPlaces != null){
            notifyDataSetChanged();
        }
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}