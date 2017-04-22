package com.example.witch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.Stack;

import se.snylt.witch.annotations.BindTo;
import se.snylt.witch.annotations.BindToView;
import se.snylt.witch.viewbinder.Witch;
import se.snylt.witch.viewbinder.bindaction.Binder;
import se.snylt.witch.viewbinder.bindaction.SyncOnBind;
import se.snylt.witch.viewbinder.bindaction.ValueBinder;

public class MainActivity extends AppCompatActivity implements OnExampleFragmentSelected {

    // Fragments back stack
    Stack<ViewModel> models = new Stack<>();

    private int transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.main_activity_toolbar));
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Initial fragment
        addFragment(Fragment.instantiate(this, ExampleListFragment.class.getName()), "Pick an example", false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addFragment(Fragment fragment, String title, boolean displayHomeAsUp) {
        models.add(new ViewModel(fragment, displayHomeAsUp, title));
        transaction = FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
        Witch.spellBind(models.peek(), this);
    }

    @Override
    public void onExampleFragmentSelected(String fragment, String title) {
        addFragment(Fragment.instantiate(this, fragment), title, true);
    }

    @Override
    public void onBackPressed() {
        if(models.size() > 1) {
            models.pop();
            transaction = FragmentTransaction.TRANSIT_FRAGMENT_CLOSE;
            Witch.spellBind(models.peek(), this);
        } else {
            super.onBackPressed();
        }
    }

    class ViewModel {

        private final Fragment fragment;

        private final boolean displayHomeAsUp;

        @BindToView(id = R.id.main_activity_toolbar, view = Toolbar.class, set = "title")
        public final String title;

        ViewModel(Fragment fragment, boolean displayHomeAsUp, String title) {
            this.fragment = fragment;
            this.displayHomeAsUp = displayHomeAsUp;
            this.title = title;
        }

        @BindTo(R.id.main_activity_fragment_container)
        ValueBinder<View, Fragment> bindFragment() {
            return ValueBinder.create(fragment, Binder.create(new SyncOnBind<View, Fragment>() {
                @Override
                public void onBind(View view, Fragment fragment) {
                    getSupportFragmentManager().beginTransaction()
                            .setTransition(transaction)
                            .replace(view.getId(), fragment)
                            .commit();
                }
            }));
        }

        @BindTo(R.id.main_activity_toolbar)
        ValueBinder<View, Boolean> bindDisplayHomeAsUp(){
            return ValueBinder.create(displayHomeAsUp, Binder.create(new SyncOnBind<View, Boolean>() {
                @Override
                public void onBind(View view, Boolean enabled) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
                }
            }));
        }
    }
}
