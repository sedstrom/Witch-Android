@BindTo(R.id.status)
@BindWhen(BindWhen.ALWAYS)
String progress; // Will be part of every bind pass

@BindTo(R.id.items)
@BindWhen(BindWhen.NOT_SAME)
List items; // Will be bound when not same instance as current value

@BindTo(R.id.message)
@BindWhen(BindWhen.NOT_EQUALS)
String message; // Will be bound when not same instance or equals to current value
