package com.bookaholicc.partner;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bookaholicc.partner.Activity.AboutUsActivity;
import com.bookaholicc.partner.Activity.BooksActivity;
import com.bookaholicc.partner.Activity.EarningsActivity;
import com.bookaholicc.partner.Activity.FirstActivity;
import com.bookaholicc.partner.Activity.NotifcationsActivity;
import com.bookaholicc.partner.Activity.ProfileActivity;
import com.bookaholicc.partner.Activity.ReferFriendsActivity;
import com.bookaholicc.partner.CustomUI.WhitenyBooksFont;

import com.bookaholicc.partner.Network.AppRequestQueue;
import com.bookaholicc.partner.StorageHelpers.DataStore;
import com.bookaholicc.partner.utils.APIUtils;
import com.bookaholicc.partner.utils.ScreenUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        Response.ErrorListener,
        Response.Listener<JSONObject>,
        View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;




    //Profile name
    @BindView(R.id.home_partner_name)
    WhitenyBooksFont mPartnerName;

    // Profile Picture
    @BindView(R.id.home_pp)
    CircleImageView mProfilePic;
    @BindView(R.id.div)
    View div;

    @BindView(R.id.noti_image) CircleImageView mNotifImage;
    @BindView(R.id.profile_image) CircleImageView mProfilePage;


    //Current Amount in Transaction
    @BindView(R.id.home_amount)
    WhitenyBooksFont mAmount;

    @BindView(R.id.wallet_amount_text)
    WhitenyBooksFont mAmountText;
    @BindView(R.id.div2)
    View div2;


    // Earning Root
    @BindView(R.id.earning_root)
    RelativeLayout mEarningRoot;

    //Books Root
    @BindView(R.id.books_text_root)
    RelativeLayout mBookRoot;

    @BindView(R.id.frag_holder_main)
    RelativeLayout mFragHolder;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerRoot;


    @BindView(R.id.amount_loading_root)
    RelativeLayout mAmountLoadingRoot;

    @BindView(R.id.back_image)
    ImageView mBackImage;

    @BindView(R.id.amount_value_root) RelativeLayout mAmountRoot;



    ///header
//    @BindView(R.id.header_title) WhitenyBooksFont mheaderName;
//    @BindView(R.id.header_amount_text)
//    TextView mHeaderAmount;

    private boolean isAnimationsDone = false;
    private String TAG = "PARTNER HOME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DataStore mStore = DataStore.getStorageInstance(this);

        if (mStore.isFirstTime()) {
            // show sig Up Activity
            startActivity(new Intent(this, FirstActivity.class));
            this.finish();
        }

        mPartnerName.setText("Hello, "+ mStore.getFirstName()+".");


        getData();
        setUpViews();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        Picasso.with(this)
                .load(R.mipmap.bg)
                .resize(ScreenUtil.getScreenWidth(this),ScreenUtil.getScreenHeight(this))
                .centerCrop()
                .into(mBackImage);
        Picasso.with(this).load(R.mipmap.pro_pic_size)
                .resize(ScreenUtil.getScreenWidth(this),ScreenUtil.getScreenHeight(this))
                .centerCrop()
                .into(mProfilePic);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        showMainFrag();
