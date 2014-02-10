package me.xiaopan.android.viewplayer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * View播放适配器
 */
public abstract class ViewPlayFragmentStatePagerAdapter extends FragmentStatePagerAdapter implements ViewPlayAdapterInterface{
	private ViewPlayMode viewPlayMode = ViewPlayMode.SWING;//播放模式，默认是从左往右转圈

	public ViewPlayFragmentStatePagerAdapter(FragmentManager fm) {
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
	public int getRealPosition(int position){
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
	public Fragment getItem(int position) {
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
