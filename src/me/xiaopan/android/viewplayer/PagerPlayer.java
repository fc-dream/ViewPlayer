package me.xiaopan.android.viewplayer;

import java.lang.reflect.Field;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class PagerPlayer extends ViewPager {
	private boolean playing;	//播放中
	private PlayController playController;	//播放控制器

	public PagerPlayer(Context context, AttributeSet attrs) {
		super(context, attrs);
		setAnimationDuration(500);
	}

	public PagerPlayer(Context context) {
		super(context);
	}
	
	@Override
	public void setAdapter(PagerAdapter arg0) {
		if(!(arg0 instanceof PagerPlayAdapterInterface)){
			throw new IllegalArgumentException("适配器必须继实现PagerPlayAdapterInterface接口");
		}

		super.setAdapter(arg0);
		if(arg0 != null && arg0.getCount() > 0){
			removeAllViews();
			if(playController == null){
				playController = new PlayController(this);
			}
			playController.reset();
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(playController != null && playing){
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN: playController.stop(); break;
				case MotionEvent.ACTION_UP: playController.start(); break;
				case MotionEvent.ACTION_CANCEL: playController.start(); break;
				default: break;
			}
		}
		return super.onTouchEvent(event);
	}
	
	/**
	 * 是否正在播放
	 * @return
	 */
	public boolean isPlaying() {
		return playing;
	}

	/**
	 * 启动
	 */
	public void start(){
		if(playController != null && !playing){
			playController.start();
			playing = true;
		}
	}
	
	/**
	 * 停止
	 */
	public void stop(){
		if(playController != null && playing){
			playController.stop();
			playing = false;
		}
	}
	
	/**
	 * 重置
	 */
	public void reset(){
		if(playController != null){
			playController.reset();
		}
	}

	/**
	 * 设置播放模式
	 * @param playMode
	 */
	public void setPlayMode(PlayMode playMode) {
		if(playController != null){
			playController.setPlayMode(playMode);
		}

		if(getAdapter() != null){
			((PagerPlayAdapterInterface) getAdapter()).setPlayMode(playMode);
			getAdapter().notifyDataSetChanged();
		}
	}
	
	/**
	 * 获取真实位置
	 * @param position
	 * @return 真实位置
	 */
	public int getRealPosition(int position){
		if(getAdapter() != null && getAdapter() instanceof PagerPlayAdapterInterface){
			return ((PagerPlayAdapterInterface) getAdapter()).getRealPosition(position);
		}else{
			return position;
		}
	}

	/**
	 * 设置自动切换动画持续时间
	 * @param duration
	 */
	public void setAnimationDuration(int duration){
		try {
			Field field = ViewPager.class.getDeclaredField("mScroller");
			field.setAccessible(true);
			FixedSpeedScroller scroller = new FixedSpeedScroller(this.getContext(), new AccelerateDecelerateInterpolator());
			field.set(this, scroller);
			scroller.setAnimationDuration(duration);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private class FixedSpeedScroller extends Scroller {
	    private int animationDuration = 400;

	    public FixedSpeedScroller(Context context) {
	        super(context);
	    }

	    public FixedSpeedScroller(Context context, Interpolator interpolator) {
	        super(context, interpolator);
	    }

	    @Override
	    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
	        super.startScroll(startX, startY, dx, dy, animationDuration);
	    }

	    @Override
	    public void startScroll(int startX, int startY, int dx, int dy) {
	        super.startScroll(startX, startY, dx, dy, animationDuration);
	    }

	    public void setAnimationDuration(int animationDuration) {
	        this.animationDuration = animationDuration;
	    }
	}
	
	/**
	 * 设置切换间隔
	 * @param switchSpace 切换间隔
	 */
	public void setSwitchSpace(int switchSpace) {
		if(playController != null){
			playController.setSwitchSpace(switchSpace);
		}
	}
}
