ixming-android-ioc
==================

This is a simple android utility for my Project.

## 简述
看了一些小框架，尝试自己编写了这个工具，功能现在很单一

--------------
### 使用方法
* 完全注解方式就可以进行UI绑定、资源动态获取和事件绑定（>目前只支持onClick方法的绑定，因为考虑到其他方法的复杂性和多样性，而不想在一些方面太多地限制使用者，比如<b>方法参数传递</b>）。
* 无需调用findViewById和setOnClickListener等。

#### Activity中注入

```java
@ResInject(id=android.R.integer.config_longAnimTime, type=ResTargetType.Integer)
private int res1;

@ResInject(id=R.string.hello_world, type=ResTargetType.String)
private String res2;

@ResInject(id=R.array.testStrArr, type=ResTargetType.StringArray)
private String[] res3;

@ResInject(id=R.drawable.ic_launcher, type=ResTargetType.Bitmap)
private Bitmap res4;

@ResInject(id=R.drawable.ic_launcher, type=ResTargetType.Drawable)
private Drawable res5;

@ViewInject(id=R.id.tv)
private TextView textView;

@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    InjectorUtils.defaultInstance().inject(this); //注入view和事件
    ...
    textView.setText("some text..." + res1);
    ...
}

/**
 * 给TextView添加监听事件
 */
@OnClickMethodInject(id=R.id.tv)
private void textOnClick() { //只需要保证没有参数，或者第一个参数为View，传入的是当前设置了事件的View

}
```

#### Fragment中注入

```java
@ResInject(id=android.R.integer.config_longAnimTime, type=ResTargetType.Integer)
private int res1;
@ViewInject(id=R.id.tv)
private TextView textView;
@Override
public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    InjectorUtils.defaultInstance().inject(this, view); //注入view和事件
    ...
    textView.setText("some text..." + res1);
    ...
}

@OnClickMethodInject(id=R.id.tv)
private void textOnClick() { //只需要保证没有参数，或者第一个参数为View，传入的是当前设置了事件的View

}
```

#### ListView的ViewHolder中注入

```java
class ViewHolder {
  @ViewInject(id=R.id.tv)
  private TextView textView;
}

...
public view getView(int position, View convertView, ViewGroup parent) {
  ViewHolder holder = new ViewHolder();
  if (null == convertView) {
    ...//inflate view
    holder = new ViewHolder();
    InjectorUtils.defaultInstance().inject(this, view);
    convertView.setTag(holder);
  } else {
    holder = convertView.setTag(holder);
  }
  ...// other operations
}
...
```

--------------
## 注意点
* 虽然onClick事件监听尝试减少参数的限制，但是这是避免不了的，使用时还得多加注意。
  > * 该处考虑参数虽然有点多余（使用时要获取的参数Activity内部都已有变量），可是否使用参数是使用者特定的情形下决定的，而这一点跟具体情况关系密切，不能模糊猜测使用者的用意，导致弄巧成拙——如果跟监听事件一样，那么跟不使用工具有什么区别？
  > * 这也是不想增加其他事件动态注入的原因

* 使用的是反射，考虑到移动设备的性能多样性，增加了设置类InjectConfigure（所以会有defaultInstance等静态方法），设置动态注入资源/View/onClick信息。

```java
  /**
	 * 当应用想要以单例模式处理——所有用到本工具的地方，都配用同一套InjectConfigure设置
	 */
	public static synchronized InjectorUtils buildAsSingleton(InjectConfigure configure) {
		if (null == mSingleton) {
			mSingleton = new InjectorUtils(configure);
		}
		return mSingleton;
	}
	
	/**
	 * 默认所有项都支持的实例。
	 * 
	 * <p>
	 * Tips:当没有通过{@link InjectorUtils#buildAsSingleton(InjectConfigure)}设置客户端单例时，
	 * 回创建一个新对象；<br/>
	 * 当作为单例创建后，始终返回单例对象。
	 * </p>
	 */
	public static InjectorUtils defaultInstance() {
		return null == mSingleton ? new InjectorUtils(new InjectConfigure()) : mSingleton;
	}

	/**
	 * 根据客户端自定义的configure设置获得相应支持的实例
	 * 
	 * <p>
	 * <strong><i>Tips:</i></strong><br/>
	 * 当没有通过{@link InjectorUtils#buildAsSingleton(InjectConfigure)}设置客户端单例时，
	 * 回创建一个新对象；<br/>
	 * 当作为单例创建后，始终返回单例对象。（configure is ignored）
	 * </p>
	 */
	public static InjectorUtils instanceBuildFrom(InjectConfigure configure) {
		return null == mSingleton ? new InjectorUtils(configure) : mSingleton;
	}
```
* 在<i>org.ixming.android.inject.themed</i>包内的功能是用于app之间使用的，类似QQ的换肤实现情境下可以使用

--------------
