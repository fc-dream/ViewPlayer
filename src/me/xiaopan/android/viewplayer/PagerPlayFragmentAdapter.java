package me.xiaopan.android.viewplayer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public abstract class PagerPlayFragmentAdapter extends FragmentStatePagerAdapter implements PagerPlayAdapterInterface{
	private PlayMode playMode = PlayMode.SWING;//播放模式，默认是从左往右转圈

	public PagerPlayFragmentAdapter(FragmentManager fm) {
		super(fm);
	}
	
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
	public PlayMode getPlayMode() {
		return playMode;
	}

	@Override
	public void setPlayMode(PlayMode playMode) {
		this.playMode = playMode;
	}
}
