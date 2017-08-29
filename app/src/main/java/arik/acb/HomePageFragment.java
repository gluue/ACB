package arik.acb;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Craig on 8/28/2017.
 */

public class HomePageFragment extends Fragment {
   RecyclerView homeList;
    Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_page_layout, container, false);

        context = rootView.getContext();

        homeList = (RecyclerView)rootView.findViewById(R.id.recyclerViewHome);
        homeList.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        homeList.setAdapter(new HomeListAdapter(context, SuperVar.historyList));

        return rootView;
    }
}
