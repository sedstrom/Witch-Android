# Zipper

An attempt at making an easy to use view-model-binding framework for Android. (Yeah I know... the name needs to change.)

### Basic concept
Represent a views state with a view model. 

### How to use:
Define view model
```java
public class MyViewModel {

  @BindTextView(id = R.id.text_view_title)
  public String title;

  @BindTextView(id = R.id.text_view_subtitle)
  public String subTitle;

  ...

}
```
Bind view model to view
```java
MyViewModel model = new MyViewModel("The title", "The sub-title"));
Zipper.bind(model, activity); // Binds to anything that contains the views defined in view model.
```
### ViewHolders built in
A view model will have its own view holder which eliminates the need for defining view holders in adapters:

```java
   @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // An empty view holder just containing the root view.
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new SimpleViewHolder(inflater.inflate(R.layout.recycler_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        binding = Zipper.bind(items.get(position), holder.itemView);
    }
```

### Supported annotations

Direct view binding
```java
@BindToTextView
@BindToEditText
@BindToCompoundButton
@BindToImageView
@BindToRecyclerView
@BindToViewPager
// More to come!
```

Bind to not yet supported views
```java
@BindToView(id = R.id.my_view, class = UnknownView.class, set = "myProperty")
```

Custom bind function

```java
// Must have empty constructor
public class MyOnBind implements OnBind<TextView> {

  void onBind(TextView view, Object value) {
    view.setText("Prefix all texts: " + (String)value);
  }
}

public class MyViewModel {
  @BindTo(R.id.my_id) // View
  @OnBind(MyOnBind.class) // Bind action
  public String text;
}
```
### Mod

Add additional actions to view model with @Mod
```java
@Mod(ViewModel.class)
public class ViewModelMod {
  // Same field name as in view model
  public List<BindAction> title = Arrays.asList(new MyMod());
}

public class MyMod implements OnPostBindAction<TextView, String> {

    @Override
    public void onPostBind(TextView view, String text) {
        if(text == null) {
          view.setVisibility(View.INVISIBLE);
        } else {
          view.setVisibility(View.VISIBLE);
        }
    }
}

public class ViewModel {

  @BindTextView(id = R.id.text_view_title)
  public String title;
}
```

## Goals
TODO
