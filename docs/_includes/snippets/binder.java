@BindTo(R.id.title)
String title = "Abra cadabra!";

@Binds
Binder<TextView, String> bindsTitle = Binder.create(new SyncOnBind<TextView, String>() {
  @Override
  public void onBind(TextView view, String title) {
    view.setText(title);
  }
});
