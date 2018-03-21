package com.example.witch.app.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.witch.R;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindData;
import se.snylt.witch.annotations.BindWhen;
import se.snylt.witch.annotations.Data;
import se.snylt.witch.viewbinder.Witch;
import se.snylt.witch.viewbinder.recyclerview.RecyclerViewBinderAdapter;

import static android.support.v7.widget.RecyclerView.VERTICAL;

public class ListActivity extends AppCompatActivity implements Observer {

    private ListViewManager listViewManager = new ListViewManager();

    // Bind
    @Override
    public void update(Observable o, Object arg) {
        bind();
    }

    private void bind() {
        Witch.bind(this, this);
    }

    private ListViewManager.ListState state() {
        return listViewManager.getState();
    }

    // Activity stuff
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        listViewManager.addObserver(this);
        listViewManager.update();
    }

    @Override
    protected void onStop() {
        super.onStop();
        listViewManager.deleteObserver(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_refresh) {
            listViewManager.update();
            return true;
        }
        return false;
    }

    // Bindings
    @Bind(id = R.id.toolbar) @BindWhen(BindWhen.ONCE)
    void bindToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
    }

    @BindData(id = R.id.progress, view = View.class, set = "visibility")
    int progressVisibility() {
        return state().isLoading && state().data.isEmpty() ? View.VISIBLE : View.GONE;
    }

    @BindData(id = R.id.small_progress, view = View.class, set = "visibility")
    int smallProgressVisibility() {
        return state().isLoading && !state().data.isEmpty() ? View.VISIBLE : View.GONE;
    }

    @Bind(id = R.id.recycler_view) @BindWhen(BindWhen.ONCE)
    void setupList(RecyclerView recyclerView) {
        recyclerView.setAdapter(new RecyclerViewBinderAdapter.Builder<String>()
                .binder(new DataBinder())
                .build());

        recyclerView.setLayoutManager(new LinearLayoutManager(this, VERTICAL, false));
    }

    @Data @BindWhen(BindWhen.NOT_SAME)
    List<String> listData() {
        return state().data;
    }

    @Bind(id = R.id.recycler_view)
    void listData(RecyclerView recyclerView, List<String> data, List<String> historyData) {
        ((RecyclerViewBinderAdapter<String>)recyclerView.getAdapter()).setItems(data);
    }

    private class DataBinder extends RecyclerViewBinderAdapter.Binder<String> {

        private DataBinder() { super(R.layout.data_list_item); }

        @Bind(id = R.id.text)
        void text(TextView textView) {
            textView.setText(item);
        }

        @Override
        public boolean bindsItem(Object item) {
            return item instanceof String;
        }
    }

}
