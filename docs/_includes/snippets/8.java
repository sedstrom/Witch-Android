new RecyclerViewBinderAdapter.Builder<>()
        .binder(new Post.Binder())
        .binder(new Header.Binder())
        .build();