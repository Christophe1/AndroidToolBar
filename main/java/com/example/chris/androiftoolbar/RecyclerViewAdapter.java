package com.example.chris.androiftoolbar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chris on 12/12/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    public List<SelectPhoneContact>mSelectPhoneContact;
    private ArrayList<SelectPhoneContact> arraylist;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        //In each recycler_blueprint show the items you want to have

        public TextView title, phone;
        public CheckBox check;
        //public Button invite;

        public ViewHolder(final View itemView) {
            super(itemView);
            //title is cast to the name id, in recycler_blueprint,
            //phone is cast to the id called no etc
            title = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.no);
            // invite = (Button) itemView.findViewById(R.id.btnInvite);
            check = (CheckBox) itemView.findViewById(R.id.checkBoxContact);

        }
    }


    public RecyclerViewAdapter(List<SelectPhoneContact>selectPhoneContacts) {
        mSelectPhoneContact=selectPhoneContacts;
        this.arraylist = new ArrayList<SelectPhoneContact>();
        this.arraylist.addAll(mSelectPhoneContact);
        System.out.println("this.arraylist" + this.arraylist);

    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.recycler_blueprint, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder viewHolder, int position){
        SelectPhoneContact selectPhoneContact = mSelectPhoneContact.get(position);
        TextView title = viewHolder.title;
        title.setText(selectPhoneContact.getName());

        TextView phone = viewHolder.phone;
        phone.setText(selectPhoneContact.getPhone());

    }

    @Override
    public int getItemCount(){
/*        if(mSelectPhoneContact == null)
            return 0;*/
        return mSelectPhoneContact.size();
    }
}
