@BindToTextView(R.id.name)
void name() {
  if(user == null) {
    return "Magica de Hex";
  }
  return user.givenName + " " + user.familyName;
}
