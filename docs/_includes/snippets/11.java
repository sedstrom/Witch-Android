@BindToView(id = R.id.user_view, view = UserView.class, set = "user")
@AlwaysBind
Value<User> user; // Will be part of every bind pass
