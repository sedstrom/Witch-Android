@BindToView(id = R.id.user_view, view = UserView.class, set = "user")
@BindWhen(BindWhen.ALWAYS)
User user; // Will be part of every bind pass

@BindToTextView(R.id.message)
@BindWhen(BindWhen.NOT_EQUALS)
String message; // Will be bound when not equals to current value
