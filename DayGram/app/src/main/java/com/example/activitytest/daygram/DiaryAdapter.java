package com.example.activitytest.daygram;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import com.example.activitytest.daygram.Diary ;

/**
 * Created by apple on 2016/9/23.
 */

public class DiaryAdapter extends BaseAdapter {
    private List<Diary> mData;       //创建Diary类型的List表
    private LayoutInflater mInflater;               //定义线性布局过滤器

    public DiaryAdapter(Context context , List<Diary> data){
        this.mData = data ;
        mInflater = LayoutInflater.from(context);       //获取布局
    }
    /**
     * 得到列表长度
     * @return
     */
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;    //得到子项位置id
    }


    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getItemViewType(int position){
        Diary    diarymem    =   mData.get(position);
        return diarymem.getType();
    }

    @Override
    public int getViewTypeCount(){
        return 2 ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (getItemViewType(position) == 0) {      //如果类型是0
            //通过LayoutInflater实例化布局
            convertView = mInflater.inflate(R.layout.dot_item, null);
            return convertView;
        } else {
            ViewHolder holder;
            //判断是否缓存
           /* if (convertView == null) {
            /*if(getItemViewType(position)  ==  0) {      //如果类型是0
                //通过LayoutInflater实例化布局
                holder  =   new ViewHolder() ;
                convertView = mInflater.inflate(R.layout.dot_item, null);
                holder.daycount= (TextView) convertView.findViewById(R.id.count_list);
                holder.daynum= (TextView) convertView.findViewById(R.id.num_list);
                holder.diarycontent= (TextView) convertView.findViewById(R.id.content_list);
            }
            else{*/
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item, null);
            //绑定id
            holder.daycount = (TextView) convertView.findViewById(R.id.count_list);
            holder.daynum = (TextView) convertView.findViewById(R.id.num_list);
            holder.diarycontent = (TextView) convertView.findViewById(R.id.content_list);
            convertView.setTag(holder);         //为View设置tag

        /*else {
                holder = (ViewHolder) convertView.getTag();      //通过tag找到缓存的布局
            }*/
            //设置布局中控件要显示的视图
            holder.daycount.setText(mData.get(position).getdaycount());
            holder.daynum.setText(mData.get(position).getdayNum());
            holder.diarycontent.setText(mData.get(position).getdiaryText());
            return convertView;     //返回一个view
        }

    }

    /**
     * 实体类
     */
    public final class ViewHolder{
        public TextView daycount;
        public TextView daynum;
        public TextView diarycontent;

    }
}



