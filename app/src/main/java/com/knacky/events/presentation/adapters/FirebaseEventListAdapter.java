package com.knacky.events.presentation.adapters;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.knacky.events.R;
import com.knacky.events.data.entities.firebase.EventModelFirebase;
import com.knacky.events.presentation.fragments.CreateEventFragment;
import com.knacky.events.presentation.fragments.EventsListFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by knacky on 29.05.2018.
 */
public class FirebaseEventListAdapter extends RecyclerView.Adapter<FirebaseEventListAdapter.FirebaseEventsViewHolder>{

    private EventsListFragment.EventListFragmentListener eventListListener;
    private List<EventModelFirebase> eventsList;
    Context context;


    public FirebaseEventListAdapter(Context context, List<EventModelFirebase> eventsList,EventsListFragment.EventListFragmentListener listener) {
        this.eventListListener = listener;
        this.eventsList = eventsList;
        this.context = context;
    }

    @Override
    public FirebaseEventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eventlist_item_layout, parent, false);
        return new FirebaseEventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FirebaseEventsViewHolder holder, int position) {
        EventModelFirebase eventModelFirebase = eventsList.get(position);

        holder.evName.setText(eventModelFirebase.getCrEventName());
        holder.evDate.setText(eventModelFirebase.getCrEventDate());
        Glide
                .with(context)
                .load(eventModelFirebase.getCrEventImgUri())
                .into(holder.evPicture);
        holder.constraintLayout.setOnClickListener(view->
                eventListListener.onEventItemClick(eventsList.get(position).getCrEventImgUri()));

//        Log.i("Adapter", "Event ID: " + eventsModel.getEvents().get(position).getId());

    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    class FirebaseEventsViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.evPictureImgView)
        ImageView evPicture;

        @BindView(R.id.evDatePageTV)
        TextView evDate;

        @BindView(R.id.evNameListTV)
        TextView evName;

        @BindView(R.id.constraint_event_item_layout)
        ConstraintLayout constraintLayout;

        public FirebaseEventsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
