Binder
  .create(new PicassoLoadOnBind<ImageView, String>())
  .next(new InvisibleIfNull<ImageView, String>());
