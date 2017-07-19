RecyclerViewBinderAdapter<Post> adapter =
        new RecyclerViewBinderAdapter.Builder<Post>()
        .binder(new Post.Binder())
        .build();

recyclerView.setAdapter(adapter);