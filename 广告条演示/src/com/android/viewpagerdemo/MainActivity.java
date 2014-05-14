package com.android.viewpagerdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity implements OnPageChangeListener {

	private List<ImageView> imagelist;
	private TextView tv;
	private LinearLayout ll;
	private int preEnablePositon = 0; // ǰһ����ѡ�еĵ������λ�� Ĭ�������Ϊ0
	private String[] imagemiaoshu = { "���������ף��ҾͲ��ܵ���", "�����ֻ����ˣ��ٳ������ϸ������˴�ϳ�",
			"���ر�����Ӱ�������", "������TV�������", "��Ѫ��˿�ķ�ɱ" };
	private ViewPager viewPager;
	private boolean isStop = false;  //�Ƿ�ֹͣ���߳�  ����ֹͣ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();
		// �����߳������Զ��ƶ�
		Thread myThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (!isStop) {
					//ÿ�������ӷ�һ����Ϣ�����̣߳�����viewpager����
					SystemClock.sleep(2000);
					runOnUiThread(new Runnable() {
						public void run() {
							// �˷��������߳���ִ��
							int newindex = viewPager.getCurrentItem() + 1;
							viewPager.setCurrentItem(newindex);
						}
					});
				}
			}
		});
		myThread.start(); // ������ϸ�µĻ���  ����ҳ��ʧȥ����ʱ��ֹͣ���ָ̻߳�����ʱ�ٿ���
	}
	
	@Override
	protected void onDestroy() {
		isStop = true;
		super.onDestroy();
	}

	private void init() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		ll = (LinearLayout) findViewById(R.id.ll_point_group);
		tv = (TextView) findViewById(R.id.tv_image_miaoshu);

		imagelist = new ArrayList<ImageView>();
		int[] imageIDs = { R.drawable.a, R.drawable.b, R.drawable.c,
				R.drawable.d, R.drawable.e, };

		ImageView iv;
		View view;
		LayoutParams params;
		for (int id : imageIDs) {
			iv = new ImageView(this);
			iv.setBackgroundResource(id);
			imagelist.add(iv);

			// ÿѭ��һ�����һ���㵽���β�����
			view = new View(this);
			view.setBackgroundResource(R.drawable.point_background);
			params = new LayoutParams(5, 5);
			params.leftMargin = 5;
			view.setEnabled(false);
			view.setLayoutParams(params);
			ll.addView(view); // �����Բ�������ӡ��㡱
		}

		viewPager.setAdapter(new MyAdapter());
		viewPager.setOnPageChangeListener(this);

		// ��ʼ��ͼƬ��������һ���㱻ѡ��
		tv.setText(imagemiaoshu[0]);
		ll.getChildAt(0).setEnabled(true);

		// ��ʼ��viewpager��Ĭ��position.MAX_value��һ��
		int index = (Integer.MAX_VALUE / 2)
				- ((Integer.MAX_VALUE / 2) % imagelist.size());
		viewPager.setCurrentItem(index); // ���õ�ǰviewpagerѡ�е�pagerҳ
											// ���ᴥ��OnPageChangeListener�е�onPageSelected����

	}

	class MyAdapter extends PagerAdapter {

		/**
		 * ���ٶ���
		 * 
		 * @param position
		 *            ��Ҫ�����ٶ��������λ��
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(imagelist.get(position % imagelist.size()));
		}

		/**
		 * ��ʼ��һ������
		 * 
		 * @param position
		 *            ��Ҫ�������Ķ��������λ��
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// �ȰѶ�����ӵ�viewpager�У��ٷ��ص�ǰ����
			container.addView(imagelist.get(position % imagelist.size()));
			return imagelist.get(position % imagelist.size());
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Integer.MAX_VALUE;
		}

		/**
		 * ���ö��� true ���ö��� false �õ���object
		 */
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int position) {
		// ȡ��������
		int newPositon = position % imagelist.size();

		// ������������ͼƬ������
		tv.setText(imagemiaoshu[newPositon]);

		// ����һ��������Ϊ��ѡ��
		ll.getChildAt(preEnablePositon).setEnabled(false);

		// �������������Ǹ��㱻ѡ��
		ll.getChildAt(newPositon).setEnabled(true);

		preEnablePositon = newPositon;

	}

}
