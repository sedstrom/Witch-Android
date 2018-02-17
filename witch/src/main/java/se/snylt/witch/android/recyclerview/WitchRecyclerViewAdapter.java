package se.snylt.witch.android.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import se.snylt.witch.android.Witch;
import se.snylt.witch.annotations.Data;

/**
 * RecyclerView adapter that uses {@link Binder} for data binding.
 * Each item type should have its own binder.
 * i.e. item of type T should have a Binder<T> provided in constructor.
 *
 * @param <Item> data model for views.
 */
public class WitchRecyclerViewAdapter<Item> extends RecyclerView.Adapter<EmptyViewHolder> {

    private List<Item> items;

    private final List<Binder<? extends Item>> binders;

    public WitchRecyclerViewAdapter(List<Item> items, List<Binder<? extends Item>> binders) {
        this.items = items;
        this.binders = binders;
    }

    public WitchRecyclerViewAdapter(List<Binder<? extends Item>> binders) {
        this(null, binders);
    }

    @Override
    public int getItemViewType(int position) {
        return getBinder(items.get(position)).getLayoutId();
    }

    @Override
    @NonNull
    public EmptyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new EmptyViewHolder(inflater.inflate(viewType, viewGroup, false));
    }

    private Binder<Item> getBinder(Item item) {
        for(Binder<? extends Item> binder: binders) {
            if(binder.bindsItem(item)) {
                return (Binder<Item>)binder;
            }
        }
        String itemType = item.getClass().getSimpleName();
        throw new IllegalArgumentException("No binder for found for " + itemType + ". Make sure binder for " + itemType
                + " is provided to adapter");
    }

    @Override
    public void onBindViewHolder(@NonNull EmptyViewHolder emptyViewHolder, int position) {
        Item item = items.get(position);
        Witch.bind(getBinder(item).take(item), emptyViewHolder.itemView);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        doNotifyDataSetChanged();
    }

    // Test
    protected void doNotifyDataSetChanged(){
        notifyDataSetChanged();
    }

    /**
     * Builder for {@link WitchRecyclerViewAdapter}
     * @param <Item> item type for adapter.
     */
    public static final class Builder<Item> {

        private final List<Item> items;

        private List<WitchRecyclerViewAdapter.Binder<? extends Item>> binders = new ArrayList<>();

        public Builder(List<Item> items) {
            this.items = items;
        }

        public Builder(){
            this(null);
        }

        public Builder<Item> binder(WitchRecyclerViewAdapter.Binder<? extends Item> binder) {
            this.binders.add(binder);
            return this;
        }

        public final WitchRecyclerViewAdapter<Item> build() {
            return new WitchRecyclerViewAdapter<>(items, binders);
        }

    }

    /**
     * Base class for RecyclerView binder.
     * @param <Item> item to be bound to view.
     */
    public static class Binder<Item> {

        /**
         * Class for matching adapter items with binder
         */
        private final Class bindsClass;

        /**
         * Item to be bound. This is a live object that will be updated prior to each
         * binding with this binder. Updated in {@link #take(Object)}.
         */
        @Data
        protected Item item;

        /**
         * Layout resource id for view creation.
         */
        private final int layoutId;

        /**
         *
         * Passing null as {@param bindsClass} will always return false from {@link #bindsItem(Object)}.
         *
         * @param layoutId id for view inflation
         * @param bindsClass class for matching adapter items with binder. See {@link #bindsItem(Object)}.
         */
        protected Binder(int layoutId, Class bindsClass) {
            this.bindsClass = bindsClass;
            this.layoutId = layoutId;
        }

        /**
         *
         * Convenience for passing null as {@param bindsClass} to {@link #Binder(int, Class)}.
         *
         */
        protected Binder(int layoutId) {
            this(layoutId, null);
        }

        /**
         * Updates item to be bound.
         * @param item item to be bound
         * @return Binder ready to bind {@param item}
         */
        Binder take(Object item) {
            this.item = (Item)item;
            return this;
        }

        /**
         * Layout id for view to be inflated.
         * @return layout id.
         */
        int getLayoutId() {
            return layoutId;
        }

        /**
         * Check if binder binds {@param item}. Default behaviour checks if {@param item} is
         * assignable from {@link #bindsClass}
         *
         * If binder binds item, item will be provided
         * in {@link #take(Object)}
         *
         * @param item to be bound in adapter.
         * @return true if binder binds item, otherwise false;
         */
        boolean bindsItem(Object item) {
            return bindsClass != null && bindsClass.isAssignableFrom(item.getClass());
        }
    }
}
