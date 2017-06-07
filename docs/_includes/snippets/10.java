@BindToView(id = R.id.user_view, view = UserView.class, set = "user")
Value<User> user;
...

user = new Value(new User("Magica"));
Witch.bind(this, this);

user.update().setName("Merlin"); // udpate() will flag value as dirty
Witch.bind(this, this);
