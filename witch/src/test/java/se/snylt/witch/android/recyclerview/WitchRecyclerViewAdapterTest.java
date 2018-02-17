package se.snylt.witch.android.recyclerview;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import android.app.Service;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import se.snylt.witch.android.WitchTestUtils;
import se.snylt.witchcore.WitchCore;
import se.snylt.witchcore.viewfinder.TagContainer;
import se.snylt.witchcore.viewfinder.ViewFinder;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WitchRecyclerViewAdapterTest {

    private WitchRecyclerViewAdapter<Object> adapter;

    private final static int EXISTING_BINDER_ITEM_POSITION = 0;

    private final static int NON_EXISTING_BINDER_ITEM_POSITION = 1;

    private String itemWithBinder = "String data";

    private Object itemWithoutBinder = new Object();;

    private List<Object> items = new ArrayList<>();

    private ItemBinder itemBinder = spy(new ItemBinder(0));

    EmptyViewHolder emptyViewHolder;

    @Mock
    WitchCore core;

    TagContainer tagContainer = new TagContainer();

    @Mock
    LayoutInflater inflater;

    @Mock View itemView;

    @Mock
    ViewGroup parent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WitchTestUtils.testInit(core);
        items.add(EXISTING_BINDER_ITEM_POSITION, itemWithBinder);
        items.add(NON_EXISTING_BINDER_ITEM_POSITION, itemWithoutBinder);

        // Layout inflater stuff
        when(itemView.getTag(anyInt())).thenReturn(tagContainer);
        emptyViewHolder = new EmptyViewHolder(itemView);

        when(inflater.inflate(anyInt(), any(ViewGroup.class), anyBoolean())).thenReturn(itemView);
        Context context = mock(Context.class);
        when(context.getSystemService(eq(Service.LAYOUT_INFLATER_SERVICE))).thenReturn(inflater);
        when(parent.getContext()).thenReturn(context);


        List<WitchRecyclerViewAdapter.Binder<?>> binders = new ArrayList<>();
        binders.add(itemBinder);
        adapter = new TestWitchRecyclerViewAdapter<>(items, binders);
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
    public void binder_bindsItem_WithSubClass_Should_ReturnTrue() {
        WitchRecyclerViewAdapter.Binder b
                = new WitchRecyclerViewAdapter.Binder<Number>(0, Number.class);
        assertTrue(b.bindsItem(1f));
    }

    @Test
    public void binder_bindsItem_WithSameClass_Should_ReturnTrue() {
        WitchRecyclerViewAdapter.Binder b
                = new WitchRecyclerViewAdapter.Binder<String>(0, String.class);
        assertTrue(b.bindsItem("Bind me?"));
    }

    @Test
    public void binder_bindsItem_WithNonAssignableClass_Should_ReturnFalse() {
        WitchRecyclerViewAdapter.Binder b
                = new WitchRecyclerViewAdapter.Binder<Number>(0, Number.class);
        assertFalse(b.bindsItem("Not a number"));
    }

    @Test
    public void binder_bindsItem_WithNullClass_Should_ReturnFalse() {
        WitchRecyclerViewAdapter.Binder b
                = new WitchRecyclerViewAdapter.Binder<Object>(0, null);
        assertFalse(b.bindsItem(new Object()));
    }

    @Test
    public void getItemCount() {
        assertSame(items.size(), adapter.getItemCount());
    }

    class ItemBinder extends WitchRecyclerViewAdapter.Binder<String> {

        private ItemBinder(int layoutId) {
            super(layoutId, String.class);
        }
    }

    class TestWitchRecyclerViewAdapter<T> extends WitchRecyclerViewAdapter<T> {

        public TestWitchRecyclerViewAdapter(List<T> items, List<Binder<? extends T>> binders) {
            super(items, binders);
        }

        @Override
        protected void doNotifyDataSetChanged() {
            // Avoid call to notifyDataSetChanged()
        }
    }
}