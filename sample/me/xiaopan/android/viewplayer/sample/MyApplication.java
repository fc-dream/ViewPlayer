package me.xiaopan.android.viewplayer.sample;

import me.xiaopan.android.imageloader.ImageLoader;
import me.xiaopan.android.imageloader.task.Options;
import me.xiaopan.android.viewplayer.R;
import android.app.Application;

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		ImageLoader.getInstance(getBaseContext()).getConfiguration().putOptions(OptionType.VIEW_PLAYER, new Options(getBaseContext()).setLoadingDrawableResId(R.drawable.image_loading).setFailureDrawableResId(R.drawable.image_loading));
	}
	
	public enum OptionType {
		VIEW_PLAYER;
	}
}
