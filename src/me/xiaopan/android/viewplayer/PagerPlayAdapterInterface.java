package me.xiaopan.android.viewplayer;

public interface PagerPlayAdapterInterface{
	
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
	public PlayMode getPlayMode();

	/**
	 * 设置播放模式
	 * @param playMode
	 */
	public void setPlayMode(PlayMode playMode);
}
