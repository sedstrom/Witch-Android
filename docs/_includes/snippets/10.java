@BindTo(R.id.my_view)
Value<User> user;
...

user = new Value(new User("Magica"));
Witch.bind(this, this);

user.update().setName("Merlin"); // udpate() will flag value as dirty
Witch.bind(this, this);
