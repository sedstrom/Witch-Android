@Bind(id = R.id.progress)
@BindWhen(BindWhen.ALWAYS) // Will be part of every bind pass
void bindProgress() ( ... )

@Bind(id = R.id.items)
@BindWhen(BindWhen.NOT_SAME) // Will run when data is not same instance as last bind pass
void bindItems( ... )

@Bind(id = R.id.message)
@BindWhen(BindWhen.NOT_EQUALS) // Will run when data is not equal to data from last bind pass 
void bindMessage( ... ) 
