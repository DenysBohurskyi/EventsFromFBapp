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
import com.knacky.events.data.entities.events.EventsModel;
import com.knacky.events.presentation.fragments.EventsListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by knacky on 20.02.2018.
 */

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {

    EventsListFragment.EventListFragmentListener onClickListener;
    EventsModel eventsModel;
    Context context;


    public EventListAdapter(Context context, EventsModel eventsModel, EventsListFragment.EventListFragmentListener onClickListener) {
        this.context = context;
        this.eventsModel = eventsModel;
        this.onClickListener = onClickListener;
//        this.constraintLayout = constrLayout;

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eventlist_item_layout, parent, false);

        return (new ViewHolder(view));
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.v("Adapter", "model = " + eventsModel.getEvents().get(position).getName());
        holder.evDateTextView.setText(eventsModel.getEvents().get(position).getStartTime());
        holder.evNameTextView.setText(eventsModel.getEvents().get(position).getName());
        String internetUrl = eventsModel.getEvents().get(position).getProfilePicture();
        Glide
                .with(context)
                .load(internetUrl)
                .into(holder.eventPicture);

        holder.constraintLayout.setTag(position);
        holder.constraintLayout.setOnClickListener(view->
                onClickListener.onItemClick(eventsModel.getEvents().get(position).getId()));

        Log.i("Adapter", "Event ID: " + eventsModel.getEvents().get(position).getId());

    }


    @Override
    public int getItemCount() {
        return eventsModel.getMetadata().getEventsNumber();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.evPictureImgView)
        ImageView eventPicture;

        @BindView(R.id.evNameListTV)
        TextView evNameTextView;

        @BindView(R.id.evDatePageTV)
        TextView evDateTextView;

        @BindView(R.id.constraint_event_item_layout)
        ConstraintLayout constraintLayout;


        public ViewHolder(View itemView) {

            super(itemView);

            ButterKnife.bind(this, itemView);
//            eventPicture = itemView.findViewById(R.id.evPictureImgView);
//            evDateTextView = itemView.findViewById(R.id.evDateListTV);
//            evNameTextView = itemView.findViewById(R.id.evNameListTV);
//            constraintLayout = itemView.findViewById(R.id.constraint_event_item_layout);


        }

//        @Override
//        public void onClick(View view) {
//            Toast.makeText(view.getContext(), Integer.toString((int) constraintLayout.getTag() + 1), Toast.LENGTH_SHORT).show();
//        }
    }
}
