package me.xiaopan.android.viewplayer.sample;

import me.xiaopan.android.viewplayer.R;
import me.xiaopan.android.viewplayer.ViewPlayIndicator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PointViewPlayIndicator extends ViewPlayIndicator {

	public PointViewPlayIndicator(Context context) {
		super(context);
	}

	public PointViewPlayIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void init(int size) {
		if(size > 1){
			setPadding(8, 8, 8, 8);
			for(int w = 0; w < size; w++){//然后初始化所有的图标并将其放进存放图标的布局中
				try{
					ImageView image = new ImageView(getContext());
					
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
					params.setMargins(8, 8, 8, 8);//设置指示器内图标的外边距
					image.setLayoutParams(params);
					
					image.setImageResource(R.drawable.selector_radio_play_indicator); 
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
}
