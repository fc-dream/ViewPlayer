package me.xiaopan.android.viewplayer;


public class PlayController implements Runnable{
	private int switchSpace = 3000;//切换间隔
	private boolean currentTowardsTheRight = true;//当前向右播放
	private PlayMode playMode = PlayMode.SWING;//播放模式，默认转圈
	private PagerPlayer pagerPlayer;
	
	public PlayController(PagerPlayer viewPager) {
		this.pagerPlayer = viewPager;
	}

	@Override
	public void run() {
		if(pagerPlayer.getAdapter().getCount() > 1){
			int nextItem = 0;
			switch(playMode){
				case CIRCLE : 
					nextItem = (pagerPlayer.getCurrentItem()+1) % pagerPlayer.getAdapter().getCount();
					break;
				case SWING : 
					//如果当前是向右播放
					if(currentTowardsTheRight){
						if(pagerPlayer.getCurrentItem() == pagerPlayer.getAdapter().getCount() -1){//如果到最后一个了
							currentTowardsTheRight = false;//标记为向左
							nextItem = pagerPlayer.getCurrentItem() - 1;
						}else{
							nextItem = pagerPlayer.getCurrentItem() + 1;
						}
					}else{
						if(pagerPlayer.getCurrentItem() == 0){//如果到第一个了
							currentTowardsTheRight = true;//标记为向右
							nextItem = pagerPlayer.getCurrentItem() + 1;
						}else{
							nextItem = pagerPlayer.getCurrentItem() - 1;
						}
					}
					break;
			}
			pagerPlayer.setCurrentItem(nextItem, true);
		}
		pagerPlayer.postDelayed(this, switchSpace);
	}
	
	/**
	 * 启动
	 */
	public void start(){
		pagerPlayer.removeCallbacks(this);
		pagerPlayer.postDelayed(this, switchSpace);
	}
	
	/**
	 * 停止
	 */
	public void stop(){
		pagerPlayer.removeCallbacks(this);
	}
	
	/**
	 * 重置
	 */
	public void reset(){
		if(pagerPlayer.getAdapter() != null && pagerPlayer.getAdapter().getCount() > 0){
			int nextItem = 0;
			if(playMode == PlayMode.CIRCLE){
				int realCount = ((PagerPlayAdapterInterface) pagerPlayer.getAdapter()).getRealCount();
				nextItem = realCount > 1?((Integer.MAX_VALUE/realCount)/2)*realCount:0;
			}
			pagerPlayer.setCurrentItem(nextItem, true);
		}
	}

	/**
	 * 设置播放模式
	 * @param playMode
	 */
	public void setPlayMode(PlayMode playMode) {
		this.playMode = playMode;
	}
	
	/**
	 * 设置切换间隔
	 * @param switchSpace 切换间隔
	 */
	public void setSwitchSpace(int switchSpace) {
		this.switchSpace = switchSpace;
	}
}