class MyBinder {
  
  @Data
  User user;

  @Bind(id = R.id.name)
  void bindName(TextView name, User user) {
    name.setText(user.getName());
  }
}