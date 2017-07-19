class ViewState {
  @BindToTextView(id = R.id.title)
  String title = "Hello Witch!";
}
...

Witch.bind(viewState, activity);