//        setUpBottomar();






    }

    private void setUpViews() {

            startAnimations();
            mNotifImage.setOnClickListener(this);
            mProfilePage.setOnClickListener(this);
            mEarningRoot.setOnClickListener(this);
            mBookRoot.setOnClickListener(this);



    }

    private void startAnimations() {
       mPartnerName.setTranslationY(-ScreenUtil.getScreenHeight(this));
       mProfilePic.setTranslationY(mProfilePage.getTranslationY());
       mProfilePage.setTranslationX(ScreenUtil.getScreenWidth(this));
       mNotifImage.setTranslationX(-ScreenUtil.getScreenWidth(this));
       mPartnerName.animate().translationY(0).setDuration(600).setStartDelay(300).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener(){
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        mProfilePic.animate().translationY(0)
                                .setDuration(600).setInterpolator(new OvershootInterpolator(1.5f))
                                .setStartDelay(500)
                                .start();
                        mProfilePage.animate().translationX(0)
                                .setDuration(600).setInterpolator(new DecelerateInterpolator())
                                .setStartDelay(400)
                                .start();
                        mNotifImage.animate().translationX(0)
                                .setDuration(600).setInterpolator(new DecelerateInterpolator())
                                .setStartDelay(400)
                                .start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                })
                .start();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    private void getData() {
        JSONObject mObject = new JSONObject();
        DataStore mStore = DataStore.getStorageInstance(this);
        int partnerId = mStore.getPartnerId();
        try {
            mObject.put(APIUtils.PARTNER_ID, partnerId);
        } catch (Exception e) {
            Log.d(TAG, "getData: Exception in putting JSON File");
        }
        JsonObjectRequest mJsonObjectRequest = new JsonObjectRequest(Request.Method.POST, APIUtils.PARTNER_HOME_API, mObject, this, this);
        AppRequestQueue mAppRequestQueue = AppRequestQueue.getInstance(this);
        mAppRequestQueue.addToRequestQue(mJsonObjectRequest);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
//
        Intent i = null;
        if (id == R.id.nav_my_books) {
//            // Handle the camera action
            i = new Intent(this,BooksActivity.class);
        } else if (id == R.id.nav_transactions) {
            i = new Intent(this,EarningsActivity.class);
        } else if (id == R.id.nav_refer_friends) {
                i = new Intent(this,ReferFriendsActivity.class);
        } else if (id == R.id.nav_abt_us) {
              i = new Intent(this,AboutUsActivity.class);
        } else if (id == R.id.nav_share) {
            Toast.makeText(this,"Share Play Store Link",Toast.LENGTH_LONG).show();
        }

        startActivity(i);
//
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

//
    }

//    @Override
//    public void onTabSelected(@IdRes int tabId) {
//        Fragment mFragment = null;
//        String transactionString = "none";
//        switch (tabId) {
//            case R.id.home_tab:
//                mFragment = new HomeFragement();
//                break;
//            case R.id.profile_tab:
//                mFragment = new ProfileFragment();
//                break;
//            case R.id.noti_tab:
//                mFragment = new NotificationFragment();
//                break;
//            case R.id.book_tab:
//                mFragment = new BookFragement();
//                break;
//            case R.id.checkout_tab:
//                mFragment = new CheckOutFragment();
//                break;
//        }
//
//        getSupportFragmentManager().
//                beginTransaction()
//                .replace(R.id.frag_holder_main, mFragment, transactionString)
//                .addToBackStack(transactionString)
//                .commit();
//
//
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d(TAG, "onErrorResponse: Failed");
    }

    @Override
    public void onResponse(JSONObject response) {
        try {

//            hideProgresDialog();
            String amount = response.getString(APIUtils.AMOUNT_EARNED);
//            String bookCount = response.getString(APIUtils.BOOK_COUNT);
//            String orderCount = response.getString(APIUtils.ORDERS_COUNT);
            toggleAmountView(amount);
        } catch (Exception e) {
            Log.d(TAG, "onResponse: " + e.getLocalizedMessage());
        }
    }

    private void toggleAmountView(String amount) {
        if (mAmountLoadingRoot != null && mAmountLoadingRoot.isShown()){
            mAmountLoadingRoot.setVisibility(View.INVISIBLE);
            mAmountRoot.setVisibility(View.VISIBLE);
            mAmount.setText(String.format("%s %s", getString(R.string.rs), amount));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.earning_root:
                startActivity(new Intent(this,EarningsActivity.class));
                finish();

                break;
            case R.id.books_text_root:
                startActivity(new Intent(this,BooksActivity.class));
                finish();
                break;
            case R.id.noti_image:
                startActivity(new Intent(this,NotifcationsActivity.class));
                finish();
                break;
            case R.id.profile_image:
                startActivity(new Intent(this,ProfileActivity.class));
                finish();
                break;

        }
    }
}
