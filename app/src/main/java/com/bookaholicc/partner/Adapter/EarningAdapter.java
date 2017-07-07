package com.bookaholicc.partner.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bookaholicc.partner.Model.Earning;
import com.bookaholicc.partner.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 23/6/17.
 *
 */

public class EarningAdapter extends RecyclerView.Adapter<EarningAdapter.EarningsHolder> {
    private List<Earning> mList;
    private Context mContext;
    String rs = null;

    public EarningAdapter(List<Earning> mList, Context mContext) {
        this.mContext = mContext;
        this.mList = mList;
        rs = mContext.getString(R.string.rs);

    }

    @Override
    public EarningsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.earning_item,parent,false);
        return new EarningsHolder(v);
    }

    @Override
    public void onBindViewHolder(EarningsHolder holder, int position) {
        long mTimeStamp = Long.parseLong(mList.get(position).getTimeStamp());
            holder.pName.setText(mList.get(position).getpName());

             Picasso.with(mContext)
                .load(mList.get(position).getProductImage())
                .into(holder.mImage);
          holder.mAmount.setText("+ "+ rs + mList.get(position).getAmount());
          String date = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date (mTimeStamp*1000));
          holder.mTimeStamp.setText(date);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class  EarningsHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.earning_item_pname) TextView pName;
        @BindView(R.id.earning_item_pimage) ImageView mImage;
        @BindView(R.id.earning_item_timestamp) TextView mTimeStamp;
        @BindView(R.id.earning_item_amount) TextView mAmount;

        public EarningsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
