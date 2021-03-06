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

/**
 * View播放控制器
 */
class ViewPlayController implements Runnable{
	private int switchSpace = 3000;//切换间隔
	private boolean currentTowardsTheRight = true;//当前向右播放
	private ViewPlayMode viewPlayMode = ViewPlayMode.CIRCLE;//播放模式，默认是转圈
	private ViewPlayer viewPlayer;
	
	public ViewPlayController(ViewPlayer viewPager) {
		this.viewPlayer = viewPager;
	}

	@Override
	public void run() {
		if(viewPlayer.isPlaying()){
			if(viewPlayer.getAdapter().getCount() > 1){
				int nextItem = 0;
				switch(viewPlayMode){
				case CIRCLE : 
					nextItem = (viewPlayer.getCurrentItem()+1) % viewPlayer.getAdapter().getCount();
					break;
				case SWING : 
					//如果当前是向右播放
					if(currentTowardsTheRight){
						if(viewPlayer.getCurrentItem() == viewPlayer.getAdapter().getCount() -1){//如果到最后一个了
							currentTowardsTheRight = false;//标记为向左
							nextItem = viewPlayer.getCurrentItem() - 1;
						}else{
							nextItem = viewPlayer.getCurrentItem() + 1;
						}
					}else{
						if(viewPlayer.getCurrentItem() == 0){//如果到第一个了
							currentTowardsTheRight = true;//标记为向右
							nextItem = viewPlayer.getCurrentItem() + 1;
						}else{
							nextItem = viewPlayer.getCurrentItem() - 1;
						}
					}
					break;
				}
				viewPlayer.setCurrentItem(nextItem, true);
			}
			viewPlayer.postDelayed(this, switchSpace);
		}
	}
	
	/**
	 * 启动
	 */
	public void start(){
		if(viewPlayer.isPlaying()){
			viewPlayer.removeCallbacks(this);
			viewPlayer.postDelayed(this, switchSpace);
		}
	}
	
	/**
	 * 停止
	 */
	public void stop(){
		if(viewPlayer.isPlaying()){
			viewPlayer.removeCallbacks(this);
		}
	}
	
	/**
	 * 重置
	 */
	public void reset(){
		if(viewPlayer.getAdapter() != null && viewPlayer.getAdapter().getCount() > 0){
			int nextItem = 0;
			if(viewPlayMode == ViewPlayMode.CIRCLE){
				int realCount = ((ViewPlayAdapterInterface) viewPlayer.getAdapter()).getRealCount();
				if(realCount > 1){
					nextItem = (viewPlayer.getAdapter().getCount()/2) -  ((viewPlayer.getAdapter().getCount()/2) % realCount);
				}else{
					nextItem = 0;
				}
			}else{
				currentTowardsTheRight = true;
			}
			viewPlayer.setCurrentItem(nextItem, true);
		}
	}

	/**
	 * 设置播放模式
	 * @param viewPlayMode
	 */
	public void setViewPlayMode(ViewPlayMode viewPlayMode) {
		this.viewPlayMode = viewPlayMode;
	}
	
	/**
	 * 设置切换间隔
	 * @param switchSpace 切换间隔
	 */
	public void setSwitchSpace(int switchSpace) {
		this.switchSpace = switchSpace;
	}
}