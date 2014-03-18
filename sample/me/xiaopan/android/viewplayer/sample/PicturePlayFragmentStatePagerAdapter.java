package me.xiaopan.android.viewplayer.sample;

import java.util.List;

import me.xiaopan.android.imageloader.ImageLoader;
import me.xiaopan.android.viewplayer.ViewPlayFragmentStatePagerAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * 图片播放适配器
 */
public class PicturePlayFragmentStatePagerAdapter extends ViewPlayFragmentStatePagerAdapter {
	private List<String> pictures;//图片列表

	public PicturePlayFragmentStatePagerAdapter(FragmentManager fm, List<String> pictureUrls) {
		super(fm);
		this.pictures = pictureUrls;
	}

	@Override
	public int getRealCount() {
		return pictures != null?pictures.size():0;
	}

	@Override
	public Fragment getRealItem(int position) {
		PictureFragment pictureFragment = new PictureFragment();
		Bundle bundle = new Bundle();
		bundle.putString(PictureFragment.PARAM_REQUIRED_STRING_PICTURE_URL, pictures.get(position));
		pictureFragment.setArguments(bundle);
		return pictureFragment;
	}
	
	public static class PictureFragment extends Fragment{
		public static final String PARAM_REQUIRED_STRING_PICTURE_URL = "PARAM_REQUIRED_STRING_PICTURE_URL";
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			ImageView imageView = new ImageView(getActivity().getBaseContext());
			imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
			imageView.setScaleType(ScaleType.CENTER_CROP);
			ImageLoader.getInstance(getActivity()).display(getArguments().getString(PARAM_REQUIRED_STRING_PICTURE_URL), imageView, OptionType.VIEW_PLAYER);
			return imageView;
		}
	}
}
