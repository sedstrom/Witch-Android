// The set-perfix can be stripped off, i.e. setText will be defined as set="text"
@BindData(id = R.id.name, view = TextView.class, set = "text")
String userName() {
    return user.getName();
}