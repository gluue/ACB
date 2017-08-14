package arik.acb;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jake on 8/13/2017.
 */

public class SplashScreenFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.splash_screen_layout, container, false);

        //DO STUFF....

        //YEAH
        boolean flag = false;

        long startTime = System.currentTimeMillis();

        while(!flag){
            if(startTime - System.currentTimeMillis() > 5000){
                flag = true;
            }
        }
        //ADDED THIS SHIT

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.animator.fade_in, R.animator.fade_out);
        transaction.replace(R.id.frameLayoutMain, new ProductFragment()).commit();


        return rootView;
    }
}
