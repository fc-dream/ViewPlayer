/*
 * Copyright 2013 Peng fei Pan
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

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * View播放指示器
 */
public abstract class ViewPlayIndicator extends LinearLayout {
	private int lastCheckedPosition;//上次选中的图标的位置
	
	public ViewPlayIndicator(Context context) {
		super(context);
	}
	
	public ViewPlayIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	/**
	 * 选中
	 * @param selectedItemPosition
	 */
	public void selected(int selectedItemPosition) {
		if(getChildCount() > 0 && selectedItemPosition < getChildCount()){
			(getChildAt(lastCheckedPosition)).setSelected(false);//先将上一个取消
			(getChildAt(selectedItemPosition)).setSelected(true);//再将当前的选中
			lastCheckedPosition = selectedItemPosition;//记录本次选中的
		}
	}
}
