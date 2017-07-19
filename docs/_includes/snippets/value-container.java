@BindToView(id = R.id.user_view, view = UserView.class, set = "user")
Value<User> user = new Value(new User("Magica"));; // Wrap user in Value container
...

// user.udpate() will return contained object and flag value as dirty,
// which will qualify user to be bound in next bind pass.
user.update().setName("Merlin");
Witch.bind(this, this);
