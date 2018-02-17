class PostBinder extends WitchRecyclerViewAdapter.Binder<Post> {

    private PostBinder() { super(R.layout.post, Post.class); }

    @Bind(id = R.id.title)
    void bindText(TextView title) {
      title.setText(item.title);
    }
}
