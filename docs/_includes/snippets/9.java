@BindToView(id = R.id.user_view, view = UserView.class, set = "user")
User user;
...

user = new User("Magica");
Witch.bind(this, this);

user.setName("Merlin"); // Will NOT be bound
Witch.bind(this, this);
