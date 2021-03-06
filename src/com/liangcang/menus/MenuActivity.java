package com.liangcang.menus;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.liangcang.R;
import com.liangcang.activits.DaRenActivity;
import com.liangcang.activits.HomeActivity;
import com.liangcang.activits.MessageActivity;
import com.liangcang.activits.MySelfActivity;
import com.liangcang.base.IActivityGroup;
import com.liangcang.base.MyApplication;
import com.liangcang.util.MyLog;
import com.liangcang.util.MyToast;
import com.liangcang.util.Util;
import com.umeng.analytics.MobclickAgent;

public class MenuActivity extends IActivityGroup implements OnClickListener {
	private String TAG = "MenuActivity";
	private LinearLayout /* linearCenter, */linearBottomLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyLog.e(TAG, "md5=" + Util.getMD5Str("2"));
		initView();
		linearBottomLayout = (LinearLayout) findViewById(R.id.mainBottomLayout);
		linearBottomLayout.removeAllViews();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		params.weight = 1;
		for (int i = 0; i < 5; i++) {
			View view = getBottomMenu(i);
			if (view != null) {
				view.setId(100 + i);
				linearBottomLayout.addView(view, params);
				view.setOnClickListener(this);
			}
		}
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {

		switch (event.getKeyCode()) {
		case KeyEvent.KEYCODE_BACK:

			break;

		default:
			break;
		}

		return super.dispatchKeyEvent(event);
	}

	private boolean isLogin() {
		MyApplication app = (MyApplication) getApplication();
		return app.isLogined();
	}

	private static int currentIndex = 0;

	protected void switchView(int position) {
		currentIndex = position;
		MyLog.e(TAG, "currentIndex=" + currentIndex);
		if (position >= 0 && position < 5) {
			switchMenuBg(position);
		}
		switchIntent(position);

	}

	public View getBottomMenu(int position) {
		View view = getLayoutInflater().inflate(R.layout.menu_item, null);
		ImageView btn = (ImageView) view.findViewById(R.id.menu_btn);
		switch (position) {
		case 0:
			btn.setImageResource(R.drawable.menu1);
			break;
		case 1:
			btn.setImageResource(R.drawable.menu2);
			break;
		case 2:
			btn.setImageResource(R.drawable.menu3);
			break;
		case 3:
			btn.setImageResource(R.drawable.menu4);
			break;
		case 4:
			btn.setImageResource(R.drawable.menu5);
			break;
		default:
			break;
		}
		// btn.setImageResource(R.drawable.tuijian);
		// TextView tv = (TextView) view.findViewById(R.id.menu_title);
		// tv.setText(getResources().getStringArray(R.array.centerText)[position]);
		return view;
	}

	private void switchMenuBg(int position) {
		for (int i = 0; i < 5; i++) {
			if (position == i)
				continue;
			View v = linearBottomLayout.getChildAt(i);
			ImageView btn = (ImageView) v.findViewById(R.id.menu_btn);
			switch (i) {
			case 0:
				btn.setImageResource(R.drawable.menu1);
				break;
			case 1:
				btn.setImageResource(R.drawable.menu2);
				break;
			case 2:
				btn.setImageResource(R.drawable.menu3);
				break;
			case 3:
				btn.setImageResource(R.drawable.menu4);
				break;
			case 4:
				btn.setImageResource(R.drawable.menu5);
				break;
			default:
				break;
			}
		}
		ImageView btn = (ImageView) linearBottomLayout.getChildAt(position)
				.findViewById(R.id.menu_btn);
		switch (position) {
		case 0:
			btn.setImageResource(R.drawable.menu1_selected);
			break;
		case 1:
			btn.setImageResource(R.drawable.menu2_selected);
			break;
		case 2:
			btn.setImageResource(R.drawable.menu3_selected);
			break;
		case 3:
			btn.setImageResource(R.drawable.menu4_selected);
			break;
		case 4:
			btn.setImageResource(R.drawable.menu5_selected);
			break;
		default:
			break;
		}
	}

	void initView() {

		switchIntent(0);

	}

	@Override
	public void onClick(View v) {
		if (v.getId() >= 100 && v.getId() < 5 + 100) {
			switchView(v.getId() - 100);
			return;
		}
		switch (v.getId()) {

		case R.id.btn_menuExit:
			// MyApplication.clearUser(this);
			Util.gotoLogin(this);
			finish();
			break;
		case R.id.btn_menuSearch:
			switchIntent(R.id.btn_menuSearch);
			break;
		case R.id.btn_menuSetting:
			switchIntent(R.id.btn_menuSetting);
			break;
		case R.id.btn_menudaren:
			switchView(R.id.btn_menudaren);
			break;
		case R.id.btn_menuHeart:
			switchView(R.id.btn_menuHeart);
			break;
		default:
			break;
		}

	}

	@Override
	protected void onResume() {

		super.onResume();
	}

	// TODO
	private void setLeftImage(int id) {

	}

	void switchIntent(int i) {
		Intent intent = new Intent();
		switch (i) {

		case 0:
			intent.setClass(this, HomeActivity.class);
			MobclickAgent.onEvent(this, "HomeActivity");
			setLeftImage(R.drawable.left_menu1);
			break;
		case 1:

			MyToast.showMsgLong(this, "没有专题");
			return;
		case 2:
			intent.setClass(this, DaRenActivity.class);
			MobclickAgent.onEvent(this, "DaRenActivity");
			setLeftImage(R.drawable.left_menu1);
			break;
		case 3:
			if (!MyApplication.isLogin()) {
				Util.gotoLogin(this);
				return;
			}
			setLeftImage(-1);
			intent.setClass(this, MessageActivity.class);
			MobclickAgent.onEvent(this, "MessageActivity");
			break;
		case 4:
			if (!MyApplication.isLogin()) {
				Util.gotoLogin(this);
				return;
			}
			setLeftImage(-1);
			intent.setClass(this, MySelfActivity.class);
			MobclickAgent.onEvent(this, "MySelfActivity");
			break;
		default:
			setLeftImage(-1);
			intent.setClass(this, RecommendActivity.class);
			MobclickAgent.onEvent(this, "RecommendActivity");
			break;
		}

		// getLocalActivityManager().getActivity("subActivity" + i);
		Window subActivity = getLocalActivityManager().startActivity(
				"subActivity" + i, intent);
		// linearCenter.removeAllViews();
		setContentView(subActivity.getDecorView());
	}

	@Override
	public void onClickRightButton() {
		Intent intent = new Intent();
		intent.setClass(this, SettingActivity.class);
		startActivity(intent);
	}

	public void onClickRightTwoButton() {

	}

	@Override
	public void setCurrentTitleString() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClickLeftButton() {
		if (currentIndex == 0) {

		} else if (currentIndex == 3) {

		}

	}

}