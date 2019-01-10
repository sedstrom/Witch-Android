@Data
String userName() {
  if (user != null) {
    return user.getName()
  }
  return "Guest user";
}
