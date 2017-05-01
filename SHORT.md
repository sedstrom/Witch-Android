## Witch Android

Witch is a flexible view-data binding library for Android.

* Bind data to views with <b>@annotations</b>.
* Compose functional bind chains for advanced bindings.
* View holders built in.

### Simple data binding

```java
// Data for view
@BindTextView(id = R.id.title_tv)
public String title;

// Bind data to view
Witch.bind(this, activity);
 ```

Bind any property to any view type
```java
// Set text color
@BindToTextView(id=R.id.title_tv, set=“textColor”)
Integer color = R.color.BLACK;

// Set address on AddressView.
@BindToView(id=R.id.custom, class=AddressView.class, set="address")
String address;
```

Use methods for dynamic values
```java
@BindToTextView(id=R.id.title_tv)
public String name() {
  return firstName + " " + lastName;
}
```

### Advanced data binding

```java
// Compose custom binder 
Binder binder = Binder.create(new SyncOnBind<TextView, String>(){
	@Override
	public void onBind(TextView view, String value) {
		view.setText(value + "dollars")
	}
});
  
// Value + binder 
@BindTo(R.id.title_tv) 
ValueBinder title = ValueBinder.create(“200”, binder);
```

Chain bind actions
```java
Binder
.create(new SetText<TextView, String>())
.next(new ApplyText<TextView, String>("dollars"))
.next(new InvisibleIfNull<TextView, String>());
```

### Async data binding

Bind actions that has async dependencies, like animations, can delay bind chain with a callback.

```java
Binder.create(
    new AsyncOnBind<TextView, String>(){
      @Override
      void onBind(TextView view, String text, final OnBindListener listener) {
        ObjectAnimator a = ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f);
        a.setDuration(300);
        a.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // Progress bind chain
                listener.onBindDone();
            }
        });
        a.start();
      }
    })
    .next(...

```

### Getting started

```groovy
  compile 'se.snylt:witch:0.0.1-SNAPSHOT'
  annotationProcessor "se.snylt:witch-processor:0.0.1-SNAPSHOT"
```
Add snapshot repository

```groovy
  repositories {
    mavenCentral()
    maven {
        url "https://oss.sonatype.org/content/repositories/snapshots/"
    }
  }
```
