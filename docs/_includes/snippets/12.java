@BindToViewPager(id = R.id.view_pager, adapter = MyAdapter.class, set = "items")
List items; // Binds prior to currentItem

@BindToView(id = R.id.view_pager, view = ViewPager.class, set = "currentItem")
int currentItem = 4;
