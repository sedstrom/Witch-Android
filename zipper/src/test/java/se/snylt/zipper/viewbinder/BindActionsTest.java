package se.snylt.zipper.viewbinder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import se.snylt.zipper.viewbinder.bindaction.BindAction;
import se.snylt.zipper.viewbinder.bindaction.OnBindAction;
import se.snylt.zipper.viewbinder.bindaction.OnPostBindAction;
import se.snylt.zipper.viewbinder.bindaction.OnPreBindAction;

import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BindActionsTest {

    @Mock
    OnPreBindAction onPreBindAction;

    @Mock
    OnPostBindAction onPostBindAction;

    @Mock
    OnBindAction onBindAction;

    TestMultipleBindActions multipleBindActions = new TestMultipleBindActions();

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void newInstance(){
        new BindActions();
    }

    @Test
    public void newInstance_OnBindActionParam_Should_ExistInOnBindActions(){
        BindActions actions = new BindActions(onBindAction);

        assertTrue(actions.onBindActions.contains(onBindAction));
    }

    @Test
    public void newInstance_OnBindActionParamList_Should_ExistInOnBindActions(){
        BindActions actions = new BindActions(Arrays.asList((BindAction) onBindAction));

        assertTrue(actions.onBindActions.contains(onBindAction));
    }

    @Test
    public void newInstance_MultipleDifferentBindActions_Should_ExistInRespectiveList(){
        // When
        BindActions actions = new BindActions(onPreBindAction, onBindAction, onPostBindAction);

        // Then
        assertTrue(actions.onPreBindActions.contains(onPreBindAction));
        assertTrue(actions.onBindActions.contains(onBindAction));
        assertTrue(actions.onPostBindActions.contains(onPostBindAction));
    }

    @Test
    public void newInstance_MultipleBindActionsOneParam_Should_ExistInRespectiveList(){
        // When
        BindActions actions = new BindActions(multipleBindActions);

        // Then
        assertTrue(actions.onPreBindActions.contains(multipleBindActions));
        assertTrue(actions.onBindActions.contains(multipleBindActions));
        assertTrue(actions.onPostBindActions.contains(multipleBindActions));
    }

    @Test
    public void addAll_OnBindActionParam_Should_ExistInOnBindActions(){
        BindActions actions = new BindActions();
        actions.addAll(Arrays.asList(onBindAction));

        assertTrue(actions.onBindActions.contains(onBindAction));
    }

    @Test
    public void addAll_MultipleDifferentBindActions_Should_ExistInRespectiveList(){
        // When
        BindActions actions = new BindActions();
        actions.addAll(Arrays.asList(onPreBindAction, onBindAction, onPostBindAction));

        // Then
        assertTrue(actions.onPreBindActions.contains(onPreBindAction));
        assertTrue(actions.onBindActions.contains(onBindAction));
        assertTrue(actions.onPostBindActions.contains(onPostBindAction));
    }

    @Test
    public void addAll_MultipleBindActionsOneParam_Should_ExistInRespectiveList(){
        // When
        BindActions actions = new BindActions();
        actions.addAll(Arrays.asList(multipleBindActions));

        // Then
        assertTrue(actions.onPreBindActions.contains(multipleBindActions));
        assertTrue(actions.onBindActions.contains(multipleBindActions));
        assertTrue(actions.onPostBindActions.contains(multipleBindActions));
    }

    @Test
    public void addAll_BindActions_With_OnBindAction_Should_ExistInOnBindActions(){
        BindActions actions = new BindActions();
        actions.addAll(new BindActions(onBindAction));

        assertTrue(actions.onBindActions.contains(onBindAction));
    }

    @Test
    public void addAll_BindActions_With_MultipleBindActions_Should_ExistInRespectiveList(){
        // When
        BindActions actions = new BindActions();
        actions.addAll(new BindActions(onPreBindAction, onBindAction, onPostBindAction));

        // Then
        assertTrue(actions.onPreBindActions.contains(onPreBindAction));
        assertTrue(actions.onBindActions.contains(onBindAction));
        assertTrue(actions.onPostBindActions.contains(onPostBindAction));
    }

    @Test
    public void addAll_BindActions_With_MultipleBindActionsOneParam_Should_ExistInRespectiveList(){
        // When
        BindActions actions = new BindActions();
        actions.addAll(new BindActions(multipleBindActions));

        // Then
        assertTrue(actions.onPreBindActions.contains(multipleBindActions));
        assertTrue(actions.onBindActions.contains(multipleBindActions));
        assertTrue(actions.onPostBindActions.contains(multipleBindActions));
    }

    @Test
    public void applyMods_Should_AddModsToBindActions_In_NewInstance(){
        // Mods
        OnPreBindAction onPreBindMod = mock(OnPreBindAction.class);
        OnBindAction onBindMod = mock(OnBindAction.class);
        OnPostBindAction onPostBindMod = mock(OnPostBindAction.class);

        Object[] mods = new Object[]{new Object()};
        ViewBinder viewBinder = mock(ViewBinder.class);
        when(viewBinder.getModActions(same(mods[0]))).thenReturn(new BindAction[]{onPreBindMod, onBindMod, onPostBindMod});

        BindActions actions = new BindActions(onPreBindAction, onBindAction, onPostBindAction);
        BindActions withMods = actions.applyMods(viewBinder, mods);

        // PreBind
        assertTrue(withMods.onPreBindActions.contains(onPreBindAction));
        assertTrue(withMods.onPreBindActions.contains(onPreBindMod));

        // OnBind
        assertTrue(withMods.onBindActions.contains(onBindAction));
        assertTrue(withMods.onBindActions.contains(onBindMod));

        // PostBind
        assertTrue(withMods.onPostBindActions.contains(onPostBindAction));
        assertTrue(withMods.onPostBindActions.contains(onPostBindMod));

        // New instance
        assertNotSame(withMods, actions);
    }

    @Test
    public void applyMods_EmptyMods_Should_Return_SameInstance(){
        // Mods
        Object[] emptyMods = new Object[0];
        ViewBinder viewBinder = mock(ViewBinder.class);

        BindActions actions = new BindActions(onPreBindAction, onBindAction, onPostBindAction);
        BindActions withMods = actions.applyMods(viewBinder, emptyMods);

        // PreBind
        assertTrue(withMods.onPreBindActions.contains(onPreBindAction));

        // OnBind
        assertTrue(withMods.onBindActions.contains(onBindAction));

        // PostBind
        assertTrue(withMods.onPostBindActions.contains(onPostBindAction));

        // New instance
        assertSame(withMods, actions);
    }

}