package me.xiaopan.android.viewplayer.sample;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.android.viewplayer.PagerPlayer;
import me.xiaopan.android.viewplayer.PlayMode;
import me.xiaopan.android.viewplayer.R;
import me.xiaopan.easy.imageloader.ImageLoader;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class MainActivity extends FragmentActivity {
	private PagerPlayer pagerPlayerCircle;
	private PagerPlayer pagerPlayerSwing;
	private List<String> pictures;
	private PointPlayIndicator pointPlayIndicator;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pagerPlayerCircle = (PagerPlayer) findViewById(R.id.pagerPlayer_picturePlayer_circle);
		pagerPlayerSwing = (PagerPlayer) findViewById(R.id.pagerPlayer_picturePlayer_swing);
		pointPlayIndicator = (PointPlayIndicator) findViewById(R.id.indiccator);
		
		pictures = new ArrayList<String>();
		for(String url : getResources().getStringArray(R.array.autoPlayGallery_urls2)){
			pictures.add(url);
		}
		
		ImageLoader.getInstance().init(getBaseContext());
		
		pointPlayIndicator.onInit(pictures.size());
		pointPlayIndicator.onItemSelected(0);
		pagerPlayerCircle.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				pointPlayIndicator.onItemSelected(pagerPlayerCircle.getRealPosition(arg0));
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		pagerPlayerCircle.setAdapter(new PicturePlayFragmentAdapter(getSupportFragmentManager(), pictures));
		pagerPlayerCircle.setPlayMode(PlayMode.CIRCLE);
		
		pagerPlayerSwing.setAdapter(new PicturePlayFragmentAdapter(getSupportFragmentManager(), pictures));
		pagerPlayerSwing.setPlayMode(PlayMode.SWING);
		pagerPlayerSwing.setPageTransformer(true, new DepthPageTransformer());
	}

	@Override
	public void onResume() {
		pagerPlayerCircle.start();
		pagerPlayerSwing.start();
		super.onPause();
	}

	@Override
	public void onPause() {
		pagerPlayerCircle.stop();
		pagerPlayerSwing.stop();
		super.onPause();
	}
}
