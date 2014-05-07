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
				return getRealCount() > 1?getRealCount() * 1000:getRealCount();
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
		if(getRealCount() <= 0){
			return 0;
		}
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