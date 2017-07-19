class Post {

  private final String title;

  Post(String title) {
    this.title = title;
  }

  static class Binder extends RecyclerViewBinderAdapter.Binder<Post> {

    Binder() {
      super(R.layout.post);
    }

    @BindToTextView(id = R.id.title)
    String title() {
      return item.title;
    }

    @Override
    public boolean bindsItem(Object item) {
      return item.getClass() == Post.class;
    }
  }
}
