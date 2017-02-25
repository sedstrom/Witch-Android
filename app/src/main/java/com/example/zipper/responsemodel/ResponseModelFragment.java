package com.example.zipper.responsemodel;

import com.example.zipper.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se.snylt.zipper.viewbinder.Zipper;

public class ResponseModelFragment extends Fragment {

    private EditText editText;

    Github github;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.response_model_fragment, container, false);


        github = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Github.class);

        editText = (EditText) view.findViewById(R.id.response_model_fragment_edit_text);
        view.findViewById(R.id.always_bind_fragment_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetch(editText.getText().toString());
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindStart();
    }

    private void fetch(final String user) {
        bindFetching();

        Call<ResponseViewModel> call = github.getUser(user);
        call.enqueue(new Callback<ResponseViewModel>() {
            @Override
            public void onResponse(Call<ResponseViewModel> call, Response<ResponseViewModel> response) {
                if(response.isSuccessful()) {
                    Zipper.bind(response.body(), getView());
                } else {
                    bindError(String.format("Could not find \"%s\"", user));
                }
            }

            @Override
            public void onFailure(Call<ResponseViewModel> call, Throwable t) {
                bindError(t.getMessage());
            }
        });
    }

    private void bindStart() {
        Zipper.bind(ResponseViewModel.idle(), getView());
    }

    private void bindFetching() {
        Zipper.bind(ResponseViewModel.progress(), getView());
    }

    private void bindError(String error) {
        Zipper.bind(ResponseViewModel.error(error), getView());
    }
}
