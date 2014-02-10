package me.xiaopan.android.viewplayer.sample;

import me.xiaopan.android.viewplayer.ViewPlayIndicator;
import me.xiaopan.android.viewplayer.ViewPlayer;
import me.xiaopan.android.viewplayer.ViewPlayer.OnSetAdapterListener;
import android.content.Context;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;

public class PointViewPlayer extends FrameLayout {
	private ViewPlayer viewPlayer;
	private PointViewPlayIndicator pointViewPlayIndicator;
	
	public PointViewPlayer(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PointViewPlayer(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		viewPlayer = new ViewPlayer(getContext());
		viewPlayer.setId(viewPlayer.hashCode());
		addView(viewPlayer);
		
		pointViewPlayIndicator = new PointViewPlayIndicator(getContext());
		pointViewPlayIndicator.setId(pointViewPlayIndicator.hashCode());
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
		addView(pointViewPlayIndicator, layoutParams);
		
		viewPlayer.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				pointViewPlayIndicator.selected(viewPlayer.getRealPosition(arg0));
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {}
			@Override
			public void onPageScrollStateChanged(int arg0) {}
		});
		
		viewPlayer.setOnSetAdapterListener(new OnSetAdapterListener() {
			@Override
			public void onSertAdapter() {
				if(viewPlayer.getAdapter() != null){
					pointViewPlayIndicator.init(viewPlayer.getRealCount());
					pointViewPlayIndicator.selected(0);
				}
			}
		});
	}

	public ViewPlayer getViewPlayer() {
		return viewPlayer;
	}

	public ViewPlayIndicator getViewPlayIndicator() {
		return pointViewPlayIndicator;
	}
}
