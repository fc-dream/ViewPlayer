package me.xiaopan.android.viewplayer;

/**
 * View播放适配器接口
 */
interface ViewPlayAdapterInterface{
	
	/**
	 * 获取真实数量
	 * @return
	 */
	public int getRealCount();
	
	/**
	 * 获取真实位置
	 * @param position
	 * @return 真实位置
	 */
	public int getRealPosition(int position);
	
	/**
	 * 获取播放模式
	 * @return
	 */
	public ViewPlayMode getViewPlayMode();

	/**
	 * 设置播放模式
	 * @param viewPlayMode
	 */
	public void setViewPlayMode(ViewPlayMode viewPlayMode);
}
