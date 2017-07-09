package com.bookaholicc.partner.Adapter;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bookaholicc.partner.CustomUI.WhitenyBooksFont;
import com.bookaholicc.partner.Model.MiniProduct;
import com.bookaholicc.partner.R;
import com.squareup.picasso.Picasso;
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
    private booksCallback mBooksCallback ;



    @Override
    public BooksItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BooksItem(LayoutInflater.from(mContext).inflate(R.layout.book_item,parent,false));
    }

    @Override
    public void onBindViewHolder(BooksItem holder, int position) {

        final MiniProduct p  = mList.get(position);
        holder.mProductName.setText(mList.get(position).getProductName());
        Picasso.with(mContext)
                .load(mList.get(position).getImageURL())
                .into(holder.pImaage);
        holder.mAvaiblitySwitch.setChecked(true);

        holder.mAvaiblitySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.getId() == R.id.b_item_switch){
                    if (mBooksCallback != null){
                      if (mBooksCallback != null){
                          mBooksCallback.changeAvailblityStatus(p.getPid(),isChecked);
                      }
                    }

                }
            }
        });
        holder.mChangQuanText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBooksCallback!= null){
                    mBooksCallback.changeQuantity(p.getPid());
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class BooksItem extends RecyclerView.ViewHolder{


        @BindView(R.id.b_item_pname)
        WhitenyBooksFont mProductName;

        @BindView(R.id.b_item_purl)
        ImageView pImaage;

        @BindView(R.id.b_item_amount) TextView mrp;

        @BindView(R.id.b_item_switch)
        Switch mAvaiblitySwitch;

        @BindView(R.id.chg_quantity_text)
        WhitenyBooksFont mChangQuanText;


        public BooksItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public BooksAdapter(Context mContext, List<MiniProduct> mlist,booksCallback mBooksCallback) {
        this.mList = mlist;
        this.mContext = mContext;
        this.mBooksCallback = mBooksCallback;
    }

    public  interface  booksCallback{
        void changeQuantity(int pid);
        void changeAvailblityStatus(int pid,boolean status);
        void seeDetails(int pid);
    }
}
