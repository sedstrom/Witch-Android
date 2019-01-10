package com.example.witch.app.login;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginManager extends Observable {

    public interface LoginCallback {
        void success();
    }

    public enum LoginError {
        INVALID_EMAIL,
        EMPTY_EMAIL,
        EMPTY_PASSWORD
    }

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static List<LoginError> validate(String emailAddress, String password) {
        List<LoginError> errors = new ArrayList<>();
        if (emailAddress == null || emailAddress.isEmpty()) {
            errors.add(LoginError.EMPTY_EMAIL);
        }

        if (emailAddress != null && !isValidEmailAddress(emailAddress)) {
            errors.add(LoginError.INVALID_EMAIL);
        }

        if (password == null || password.isEmpty()) {
            errors.add(LoginError.EMPTY_PASSWORD);
        }
        return errors;
    }

    public static void login(final LoginCallback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.success();
            }
        }, 2000);
    }

    private static boolean isValidEmailAddress(String emailAddress) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailAddress);
        return matcher.find();
    }


}
