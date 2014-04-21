package me.xiaopan.android.viewplayer.sample;

import me.xiaopan.android.viewplayer.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class ViewPagerActivity extends FragmentActivity{
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_view_pager);
		ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager_main);
		setContentView(viewPager);
		
		viewPager.setAdapter(new FragmentListPagerAdapter(getSupportFragmentManager(), new SwingFragment(), new CircleFragment()));
	}
}
