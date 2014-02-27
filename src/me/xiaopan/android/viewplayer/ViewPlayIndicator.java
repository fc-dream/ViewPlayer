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

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * View播放指示器
 */
public class ViewPlayIndicator extends LinearLayout {
	private int lastCheckedPosition;//上次选中的图标的位置
	private int indicatorDrawableResdId;	//指示器图片
	
	public ViewPlayIndicator(Context context) {
		super(context);
	}
	
	public ViewPlayIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	/**
	 * 初始化指示器
	 * @param size
	 */
	public void initIndicator(int size) {
		removeAllViews();
		if(size > 1 && indicatorDrawableResdId != 0){
			setPadding(8, 8, 8, 8);
			for(int w = 0; w < size; w++){//然后初始化所有的图标并将其放进存放图标的布局中
				try{
					ImageView image = new ImageView(getContext());
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
					params.setMargins(8, 8, 8, 8);//设置指示器内图标的外边距
					image.setLayoutParams(params);
					image.setImageResource(indicatorDrawableResdId);
					addView(image);
				}catch(Throwable throwable){
					throwable.printStackTrace();
				}
			}
			setVisibility(View.VISIBLE);
		}else{
			setVisibility(View.GONE);
		}
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

	/**
	 * 设置指示图标图片
	 * @param indicatorDrawableResdId
	 */
	public void setIndicatorDrawableResId(int indicatorDrawableResdId) {
		this.indicatorDrawableResdId = indicatorDrawableResdId;
	}
}
