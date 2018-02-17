@Bind(id = R.id.image)
@BindNull
void bindImageUrl(ImageView image, String url) {
  if(url == null) {
    image.setImageDrawable(null);
  } else {
    // Load image from url
  }
}