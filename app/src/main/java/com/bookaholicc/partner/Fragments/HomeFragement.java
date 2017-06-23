package com.bookaholicc.partner.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bookaholicc.partner.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nandhu on 23/6/17.
 * The Fragment which shows what is his Earning and May be Which is New Books that he may want to  Buy andRead
 */

public class HomeFragement extends Fragment {

    @BindView(R.id.home_amount)
    TextView mAmount;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.home_tot_p_text)
    TextView mTotalProductstext;
    @BindView(R.id.home_total_p_val)
    TextView mProductsCount;
    @BindView(R.id.home_p_exchanged)
    TextView mExText;
    @BindView(R.id.home_p_exchnaged_val)
    TextView mRentedCount;
    @BindView(R.id.home_month)
    TextView mMonth;
    @BindView(R.id.home_bill_breakdown)
    Button mEarningsButton;
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.home_frag, container, false);
        ButterKnife.bind(this, v);



        mEarningsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEarningsPage();
            }
        });


        return v;
    }

    private void showEarningsPage() {
        EarningFragment mFragment  = new EarningFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mContext != null) {
            mContext = null;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mContext == null) {
            mContext = context;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
