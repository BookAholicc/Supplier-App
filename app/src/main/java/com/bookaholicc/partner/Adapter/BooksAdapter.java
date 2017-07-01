package com.bookaholicc.partner.Adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bookaholicc.partner.Model.MiniProduct;
import com.bookaholicc.partner.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 1/7/17.
 *
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksItem>{


    private Context mContext;
    private List<MiniProduct> mList;



    @Override
    public BooksItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BooksItem(LayoutInflater.from(mContext).inflate(R.layout.book_item,parent,false));
    }

    @Override
    public void onBindViewHolder(BooksItem holder, int position) {

        holder.mProductName.setText(mList.get(position).getProductName());
        Picasso.with(mContext)
                .load(mList.get(position).getImageURL())
                .into(holder.pImaage);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class BooksItem extends RecyclerView.ViewHolder{


        @BindView(R.id.b_item_pname)
        TextView mProductName;

        @BindView(R.id.b_item_purl)
        ImageView pImaage;

        @BindView(R.id.b_item_amount) TextView mrp;


        public BooksItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public BooksAdapter(Context mContext, List<MiniProduct> mlist) {
        this.mList = mlist;
        this.mContext = mContext;
    }
}
