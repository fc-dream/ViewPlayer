package me.xiaopan.android.viewplayer.sample;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.android.viewplayer.PagerPlayer;
import me.xiaopan.android.viewplayer.PlayMode;
import me.xiaopan.android.viewplayer.R;
import me.xiaopan.easy.imageloader.ImageLoader;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

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
	
	public class DepthPageTransformer implements PageTransformer {
	    private static final float MIN_SCALE = 0.75f;
	    @SuppressLint("NewApi")
	    @Override
	    public void transformPage(View view, float position) {
	        int pageWidth = view.getWidth();
	        if (position < -1) { // [-Infinity,-1)
	                                // This page is way off-screen to the left.
	            view.setAlpha(0);
	        } else if (position <= 0) { // [-1,0]
	                                    // Use the default slide transition when
	                                    // moving to the left page
	            view.setAlpha(1);
	            view.setTranslationX(0);
	            view.setScaleX(1);
	            view.setScaleY(1);
	        } else if (position <= 1) { // (0,1]
	                                    // Fade the page out.
	            view.setAlpha(1 - position);
	            // Counteract the default slide transition
	            view.setTranslationX(pageWidth * -position);
	            // Scale the page down (between MIN_SCALE and 1)
	            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
	            view.setScaleX(scaleFactor);
	            view.setScaleY(scaleFactor);
	        } else { // (1,+Infinity]
	                    // This page is way off-screen to the right.
	            view.setAlpha(0);
	        }
	    }
	}
}
