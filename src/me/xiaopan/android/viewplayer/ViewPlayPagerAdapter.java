package me.xiaopan.android.viewplayer;

import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

/**
 * View播放适配器
 */
public abstract class ViewPlayPagerAdapter extends PagerAdapter implements ViewPlayAdapterInterface{
	private ViewPlayMode viewPlayMode = ViewPlayMode.CIRCLE;//播放模式，默认是转圈
	
	@Override
	public final int getCount() {
		switch(viewPlayMode){
			case CIRCLE : 
				if(getRealCount() > 1){
					return Integer.MAX_VALUE;
				}else{
					return getRealCount();
				}
			case SWING :
				return getRealCount();
			default : 
				return 0;
		}
	}

	@Override
	public final void destroyItem(ViewGroup container, int position, Object object) {
		destroyRealItem(container, getRealPosition(position), object);
	}

	@Override
	public final Object instantiateItem(ViewGroup container, int position) {
		return instantiateRealItem(container, getRealPosition(position));
	}
	
	@Override
	public final int getRealPosition(int position){
		switch(viewPlayMode){
			case CIRCLE : 
				return position % getRealCount();
			case SWING :
				return position;
			default : 
				return position;
		}
	}
	
	/**
	 * 初始化真实的项
	 * @param container
	 * @param position
	 * @return
	 */
	protected abstract Object instantiateRealItem(ViewGroup container, int position);

	/**
	 * 销毁真实的项
	 * @param container
	 * @param position
	 * @param object
	 */
	protected abstract void destroyRealItem(ViewGroup container, int position, Object object);

	@Override
	public ViewPlayMode getViewPlayMode() {
		return viewPlayMode;
	}

	@Override
	public void setViewPlayMode(ViewPlayMode viewPlayMode) {
		this.viewPlayMode = viewPlayMode;
	}
}