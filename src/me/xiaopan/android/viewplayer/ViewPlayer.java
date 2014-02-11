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

/**
 * View播放器
 */
public class ViewPlayer extends ViewPager {
	private boolean playing;	//播放中
	private ViewPlayController viewPlayController;	//播放控制器
	private OnSetAdapterListener onSetAdapterListener;
	private ViewPlayMode viewPlayMode = ViewPlayMode.CIRCLE;//播放模式，默认是转圈

	public ViewPlayer(Context context, AttributeSet attrs) {
		super(context, attrs);
		setAnimationDuration(500);
	}

	public ViewPlayer(Context context) {
		super(context);
		setAnimationDuration(500);
	}
	
	@Override
	public void setAdapter(PagerAdapter arg0) {
		if(arg0 == null || !(arg0 instanceof ViewPlayAdapterInterface)){
			throw new IllegalArgumentException("适配器必须继实现PagerPlayAdapterInterface接口");
		}

		super.setAdapter(arg0);
		if(viewPlayController == null){
			viewPlayController = new ViewPlayController(this);
		}
		viewPlayController.setViewPlayMode(viewPlayMode);
		((ViewPlayAdapterInterface) arg0).setViewPlayMode(viewPlayMode);
		arg0.notifyDataSetChanged();
		viewPlayController.reset();
		if(onSetAdapterListener != null){
			onSetAdapterListener.onSertAdapter();
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(viewPlayController != null && playing){
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN: viewPlayController.stop(); break;
				case MotionEvent.ACTION_UP: viewPlayController.start(); break;
				case MotionEvent.ACTION_CANCEL: viewPlayController.start(); break;
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
		if(viewPlayController != null && !playing){
			viewPlayController.start();
			playing = true;
		}
	}
	
	/**
	 * 停止
	 */
	public void stop(){
		if(viewPlayController != null && playing){
			viewPlayController.stop();
			playing = false;
		}
	}
	
	/**
	 * 重置
	 */
	public void reset(){
		if(viewPlayController != null){
			viewPlayController.reset();
		}
	}

	/**
	 * 设置播放模式
	 * @param viewPlayMode
	 */
	public void setViewPlayMode(ViewPlayMode viewPlayMode) {
		this.viewPlayMode = viewPlayMode;
		
		if(viewPlayController != null){
			viewPlayController.setViewPlayMode(viewPlayMode);
			viewPlayController.reset();
		}

		if(getAdapter() != null){
			((ViewPlayAdapterInterface) getAdapter()).setViewPlayMode(viewPlayMode);
			getAdapter().notifyDataSetChanged();
		}
	}
	
	/**
	 * 获取真实数量
	 * @return
	 */
	public int getRealCount(){
		if(getAdapter() != null && getAdapter() instanceof ViewPlayAdapterInterface){
			return ((ViewPlayAdapterInterface) getAdapter()).getRealCount();
		}else{
			return 0;
		}
	}
	
	/**
	 * 获取真实位置
	 * @param position
	 * @return 真实位置
	 */
	public int getRealPosition(int position){
		if(getAdapter() != null && getAdapter() instanceof ViewPlayAdapterInterface){
			return ((ViewPlayAdapterInterface) getAdapter()).getRealPosition(position);
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
	 * 当设置适配器时的监听器
	 * @param onSetAdapterListener
	 */
	public void setOnSetAdapterListener(OnSetAdapterListener onSetAdapterListener) {
		this.onSetAdapterListener = onSetAdapterListener;
	}

	/**
	 * 设置切换间隔
	 * @param switchSpace 切换间隔
	 */
	public void setSwitchSpace(int switchSpace) {
		if(viewPlayController != null){
			viewPlayController.setSwitchSpace(switchSpace);
		}
	}
	
	public interface OnSetAdapterListener{
		public void onSertAdapter();
	}
}
