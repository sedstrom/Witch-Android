package com.example.witch.login;

import com.example.witch.R;
import com.example.witch.utils.TextWatcherAdapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import se.snylt.witch.viewbinder.Witch;

public class LoginFragment extends Fragment {

    private final LoginViewModel model = new LoginViewModel("snylt", "password");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.login_fragment, container, false);

        // Track input change
        ((EditText)view.findViewById(R.id.login_fragment_password)).addTextChangedListener(new TextWatcherAdapter() {

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                model.setPassword(String.valueOf(charSequence));
            }

        });

        ((EditText)view.findViewById(R.id.login_fragment_username)).addTextChangedListener(new TextWatcherAdapter() {

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                model.setUsername(String.valueOf(charSequence));
            }

        });

        view.findViewById(R.id.login_fragment_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setLoggingIn(true);
                bind();

                Login.login(model.username(), model.password(), new Login.LoginListener(){

                    @Override
                    public void loginSuccessful() {
                        model.setIsError(false);
                        model.setLoggingIn(false);
                        model.setMessage("Logged in :)");
                        bind();
                    }

                    @Override
                    public void loginFailed() {
                        model.setIsError(true);
                        model.setLoggingIn(false);
                        model.setMessage("Something went wrong :(");
                        bind();
                    }
                });
            }
        });

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind();
    }

    private void bind(){
        if(isAdded()){
            Witch.bind(model, getView());
        }
    }
}
