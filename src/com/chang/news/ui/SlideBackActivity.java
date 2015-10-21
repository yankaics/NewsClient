package com.chang.news.ui;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.VelocityTracker;

public class SlideBackActivity extends Activity {
	// ��ָ���»���ʱ����С�ٶ�
	private static final int YSPEED_MIN = 1000;

	// ��ָ���һ���ʱ����С����
	private static final int XDISTANCE_MIN = 50;

	// ��ָ���ϻ����»�ʱ����С����
	private static final int YDISTANCE_MIN = 100;

	// ��¼��ָ����ʱ�ĺ����ꡣ
	private float xDown;

	// ��¼��ָ����ʱ�������ꡣ
	private float yDown;

	// ��¼��ָ�ƶ�ʱ�ĺ����ꡣ
	private float xMove;

	// ��¼��ָ�ƶ�ʱ�������ꡣ
	private float yMove;

	// ���ڼ�����ָ�������ٶȡ�
	private VelocityTracker mVelocityTracker;

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		createVelocityTracker(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDown = event.getRawX();
			yDown = event.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			xMove = event.getRawX();
			yMove = event.getRawY();
			// �����ľ���
			int distanceX = (int) (xMove - xDown);
			int distanceY = (int) (yMove - yDown);
			// ��ȡ˳ʱ�ٶ�
			int ySpeed = getScrollVelocity();
			// �ر�Activity����������������
			// 1.x�Ử���ľ���>XDISTANCE_MIN
			// 2.y�Ử���ľ�����YDISTANCE_MIN��Χ��
			// 3.y���ϣ������»������ٶȣ�<XSPEED_MIN��������ڣ�����Ϊ�û���ͼ�������»��������󻬽���Activity
			if (distanceX > XDISTANCE_MIN
					&& (distanceY < YDISTANCE_MIN && distanceY > -YDISTANCE_MIN)
					&& ySpeed < YSPEED_MIN) {
				finish();
			}
			break;
		case MotionEvent.ACTION_UP:
			recycleVelocityTracker();
			break;
		default:
			break;
		}
		return super.dispatchTouchEvent(event);
	}

	/**
	 * ����VelocityTracker���󣬲�����������Ļ����¼����뵽VelocityTracker���С�
	 *
	 * @param event
	 *
	 */
	private void createVelocityTracker(MotionEvent event) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
	}

	/**
	 * ����VelocityTracker����
	 */
	private void recycleVelocityTracker() {
		mVelocityTracker.recycle();
		mVelocityTracker = null;
	}

	/**
	 *
	 * @return �����ٶȣ���ÿ�����ƶ��˶�������ֵΪ��λ��
	 */
	private int getScrollVelocity() {
		mVelocityTracker.computeCurrentVelocity(1000);
		int velocity = (int) mVelocityTracker.getYVelocity();
		return Math.abs(velocity);
	}
	

}