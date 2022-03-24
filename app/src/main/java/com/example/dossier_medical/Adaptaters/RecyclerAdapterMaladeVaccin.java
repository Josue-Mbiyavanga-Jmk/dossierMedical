package com.example.dossier_medical.Adaptaters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dossier_medical.Entites.EMalade;
import com.example.dossier_medical.Entites.EVaccin;
import com.example.dossier_medical.R;
import com.example.dossier_medical.Utils.UtilTimeStampToDate;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import customfonts.MyTextView_Roboto_Regular;


public class RecyclerAdapterMaladeVaccin extends RecyclerView.Adapter<RecyclerAdapterMaladeVaccin.ViewHolder> {
    private List<EVaccin> items;
    private AppCompatActivity activity;

    public interface ItemButtonListener {

        void onUpdateClickListener(int position);
        void onItemClickListener(int position);
    }

    private  final ItemButtonListener callback_click;


    public RecyclerAdapterMaladeVaccin(AppCompatActivity appCompatActivity, List<EVaccin> items, ItemButtonListener callback) {
        this.items = items;
        this.activity=appCompatActivity;
        callback_click=callback;
    }



    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerAdapterMaladeVaccin.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_story_vaccin, parent, false);

        return new ViewHolder(v);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final EVaccin item =items !=null ? items.get(position):null;


        if(item!=null)
        {
            holder.txt_name.setText(item.getNom());
            holder.txt_date.setText(item.getDate());


        }
                holder.updateWith(this.callback_click);
                holder.updateItemClick(this.callback_click);
        }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return items.size();
    }



    public void change(EMalade item) {
        int position = items.indexOf(item);
        notifyItemChanged(position);
    }



    public void notify(List<EMalade> list_items) {

        //items.clear();
//        items.addAll(list_items);
        notifyDataSetChanged();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public MyTextView_Roboto_Regular txt_name,txt_date;
        public ImageView img_status;


        public ViewHolder(View v) {
            super(v);

            txt_name = v.findViewById(R.id.txt_name);
            txt_date = v.findViewById(R.id.txt_date);
            img_status = v.findViewById(R.id.img_status);
        }

        private WeakReference<ItemButtonListener> callbackWeakRef;

        public void updateWith(ItemButtonListener callback){

            this.img_status.setOnClickListener(this);
            this.callbackWeakRef = new WeakReference<>(callback);
        }


        public void updateItemClick(ItemButtonListener callback){

            //3 - Implement Listener on ImageButton
            if(this.callbackWeakRef!=null)
            {
                itemView.setOnClickListener(this);
            }
            else
            {
                this.callbackWeakRef = new WeakReference<>(callback);
                itemView.setOnClickListener(this);
            }

        }


        @Override
        public void onClick(View v) {
            // 5 - When a click happens, we fire our listener.
            ItemButtonListener callback = callbackWeakRef.get();
            if (callback != null)
            {

               // callback.onItemClickListener(getAdapterPosition());
               switch (v.getId())
                {
                    case R.id.img_status:
                        callback.onUpdateClickListener(getAdapterPosition());
                        break;
                    default:
                        callback.onItemClickListener(getAdapterPosition());
                        break;
                }

            }
        }
    }

}
