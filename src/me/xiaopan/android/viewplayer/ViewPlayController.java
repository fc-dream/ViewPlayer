package me.xiaopan.android.viewplayer;

/**
 * View播放控制器
 */
class ViewPlayController implements Runnable{
	private int switchSpace = 3000;//切换间隔
	private boolean currentTowardsTheRight = true;//当前向右播放
	private ViewPlayMode viewPlayMode = ViewPlayMode.SWING;//播放模式，默认转圈
	private ViewPlayer viewPlayer;
	
	public ViewPlayController(ViewPlayer viewPager) {
		this.viewPlayer = viewPager;
	}

	@Override
	public void run() {
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
	
	/**
	 * 启动
	 */
	public void start(){
		viewPlayer.removeCallbacks(this);
		viewPlayer.postDelayed(this, switchSpace);
	}
	
	/**
	 * 停止
	 */
	public void stop(){
		viewPlayer.removeCallbacks(this);
	}
	
	/**
	 * 重置
	 */
	public void reset(){
		if(viewPlayer.getAdapter() != null && viewPlayer.getAdapter().getCount() > 0){
			int nextItem = 0;
			if(viewPlayMode == ViewPlayMode.CIRCLE){
				int realCount = ((ViewPlayAdapterInterface) viewPlayer.getAdapter()).getRealCount();
				nextItem = realCount > 1?((Integer.MAX_VALUE/realCount)/2)*realCount:0;
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