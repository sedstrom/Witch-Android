@Bind(id = R.id.list) 
void bindPosts(RecyclerView view, List<Post> posts) {
    ((MyAdapter)view.getAdapter()).setPosts(posts);
}

@Bind(id = R.id.list) 
void bindCurrentItem(RecyclerView view, int currentItem) {
    view.setCurrentItem(currentItem);
}