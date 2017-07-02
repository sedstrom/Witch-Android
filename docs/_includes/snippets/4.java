@BindTo(R.id.title)
String title = "Abra cadabra!";

@Bind
Binder<TextView, String> bindTitle = Binder.create(new SyncOnBind<TextView, String>() {
  @Override
  public void onBind(TextView view, String title) {
    view.setText(title);
  }
});
