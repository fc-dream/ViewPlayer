package me.xiaopan.android.viewplayer;

import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

public abstract class PagerPlayAdapter extends PagerAdapter implements PagerPlayAdapterInterface{
	private PlayMode playMode = PlayMode.CIRCLE;//播放模式，默认是从左往右转圈
	
	@Override
	public final int getCount() {
		switch(playMode){
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
	public int getRealPosition(int position){
		switch(playMode){
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
	public PlayMode getPlayMode() {
		return playMode;
	}

	@Override
	public void setPlayMode(PlayMode playMode) {
		this.playMode = playMode;
	}
}