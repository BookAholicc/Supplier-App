package com.bookaholicc.partner.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bookaholicc.partner.Fragments.EarningFragment;
import com.bookaholicc.partner.Model.Earning;
import com.bookaholicc.partner.R;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;

/**
 * Created by nandhu on 23/6/17.
 *
 */

public class EarningAdapter extends RecyclerView.Adapter<EarningAdapter.EarningsHolder> {
    private List<Earning> mList;
    private Context mContext;

    public EarningAdapter(List<Earning> mList, EarningFragment earningFragment, Context mContext) {
        this.mContext = mContext;
        this.mList = mList;

    }

    @Override
    public EarningsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.earning_item,parent,false);
        return new EarningsHolder(v);
    }

    @Override
    public void onBindViewHolder(EarningsHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class  EarningsHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.earning_item_pname) TextView pName;
        @BindView(R.id.earning_item_price) TextView price;
        @BindView(R.id.earning_item_duration) TextView duration;
        @BindView(R.id.earning_item_image) ImageView mImage;
        public EarningsHolder(View itemView) {
            super(itemView);
        }
    }
}
