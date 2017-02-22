package com.example.zipper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;

import java.util.Stack;

import se.snylt.zipper.annotations.Mod;
import se.snylt.zipper.viewbinder.Zipper;
import se.snylt.zipper.viewbinder.bindaction.BindAction;
import se.snylt.zipper.viewbinder.bindaction.OnBindAction;

@Mod(MainViewModel.class)
public class MainActivity extends AppCompatActivity implements OnExampleFragmentSelected {

    // View model back stack
    Stack<MainViewModel> models = new Stack<>();

    public BindAction[] fragment = new BindAction[] {new OnBindAction<ViewGroup, Fragment>() {
        @Override
        public void onBind(ViewGroup view, Fragment fragment) {
            getSupportFragmentManager().beginTransaction()
                    .setTransition(transaction)
                    .replace(view.getId(), fragment)
                    .commit();
        }
    }};

    public BindAction[] displayHomeAsUp = new BindAction[] {new OnBindAction<Toolbar, Boolean>() {
        @Override
        public void onBind(Toolbar view, Boolean enabled) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
        }
    }};

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
        models.add(new MainViewModel(fragment, displayHomeAsUp, title));
        transaction = FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
        Zipper.bind(models.peek(), this, this);
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
            Zipper.bind(models.peek(), this, this);
        } else {
            super.onBackPressed();
        }
    }
}
