package arik.acb;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jake on 7/26/2017.
 */

public class ProductFragment extends Fragment {

    RecyclerView recyclerView;
    BottomNavigationView productNavigationView;
    List<Product> list;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_fragment_layout, container, false);
        context = rootView.getContext();
        SuperVar.lastPageID = SuperVar.pageID;
        SuperVar.pageID = "productList";
        list = new ArrayList<>();
        SuperVar.mainNavigationView.getMenu().getItem(0).setChecked(true);

        for(Dispensary d : SuperVar.dispensaryList){
            for(int i = 0; i < d.getProductList().size(); i++){
                list.add(d.getProductList().get(i));
            }
        }

        productNavigationView = (BottomNavigationView)rootView.findViewById(R.id.productNavigationBar);
        productNavigationView.setOnNavigationItemSelectedListener(productNavigationItemSelectedListener);

        switch(SuperVar.productListType){
            case "f":
                productNavigationView.getMenu().getItem(0).setChecked(true);
                break;
            case "c":
                productNavigationView.getMenu().getItem(1).setChecked(true);
                break;
            case "e":
                productNavigationView.getMenu().getItem(2).setChecked(true);
                break;
        }

        recyclerView = (RecyclerView)rootView.findViewById(R.id.rvProductList);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        recyclerView.setAdapter(new ProductListAdapter(rootView.getContext(), list, getFragmentManager()));

        return rootView;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener productNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.productMenuFlower:
                    SuperVar.productListType = "f";
                    recyclerView.setAdapter(new ProductListAdapter(context, list, getFragmentManager()));
                    //getFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new ProductFragment()).commit();
                    return true;
                case R.id.productMenuConcentrate:
                    SuperVar.productListType = "c";
                    recyclerView.setAdapter(new ProductListAdapter(context, list, getFragmentManager()));
                    //getFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new ProductFragment()).commit();
                    return true;
                case R.id.productMenuEdible:
                    SuperVar.productListType = "e";
                    recyclerView.setAdapter(new ProductListAdapter(context, list, getFragmentManager()));
                    //getFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new ProductFragment()).commit();
                    return true;
            }
            return false;
        }

    };
}
