@BindToTextView(id = R.id.name)
String name() {
  if(user == null) {
    return "Magica de Hex";
  }
  return user.givenName + " " + user.familyName;
}
