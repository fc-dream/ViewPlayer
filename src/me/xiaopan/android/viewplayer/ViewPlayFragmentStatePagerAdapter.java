/*
 * Copyright (C) 2013 Peng fei Pan <sky@xiaopan.me>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.xiaopan.android.viewplayer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * View播放适配器
 */
public abstract class ViewPlayFragmentStatePagerAdapter extends FragmentStatePagerAdapter implements ViewPlayAdapterInterface{
	private ViewPlayMode viewPlayMode = ViewPlayMode.CIRCLE;//播放模式，默认是转圈
	
	public ViewPlayFragmentStatePagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	@Override
	public final int getCount() {
		switch(viewPlayMode){
			case CIRCLE : 
				return getRealCount() > 1?getRealCount() * 1000:getRealCount();
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
	public final Fragment getItem(int position) {
		return getRealItem(getRealPosition(position));
	}
	
	@Override
	public int getRealCount() {
		return 0;
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
