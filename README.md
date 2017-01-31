# Zipper

An attempt at making an easy to use view-model-binding framework for Android. (Yeah I know... the name needs to change.)

### Basic concept
Represent a views state with a view model. 

### How to use:
Define view model.
```java
public class MyViewModel {

  @BindTextView(id = R.id.text_view_title)
  public String title;

  @BindTextView(id = R.id.text_view_subtitle)
  public String subTitle;

  ...

}
```
Bind to view.
```java
MyViewModel model = new MyViewModel("The title", "The sub-title"));
Zipper.bind(model, activity); // Binds to anything that contains the views defined in view model.
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


## Goals
TODO
