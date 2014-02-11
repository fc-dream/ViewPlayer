package me.xiaopan.android.viewplayer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * View播放适配器
 */
public abstract class ViewPlayFragmentPagerAdapter extends FragmentPagerAdapter implements ViewPlayAdapterInterface{
	private ViewPlayMode viewPlayMode = ViewPlayMode.SWING;//播放模式，默认是摇摆
	
	public ViewPlayFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
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

	@Override
	public final Fragment getItem(int position) {
		return getRealItem(getRealPosition(position));
	}
	
	/**
	 * 获取真实的项
	 * @param position
	 * @return
	 */
	public abstract Fragment getRealItem(int position);

	@Override
	public ViewPlayMode getViewPlayMode() {
		return viewPlayMode;
	}

	@Override
	public void setViewPlayMode(ViewPlayMode viewPlayMode) {
		this.viewPlayMode = viewPlayMode;
	}
}
