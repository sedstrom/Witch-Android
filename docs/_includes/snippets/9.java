@BindTo(R.id.my_view)
User user;
...

user = new User("Magica");
Witch.bind(this, this);

user.setName("Merlin"); // Will NOT be bound
Witch.bind(this, this);
