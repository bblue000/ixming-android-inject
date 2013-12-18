package org.ixming.android;

import org.ixming.android.inject.InjectorUtils;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

//	@ViewInject(id=R.id.tv1)
//	TextView test1;
	
//	@ViewInject(id=R.id.tv1)
//	private ImageView test2;
	
	// right-OK
//	@ViewInject(id=R.id.tv1)
//	private View testx;
	
//	@ViewInject(id=R.id.tv1, parentId=R.id.rlt1)
//	TextView testx2;
	
//	@ViewInject(id=R.id.tv2)
//	TextView test3;
	
//	@ViewInject(id=R.id.tv2, parentId=R.id.tv2)
//	TextView testx3;
	
	// error-OK
//	@ViewInject(id=R.id.tv2, parentId=R.id.tv1)
//	private TextView test4;
	
	// error-OK
//	@ViewInject(id=R.id.tv2, parentId=R.id.rlt1)
//	private TextView test5;
	
	// error-OK
//	@ViewInject(id=R.id.tv2, parentId=R.id.rlt2)
//	private TextView test6;
	
//	@ViewInject(id=R.id.rlt1)
//	RelativeLayout rlt1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InjectorUtils.defaultInstance().inject(this);
        System.gc();
    }

//    @OnClickMethodInject(id=R.id.tv1)
//    private void test1() {
//    	Log.i("yytest", "test1");
//    }
    
//    @OnClickMethodInject(id=R.id.tv1)
//    private void test2(View v) {
//    	Log.i("yytest", "test2 " + v);
//    }
    
//    @OnClickMethodInject(id=R.id.tv1)
//    private void test3(Object o) {
//    	Log.i("yytest", "test3 " + o);
//    }
    
//    @OnClickMethodInject(id=R.id.tv1)
//    private void test4(Object...o) {
//    	Log.i("yytest", "test4 " + o);
//    }
    
//    @OnClickMethodInject(id=R.id.tv1)
//    void test5(String a, byte b, Runnable c) {
//    	Log.i("yytest", "test5 : " + a + ", " + b + ", " + c);
//    }
}
