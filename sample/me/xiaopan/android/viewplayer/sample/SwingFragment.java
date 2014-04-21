package me.xiaopan.android.viewplayer.sample;

import me.xiaopan.android.viewplayer.R;
import me.xiaopan.android.viewplayer.ViewPlayMode;
import me.xiaopan.android.viewplayer.sample.FragmentListPagerAdapter.TitleFragment;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.PageTransformer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SwingFragment extends Fragment implements TitleFragment{
	private PictureViewPlayer pictureViewPlayer;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.picture_player, null);
		pictureViewPlayer = (PictureViewPlayer) view.findViewById(R.id.viewPager);
		pictureViewPlayer.getViewPlayer().setAdapter(new PicturePagerAdapter(getChildFragmentManager(), getResources().getStringArray(R.array.autoPlayGallery_urls2)));
		pictureViewPlayer.getViewPlayer().setViewPlayMode(ViewPlayMode.SWING);
		pictureViewPlayer.getViewPlayer().setPageTransformer(true, new DepthPageTransformer());
		return view;
	}

	@Override
	public void onPause() {
		super.onPause();
		pictureViewPlayer.getViewPlayer().stop();
	}

	@Override
	public void onResume() {
		super.onResume();
		pictureViewPlayer.getViewPlayer().start();
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

	@Override
	public CharSequence pageTitle() {
		return "摇摆";
	}
}
