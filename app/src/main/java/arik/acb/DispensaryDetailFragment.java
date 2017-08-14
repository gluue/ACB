package arik.acb;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Craig on 7/27/2017.
 */

public class DispensaryDetailFragment extends Fragment implements OnMapReadyCallback {
    ImageView dispensaryImage1, dispensaryImage2, dispensaryImage3;
    ViewFlipper viewFlipper;
    Toolbar toolbar;
    AppBarLayout appBar;
    AppBarLayout.OnOffsetChangedListener mListener;
    RecyclerView productRecyclerView, reviewRecyclerlist;
    Context context;
    BottomNavigationView dispensaryProductMenu;
    SupportMapFragment mapFragment;
    GoogleMap gmap;
    LocationManager locationManager;
    FloatingActionButton fab;
    List<Review> reviewList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dispensary_detail_layout, container, false);

        toolbar = (Toolbar)rootView.findViewById(R.id.toolbar);
        appBar = (AppBarLayout)rootView.findViewById(R.id.appBarDispensary);
        context = rootView.getContext();
        dispensaryProductMenu = (BottomNavigationView)rootView.findViewById(R.id.dispensaryNavigationView);
        SuperVar.lastPageID = SuperVar.pageID;
        SuperVar.pageID = "dispensaryDetail";
        dispensaryImage1 = (ImageView)rootView.findViewById(R.id.imageViewDispensaryDetail1);
        dispensaryImage1.setBackground(SuperVar.targetDispensary.getImage());
        fab = (FloatingActionButton)rootView.findViewById(R.id.fabDispensary);
        reviewRecyclerlist = (RecyclerView)rootView.findViewById(R.id.recyclerViewDispensaryReview);
        productRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewDispensaryProductList);

        productRecyclerView.setNestedScrollingEnabled(false);
        reviewRecyclerlist.setNestedScrollingEnabled(false);

        reviewList = new ArrayList<>();
        Random random = new Random();

        for(int i = 0; i < 5; i++){
            User user = new User();
            user.setUserName("user " + Integer.toString(i));
            Review review = new Review();
            review.setUser(user);
            review.setDescription("SOME FUCKING DESCRIPTION OF SOME SHIT, I DONT KNOW ITS PROBABLY A GREAT DISPENSARY THOUGH....");
            review.setRating(random.nextInt(5)+1);
            reviewList.add(review);
        }

        reviewRecyclerlist.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        reviewRecyclerlist.setAdapter(new ReviewListAdapter(rootView.getContext(), reviewList, getFragmentManager()));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuperVar.requestMap = true;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_right);
                transaction.replace(R.id.frameLayoutMain, new DispensaryFragment()).commit();
            }
        });

        if(!SuperVar.mainNavigationView.getMenu().getItem(1).isChecked()){
            SuperVar.mainNavigationView.getMenu().getItem(1).setChecked(true);
        }

        dispensaryImage2 = (ImageView)rootView.findViewById(R.id.imageViewDispensaryDetail2);
        dispensaryImage3 = (ImageView)rootView.findViewById(R.id.imageViewDispensaryDetail3);

        dispensaryImage2.setBackground(getResources().getDrawable(R.drawable.medical_dispensary_cases));
        dispensaryImage3.setBackground(getResources().getDrawable(R.drawable.marijuana_dispensary));

        mapFragment = SupportMapFragment.newInstance();
        mapFragment.onCreate(savedInstanceState);
        mapFragment.getMapAsync(this);

        viewFlipper = (ViewFlipper) rootView.findViewById(R.id.viewFlipperDispensary);

        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(rootView.getContext(), android.R.anim.slide_in_left));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(rootView.getContext(), android.R.anim.slide_out_right));

        viewFlipper.setAutoStart(true);

        SuperVar.supportFragmentManager.beginTransaction().replace(R.id.frameLayoutViewFlipperDispensary, mapFragment).commit();


        productRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        productRecyclerView.setAdapter(new ProductListAdapter(rootView.getContext(), SuperVar.targetDispensary.getProductList(), getFragmentManager()));
        dispensaryProductMenu.setOnNavigationItemSelectedListener(productNavigationItemSelectedListener);



        mListener = new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset == toolbar.getHeight() - appBarLayout.getHeight()){
                    toolbar.setTitle(SuperVar.targetDispensary.getDispensaryName());
                    fab.hide();
                }else{
                    toolbar.setTitle("");
                    fab.show();
                }
            }
        };

        appBar.addOnOffsetChangedListener(mListener);



        return rootView;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener productNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.productMenuFlower:
                    SuperVar.productListType = "f";
                    productRecyclerView.setAdapter(new ProductListAdapter(context, SuperVar.targetDispensary.getProductList(), getFragmentManager()));
                    //getFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new ProductFragment()).commit();
                    return true;
                case R.id.productMenuConcentrate:
                    SuperVar.productListType = "c";
                    productRecyclerView.setAdapter(new ProductListAdapter(context, SuperVar.targetDispensary.getProductList(), getFragmentManager()));
                    //getFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new ProductFragment()).commit();
                    return true;
                case R.id.productMenuEdible:
                    SuperVar.productListType = "e";
                    productRecyclerView.setAdapter(new ProductListAdapter(context, SuperVar.targetDispensary.getProductList(), getFragmentManager()));
                    //getFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new ProductFragment()).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.dispensary_toolbar, menu);


        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onMapReady(GoogleMap map)
    {
        try{
            gmap = map;

            if(SuperVar.targetDispensary.getLatitude()!=0||SuperVar.targetDispensary.getLongitude()!=0){
                LatLng target = new LatLng(SuperVar.targetDispensary.getLatitude(), SuperVar.targetDispensary.getLongitude());
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(SuperVar.targetDispensary.getLatitude(), SuperVar.targetDispensary.getLongitude()))
                        .title(SuperVar.targetDispensary.getDispensaryName()));
                gmap.moveCamera(CameraUpdateFactory.newLatLng(target));  //CATCH A NULL POINTER HERE. LOCATION LISTENER FAILURE
                gmap.animateCamera(CameraUpdateFactory.zoomTo(11));
            }


            //gmap.setMyLocationEnabled(true);
            //enabling location my location enabled eats up data

        }catch (SecurityException sec){
            System.out.println("SECURITY EXCEPTION");
        }
    }

    public class LocationListenerGPS implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
            CameraPosition cameraPosition = new CameraPosition.Builder().target(current).zoom(15).bearing(0).tilt(45).build();
            try{
                gmap.moveCamera(CameraUpdateFactory.newLatLng(current));  //CATCH A NULL POINTER HERE. LOCATION LISTENER FAILURE
                gmap.animateCamera(CameraUpdateFactory.zoomTo(11));
            }catch (NullPointerException nP){
                nP.printStackTrace();
            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    public void mapAroundUser(){
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        try{
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListenerGPS(), null);

        }catch (SecurityException sec){
            sec.printStackTrace();
        }
    }


}
