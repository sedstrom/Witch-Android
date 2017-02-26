package se.snylt.zipper.viewbinder;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import android.view.View;

import se.snylt.zipper.viewbinder.bindaction.OnBindAction;
import se.snylt.zipper.viewbinder.bindaction.OnPostBindAction;
import se.snylt.zipper.viewbinder.bindaction.OnPreBindAction;
import se.snylt.zipper.viewbinder.bindaction.PreBindDone;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class BindActionsRunnerTest {

    @Mock
    View view;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void bind_EmptyActions_Should_Not_Break() {
        BindActionsRunner.runner().bind(new BindActions(), view, "123");
    }

    @Test
    public void bind_With_One_OnBindAction_Should_BindWithViewAndValue() {
        // When
        OnBindAction action = mock(OnBindAction.class);
        BindActionsRunner.runner().bind(new BindActions(action), view, "123");

        // Then
        verify(action).onBind(same(view), eq("123"));
    }

    @Test
    public void bind_With_One_OnBindAction_One_PostBindAction_Should_InOrder_Bind_OnBindAction_OnPostBindAction() {
        // When
        OnBindAction onBindAction = mock(OnBindAction.class);
        OnPostBindAction onPostBindAction = mock(OnPostBindAction.class);
        InOrder inOrder = Mockito.inOrder(onBindAction, onPostBindAction);
        BindActionsRunner.runner().bind(new BindActions(onBindAction, onPostBindAction), view, "123");

        // Then
        inOrder.verify(onBindAction).onBind(same(view), eq("123"));
        inOrder.verify(onPostBindAction).onPostBind(same(view), eq("123"));
    }

    @Test
    public void bind_With_OnePreBindAction_NeverCallsPreBindDone_One_OnBindAction_One_PostBindAction_Should_Bind_OnPreBind_Never_Bind_OnBindAction_OnPostBindAction() {
        // When
        OnPreBindAction onPreBindAction = spy(new TestOnPreBindAction());
        OnBindAction onBindAction = mock(OnBindAction.class);
        OnPostBindAction onPostBindAction = mock(OnPostBindAction.class);
        InOrder inOrder = Mockito.inOrder(onBindAction, onPostBindAction, onPreBindAction);
        BindActionsRunner.runner().bind(new BindActions(onPreBindAction, onBindAction, onPostBindAction), view, "123");

        // Then
        inOrder.verify(onPreBindAction).onPreBind(same(view), eq("123"), any(PreBindDone.class));
        inOrder.verify(onBindAction, never()).onBind(same(view), eq("123"));
        inOrder.verify(onPostBindAction, never()).onPostBind(same(view), eq("123"));
    }

    @Test
    public void bind_With_OnePreBindAction_CallsPreBindDone_One_OnBindAction_One_PostBindAction_Should_Bind_OnPreBind_OnBindAction_OnPostBindAction() {
        // When
        TestOnPreBindAction onPreBindAction = spy(new TestOnPreBindAction());
        OnBindAction onBindAction = mock(OnBindAction.class);
        OnPostBindAction onPostBindAction = mock(OnPostBindAction.class);
        InOrder inOrder = Mockito.inOrder(onBindAction, onPostBindAction, onPreBindAction);
        BindActionsRunner.runner().bind(new BindActions(onPreBindAction, onBindAction, onPostBindAction), view, "123");
        onPreBindAction.preBindDone.done();

        // Then
        inOrder.verify(onPreBindAction).onPreBind(same(view), eq("123"), any(PreBindDone.class));
        inOrder.verify(onBindAction).onBind(same(view), eq("123"));
        inOrder.verify(onPostBindAction).onPostBind(same(view), eq("123"));
    }

    @Test
    public void bind_With_Two_PreBindAction_OneCallsPreBindDone_One_OnBindAction_One_PostBindAction_Should_Bind_OnPreBind_Never_Bind_OnBindAction_OnPostBindAction() {
        // When
        TestOnPreBindAction onPreBindActionOne = spy(new TestOnPreBindAction());
        TestOnPreBindAction onPreBindActionTwo = spy(new TestOnPreBindAction());
        OnBindAction onBindAction = mock(OnBindAction.class);
        OnPostBindAction onPostBindAction = mock(OnPostBindAction.class);
        InOrder inOrder = Mockito.inOrder(onBindAction, onPostBindAction, onPreBindActionOne, onPreBindActionTwo);
        BindActionsRunner.runner().bind(new BindActions(onPreBindActionOne, onPreBindActionTwo, onBindAction, onPostBindAction), view, "123");
        // Only one reports done
        onPreBindActionOne.preBindDone.done();

        // Then
        inOrder.verify(onPreBindActionOne).onPreBind(same(view), eq("123"), any(PreBindDone.class));
        inOrder.verify(onPreBindActionTwo).onPreBind(same(view), eq("123"), any(PreBindDone.class));
        inOrder.verify(onBindAction, never()).onBind(same(view), eq("123"));
        inOrder.verify(onPostBindAction, never()).onPostBind(same(view), eq("123"));
    }

    @Test
    public void bind_With_Two_PreBindAction_BothCallsPreBindDone_One_OnBindAction_One_PostBindAction_Should_Bind_OnPreBind_OnBindAction_OnPostBindAction() {
        // When
        TestOnPreBindAction onPreBindActionOne = spy(new TestOnPreBindAction());
        TestOnPreBindAction onPreBindActionTwo = spy(new TestOnPreBindAction());
        OnBindAction onBindAction = mock(OnBindAction.class);
        OnPostBindAction onPostBindAction = mock(OnPostBindAction.class);
        InOrder inOrder = Mockito.inOrder(onBindAction, onPostBindAction, onPreBindActionOne, onPreBindActionTwo);
        BindActionsRunner.runner().bind(new BindActions(onPreBindActionOne, onPreBindActionTwo, onBindAction, onPostBindAction), view, "123");
        // Only one reports done
        onPreBindActionOne.preBindDone.done();
        onPreBindActionTwo.preBindDone.done();

        // Then
        inOrder.verify(onPreBindActionOne).onPreBind(same(view), eq("123"), any(PreBindDone.class));
        inOrder.verify(onPreBindActionTwo).onPreBind(same(view), eq("123"), any(PreBindDone.class));
        inOrder.verify(onBindAction).onBind(same(view), eq("123"));
        inOrder.verify(onPostBindAction).onPostBind(same(view), eq("123"));
    }

    @Test
    public void bind_With_PreBindAction_CallsPreBindDoneInBindCall_One_OnBindAction_One_PostBindAction_Should_Bind_OnPreBind_OnBindAction_OnPostBindAction() {
        // When
        TestOnPreBindAction onPreBindAction = spy(new TestOnPreBindAction(true)); // instant call to done
        OnBindAction onBindAction = mock(OnBindAction.class);
        OnPostBindAction onPostBindAction = mock(OnPostBindAction.class);
        InOrder inOrder = Mockito.inOrder(onBindAction, onPostBindAction, onPreBindAction);
        BindActionsRunner.runner().bind(new BindActions(onPreBindAction, onBindAction, onPostBindAction), view, "123");

        // Then
        inOrder.verify(onPreBindAction).onPreBind(same(view), eq("123"), any(PreBindDone.class));
        inOrder.verify(onBindAction).onBind(same(view), eq("123"));
        inOrder.verify(onPostBindAction).onPostBind(same(view), eq("123"));
    }

    @Test
    public void isNewValue_Null_NoHistory_Should_ReturnTrue() {
        assertTrue(BindActionsRunner.runner().isNewValue(null, BindActionsRunner.NO_HISTORY));
    }

    @Test
    public void isNewValue_Null_Null_Should_ReturnFalse() {
        assertFalse(BindActionsRunner.runner().isNewValue(null, null));
    }

    @Test
    public void isNewValue_NotNull_Null_Should_ReturnTrue() {
        assertTrue(BindActionsRunner.runner().isNewValue("123", null));
    }

    @Test
    public void isNewValue_Null_NotNull_Should_ReturnTrue() {
        assertTrue(BindActionsRunner.runner().isNewValue(null,"123"));
    }

    @Test
    public void isNewValue_NonEqualValues_Should_ReturnTrue() {
        assertTrue(BindActionsRunner.runner().isNewValue("123", "456"));
    }

    @Test
    public void isNewValue_EqualValues_DifferentValues_Should_ReturnFalse() {
        assertFalse(BindActionsRunner.runner().isNewValue("123", "123"));
    }

    private class TestOnPreBindAction implements OnPreBindAction {

        public  PreBindDone preBindDone;

        private boolean callDoneOnBind;

        private TestOnPreBindAction(boolean callDoneOnBinds){
            this.callDoneOnBind = callDoneOnBinds;
        }

        private TestOnPreBindAction(){
            this(false);
        }

        @Override
        public void onPreBind(View view, Object o, PreBindDone preBindDone) {
            this.preBindDone = preBindDone;
            if(callDoneOnBind) {
                preBindDone.done();
            }
        }
    }
}