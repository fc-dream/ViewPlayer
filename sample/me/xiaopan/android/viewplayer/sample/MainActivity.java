package me.xiaopan.android.viewplayer.sample;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import me.xiaopan.android.viewplayer.R;
import me.xiaopan.android.viewplayer.ViewPlayMode;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

public class MainActivity extends FragmentActivity {
	private PictureViewPlayer pointViewPlayerCircle;
	private PictureViewPlayer pointViewPlayerSwing;
	private List<String> pictureUrls;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pointViewPlayerCircle = (PictureViewPlayer) findViewById(R.id.pagerPlayer_picturePlayer_circle);
		pointViewPlayerSwing = (PictureViewPlayer) findViewById(R.id.pagerPlayer_picturePlayer_swing);
		
		pictureUrls = new ArrayList<String>();
		for(String url : getResources().getStringArray(R.array.autoPlayGallery_urls2)){
			pictureUrls.add(url);
		}
		
		pointViewPlayerCircle.getViewPlayer().setAdapter(new PicturePlayFragmentStatePagerAdapter(getSupportFragmentManager(), pictureUrls));
		
		pointViewPlayerSwing.getViewPlayer().setAdapter(new PicturePlayFragmentStatePagerAdapter(getSupportFragmentManager(), pictureUrls));
		pointViewPlayerSwing.getViewPlayer().setViewPlayMode(ViewPlayMode.SWING);
		pointViewPlayerSwing.getViewPlayer().setPageTransformer(true, new DepthPageTransformer());
	}

	@Override
	public void onResume() {
		pointViewPlayerCircle.getViewPlayer().start();
		pointViewPlayerSwing.getViewPlayer().start();
		super.onPause();
	}

	@Override
	public void onPause() {
		pointViewPlayerCircle.getViewPlayer().stop();
		pointViewPlayerSwing.getViewPlayer().stop();
		super.onPause();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_all, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_github :
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(getString(R.string.url_github)));
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
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
