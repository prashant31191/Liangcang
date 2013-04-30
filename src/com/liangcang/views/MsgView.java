package com.liangcang.views;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangcang.R;
import com.liangcang.base.MyBasePageAdapter;
import com.liangcang.mode.Good;
import com.liangcang.util.ImageDownloader;
import com.liangcang.util.RichText;
import com.liangcang.util.Util;
import com.liangcang.weigets.LoadMoreListView;
import com.liangcang.weigets.LoadMoreListView.LoadCallBack;

public class MsgView extends BaseView {

	private boolean isHadLoad=false;
	public void ifLoadMoreNotData() {
		if(!isHadLoad)
		{
			onRefresh();
		}
		
	}
	@Override
	public void onRefresh() {
		isHadLoad=true;
		adapter.onRefresh();
	}
	private MyBasePageAdapter<Good> adapter;

	public MsgView(final Context mContext) {
		super(mContext);
		LoadMoreListView list = new LoadMoreListView(mContext);
		list.setDividerHeight(0);
		setContentView(list);
		list.setOnLoadCallBack(new LoadCallBack() {
			
			@Override
			public void onLoading() {
				adapter.loadMore();
				
			}
		});
		isHadLoad=false;
		adapter = new MyBasePageAdapter<Good>(mContext) {

			@Override
			public String path() {
				return "notifications";
			}

			@Override
			public void onReceiveSuccess() {

			}

			@Override
			public void onReceiveFailure(String msg) {

			}

			@Override
			public boolean isDoGet() {
				return true;
			}

			@Override
			public Class<Good> getModeClass() {
				return Good.class;
			}

			@Override
			public View bindView(int position, Good t, View view) {
				if (view == null) {
					view = getLayoutInflater().inflate(R.layout.msg_layout,
							null);

				}
				ImageView msgUser = (ImageView) view
						.findViewById(R.id.msg_userImage);
				ImageView msg_userGoodImage = (ImageView) view
						.findViewById(R.id.msg_userGoodImage);
				ImageDownloader.getInstance().download(t.getUser_image(),
						msgUser);
				ImageDownloader.getInstance().download(t.getGoods_image(),
						msg_userGoodImage);

				TextView tv = (TextView) view.findViewById(R.id.msg_content);
				RichText rt = new RichText(MsgView.this.mContext);
				switch (t.getType()) {
				case 1:
					rt.addTextColor(t.getUser_name(), mContext.getResources()
							.getColor(R.color.blue));
					rt.addTextColor("评论了我的良品："+t.getMsg(), mContext
							.getResources().getColor(R.color.white));
					break;
				case 2:
					rt.addTextColor(t.getUser_name(), mContext.getResources()
							.getColor(R.color.blue));
					rt.addTextColor("喜欢了我的良品："+t.getMsg(), mContext
							.getResources().getColor(R.color.white));
					break;
				case 3:
					rt.addTextColor(t.getUser_name(), mContext.getResources()
							.getColor(R.color.blue));
					rt.addTextColor("回复了我的评论："+t.getMsg(), mContext
							.getResources().getColor(R.color.white));
					break;
				case 4:
					rt.addTextColor("您的良品 ", mContext
							.getResources().getColor(R.color.white));
					rt.addTextColor(t.getGoods_name(), mContext.getResources()
							.getColor(R.color.blue));
					rt.addTextColor(" 被选入了良品精选", mContext
							.getResources().getColor(R.color.white));
					break;
				case 5:
				default:
					rt.addTextColor(t.getMsg(), mContext
							.getResources().getColor(R.color.white));
				
					break;
				}
				
				tv.setText(rt);

				return view;
			}
		};
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Util.gotoComment(MsgView.this.mContext, "");
			}
		});
	}

}
