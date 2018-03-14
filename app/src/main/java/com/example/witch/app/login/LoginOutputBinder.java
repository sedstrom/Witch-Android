package com.example.witch.app.login;

import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.witch.R;
import com.example.witch.app.witch.WitchBinder;

import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindData;
import se.snylt.witch.annotations.BindNull;
import se.snylt.witch.annotations.Data;
import se.snylt.witchcore.viewfinder.ViewFinder;

class LoginOutputBinder extends WitchBinder {

    private LoginState loginState;

    LoginOutputBinder(ViewFinder viewFinder) {
        super(viewFinder);
    }

    void setLoginState(LoginState loginState) {
        this.loginState = loginState; bind();
    }

    @Data
    String emailTitle() {
        if (loginState.getErrors().contains(LoginState.Error.EMPTY_EMAIL)) {
            return "Email address";
        } else if (loginState.getErrors().contains(LoginState.Error.INVALID_EMAIL)) {
            return "person@company.ab";
        }
        return "Perfect!";
    }

    @Bind(id = R.id.emailTitle)
    void emailTitle(TextView textView, String title) {
        textView.setTranslationY(textView.getHeight()*0.75f);
        textView.setAlpha(0);
        textView.animate()
                .translationY(0)
                .alpha(1)
                .setInterpolator(new OvershootInterpolator(2f)).setDuration(500).start();
        textView.setText(title);
    }

    @BindData(id = R.id.emailAddress, view = View.class, set = "enabled")
    boolean emailEnabled() {
        return !loginState.isLoggingIn();
    }

    @BindData(id = R.id.password, view = View.class, set = "enabled")
    boolean passwordEnabled() {
        return !loginState.isLoggingIn();
    }

    @BindData(id = R.id.loginbutton, view = View.class, set = "enabled")
    boolean loginButtonEnabled() {
        return loginState.getErrors().isEmpty() && !loginState.isLoggingIn();
    }

    @BindData(id = R.id.progress, view = View.class, set = "visibility")
    int progressVisibility() {
        return loginState.isLoggingIn() ? View.VISIBLE : View.GONE;
    }

    @Data
    String loggedInMessage() {
        return loginState.isLoggedIn() ? "Logged in! :)" : null;
    }

    @Bind(id = R.id.container)
    void loggedInMessage(View view, String message) {
        Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();
    }
}