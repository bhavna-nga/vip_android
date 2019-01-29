package com.zorad.vip;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TreeSet;

public class ChatAdapter extends ArrayAdapter<EachMessage> {
	Context context;
	int layoutuserResourceId;
	int layoutClientResourceId;
	ArrayList<EachMessage> data = null;
	private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();
	static String previousDate = "", currentDate = "";
	private static final int TYPE_ITEM = 0;
	private static final int TYPE_SEPARATOR = 1;

	public ChatAdapter(Context context, int layoutuserResourceId,
			int layoutClientResourceId, ArrayList<EachMessage> data) {

		super(context, layoutuserResourceId, layoutClientResourceId, data);

		this.context = context;
		this.layoutuserResourceId = layoutuserResourceId;
		this.layoutClientResourceId = layoutClientResourceId;
		this.data = data;
		this.previousDate = "";
		this.currentDate = "";
		this.sectionHeader.clear();
	}

	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			if (data != null && data.size() > position) {
				if (!data.get(position).isClient) {
					LayoutInflater inflater = (LayoutInflater) context
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					convertView = inflater.inflate(layoutuserResourceId,
							parent, false);
				} else {
					LayoutInflater inflater = (LayoutInflater) context
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					convertView = inflater.inflate(layoutClientResourceId,
							parent, false);
				}
			}
		}

		if (data != null && data.size() > position) {
			EachMessage mycontact = data.get(position);
			int rowType = getItemViewType(position);
			if (!mycontact.isClient) {
				EachUserMessageHolder holder = null;
				if (convertView == null
						|| !(convertView.getTag() instanceof EachUserMessageHolder)) {
					LayoutInflater inflater = (LayoutInflater) context
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					convertView = inflater.inflate(layoutuserResourceId,
							parent, false);

					holder = new EachUserMessageHolder();
					holder.message = (TextView) convertView
							.findViewById(R.id.txt_user_chat);
					holder.time = (TextView) convertView
							.findViewById(R.id.txt_user_time);
					holder.date = (TextView) convertView
							.findViewById(R.id.txt_chat_date);
					convertView.setTag(holder);
				} else {
					holder = (EachUserMessageHolder) convertView.getTag();
				}
				Log.d("date:", "date_user:" + mycontact.date);
				holder.date.setText(mycontact.date);
				currentDate = mycontact.date;
				if (rowType == TYPE_ITEM) {
					holder.date.setVisibility(View.GONE);
				} else {
					holder.date.setVisibility(View.VISIBLE);
				}
				holder.time.setText(mycontact.time);
				holder.message.setText(mycontact.message.trim());
				if (mycontact.message.trim().length() == 0) {
					holder.message.setVisibility(View.GONE);
				} else {
					holder.message.setText(mycontact.message.trim());
					holder.message.setVisibility(View.VISIBLE);
				}
			} else {

				EachClientMessageHolder holder = null;

				if (convertView == null
						|| !(convertView.getTag() instanceof EachClientMessageHolder)) {
					LayoutInflater inflater = (LayoutInflater) context
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					convertView = inflater.inflate(layoutClientResourceId,
							parent, false);

					holder = new EachClientMessageHolder();
					holder.message = (TextView) convertView
							.findViewById(R.id.txt_client_chat);
					holder.time = (TextView) convertView
							.findViewById(R.id.txt_client_time);
					holder.date = (TextView) convertView
							.findViewById(R.id.txt_chat_date);
					convertView.setTag(holder);
				} else {
					holder = (EachClientMessageHolder) convertView.getTag();
				}
				Log.d("date:", "date_Client:" + mycontact.date);
				holder.date.setText(mycontact.date);
				currentDate = mycontact.date;
				if (rowType == TYPE_ITEM) {
					holder.date.setVisibility(View.GONE);
				} else {
					holder.date.setVisibility(View.VISIBLE);
				}
				holder.time.setText(mycontact.time);
				holder.message.setText(mycontact.message.trim());
				if (mycontact.message.trim().length() == 0) {
					holder.message.setVisibility(View.GONE);
				} else {
					holder.message.setText(mycontact.message.trim());
					holder.message.setVisibility(View.VISIBLE);
				}
			}
		}
		return convertView;
	}

	public void addSectionHeaderItem(final String item) {

		for (int i = 0; i <= GlobalArrayList.message2.size(); i++) {
			try{
			if (i==(GlobalArrayList.message2.size()) && (!GlobalArrayList.message2.get(i-1).date.contains(item))) {
				sectionHeader.add(GlobalArrayList.message2.size());
				notifyDataSetChanged();
				break;
			}
			}catch(IndexOutOfBoundsException e){
				sectionHeader.clear();
				sectionHeader.add(GlobalArrayList.message2.size());
				notifyDataSetChanged();
				break;
			}
		}
		
		Log.d("section header", ""+sectionHeader);
//		sectionHeader.add(GlobalArrayList.message2.size());
//		notifyDataSetChanged();
	}

	@Override
	public int getItemViewType(int position) {
		return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
	}

	@Override
	public int getCount() {
		if (data != null) {
			return data.size();
		} else {
			return 0;
		}
	}

	static class EachClientMessageHolder {
		TextView message;
		TextView time;
		TextView date;
	}

	static class EachUserMessageHolder {
		TextView message;
		TextView time;
		TextView date;
	}

}
