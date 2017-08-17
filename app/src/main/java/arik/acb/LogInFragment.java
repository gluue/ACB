package arik.acb;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.SupportErrorDialogFragment;

/**
 * Created by Jake on 8/13/2017.
 */

public class LogInFragment extends Fragment{

    Button logInButton;
    EditText userField, passField;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.log_in_layout, container, false);

        logInButton = (Button)rootView.findViewById(R.id.buttonLogIn);
        userField = (EditText)rootView.findViewById(R.id.editTextUserName);
        passField = (EditText)rootView.findViewById(R.id.editTextPassword);

        //GET USER PASS
        //FOR TEST We'LL USE a FIXED USER LIST

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(User u : SuperVar.userList){
                    if(userField.getText().toString().equals(u.getUserName())){
                        if(passField.getText().toString().equals(u.getUserPassword())){
                            u.setUserAvater(getResources().getDrawable(R.drawable.avatar_default));
                            SuperVar.currentUser = u;
                            getFragmentManager().beginTransaction().replace(R.id.frameLayoutMain, new SplashScreenFragment()).commit();
                        }else{
                            Toast.makeText(getActivity().getApplicationContext(), "Incorecct Username/Password", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getActivity().getApplicationContext(), "Incorecct Username/Password", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        return rootView;
    }
}
