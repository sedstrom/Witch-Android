package se.snylt.witch.viewbinder.recyclerview;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import android.app.Service;
import android.support.v7.widget.RecyclerView;
import android.test.mock.MockContext;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import se.snylt.witch.viewbinder.Witch;
import se.snylt.witch.viewbinder.WitchCore;
import se.snylt.witch.viewbinder.viewfinder.ViewFinder;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecyclerViewBinderAdapterTest {

    private RecyclerViewBinderAdapter<Object> adapter;

    private final static int EXISTING_BINDER_ITEM_POSITION = 0;

    private final static int NON_EXISTING_BINDER_ITEM_POSITION = 1;

    private Object itemWithBinder = new Object();

    private String itemWithoutBinder = "No binder";

    private List<Object> items = new ArrayList<>();

    private ItemBinder itemBinder = spy(new ItemBinder(0));

    @Mock
    EmptyViewHolder emptyViewHolder;

    @Mock
    WitchCore core;

    private LayoutInflater inflater;

    private View itemView;

    private ViewGroup parent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Witch.witch(core);
        items.add(EXISTING_BINDER_ITEM_POSITION, itemWithBinder);
        items.add(NON_EXISTING_BINDER_ITEM_POSITION, itemWithoutBinder);

        // Layout inflater stuff
        parent = mock(ViewGroup.class);
        inflater = mock(LayoutInflater.class);
        itemView = mock(View.class);
        when(inflater.inflate(anyInt(), any(ViewGroup.class), anyBoolean())).thenReturn(itemView);
        MockContext context = mock(MockContext.class);
        when(context.getSystemService(eq(Service.LAYOUT_INFLATER_SERVICE))).thenReturn(inflater);
        when(parent.getContext()).thenReturn(context);

        adapter = new RecyclerViewBinderAdapter<>(items, itemBinder);
    }

    @Test
    public void onBindViewHolder_BinderExists_Should_InOrder_TakeItem_Bind() {
        InOrder inOrder = Mockito.inOrder(itemBinder, core);
        // When
        adapter.onBindViewHolder(emptyViewHolder, EXISTING_BINDER_ITEM_POSITION);

        // Then
        inOrder.verify(itemBinder).take(same(itemWithBinder));
        inOrder.verify(core).doBind(same(itemBinder), any(ViewFinder.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void onBindViewHolder_BinderDoesNotExists_Should_ThrowIllegalArgumentException() {
        adapter.onBindViewHolder(emptyViewHolder, NON_EXISTING_BINDER_ITEM_POSITION);
    }

    @Test
    public void onCreateViewHolder_Should_InflateLayoutFromBinder_ReturnEmptyViewHolder() {
        // When
        RecyclerView.ViewHolder vh = adapter.onCreateViewHolder(parent, 0);

        // Then
        verify(inflater).inflate(eq(itemBinder.getLayoutId()), same(parent), eq(false));
        assertEquals(EmptyViewHolder.class, vh.getClass());
        assertSame(itemView, vh.itemView);
    }

    @Test
    public void getItemViewType_Should_UseLayoutIdAsViewType() {
        // When
        int viewType = adapter.getItemViewType(EXISTING_BINDER_ITEM_POSITION);

        // Then
        assertSame(itemBinder.getLayoutId(), viewType);
    }

    @Test
    public void getItems() {
        assertSame(items, adapter.getItems());
    }

    @Test
    public void setItems() {
        List<Object> newItems = new ArrayList<>();
        adapter.setItems(newItems);
        assertSame(newItems, adapter.getItems());
    }

    @Test
    public void getItemCount() {
        assertSame(items.size(), adapter.getItemCount());
    }

    private class ItemBinder extends RecyclerViewBinderAdapter.Binder<Object> {

        private ItemBinder(int layoutId) {
            super(layoutId);
        }

        @Override
        public Class getItemType() {
            return Object.class;
        }
    }

}