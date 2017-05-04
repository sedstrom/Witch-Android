Binder binder = Binder.create(new SyncOnBind<ImageView, String>() {
  @Override 
  void onBind(ImageView view, String url) {
    Picasso
    .with(view.getContext())
    .load(url)
    .into(imageView);
  }
});

// Use ValueBinder to use binder with annotation
@BindTo(R.id.image)
ValueBinder kitten = ValueBinder.create(“www.snylt.se/kitten.png”, binder);
