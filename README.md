# ![Logo](https://github.com/xiaopansky/Android-ViewPlayer/raw/master/res/drawable-mdpi/ic_launcher.png) Android-ViewPlayer

这是Android上一个专门用于轮播各种View的项目

![sample](https://github.com/xiaopansky/Android-ViewPlayer/raw/master/docs/sample.png)

##Downloads
**[android-view-player-1.1.0.jar](https://github.com/xiaopansky/Android-ViewPlayer/raw/master/releases/android-view-player-1.1.0.jar)**

##Usage Guide
###1.创建你的PagerAdapter适配器
实现方式有两种
>* 你可以选择继承ViewPlayPagerAdapter或者ViewPlayFragmentStatePagerAdapter或者ViewPlayFragmentPagerAdapter三个其中的一个
>* 你可以选择自己继承PagerAdapter并实现ViewPlayAdapterInterface接口，值得注意的是你必须实现ViewPlayAdapterInterface接口，否则将无法使用

示例如下：
```java
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
			ImageLoader.getInstance().display(getArguments().getString(PARAM_REQUIRED_STRING_PICTURE_URL), imageView);
			return imageView;
		}
	}
}
```

###2.创建ViewPlayer并设置适配器
```java
ArrayList pictureUrls = new ArrayList<String>();
for(String url : getResources().getStringArray(R.array.autoPlayGallery_urls2)){
	pictureUrls.add(url);
}
ViewPlayer viewPlayer = new ViewPlayer(getContext());
viewPlayer.setAdapter(new PicturePlayFragmentStatePagerAdapter(getSupportFragmentManager(), pictureUrls));
viewPlayer.setPlayMode(ViewPlayMode.SWING);
```

##License
```java
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
```