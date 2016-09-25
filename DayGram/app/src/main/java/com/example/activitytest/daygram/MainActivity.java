package com.example.activitytest.daygram;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {
    int[] daynum_month={31,30,31,30,31,30,31,31,30,31,30,31};
    private    ListView   mListView ;
    private DiaryAdapter adpter;
    Diary tddiary=new Diary(0,"","","","");//被操作的某天数据
    ArrayList<Diary> data = new ArrayList<Diary>(); // 当月数据
    int mday;//被操作日期

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mListView   =   (ListView)findViewById(R.id.list_view);
       data=(ArrayList<Diary>)getObject("object_2016_9");
       // data.set(2,new Diary(1,"2","9","WED","HAPPY"));
        //按照月份初始化，默认当前月


        adpter=new DiaryAdapter(this , data);
        mListView.setAdapter(adpter); //为ListView设置适配器
        //按下list中元素，进入编辑页面，传入对象
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mday = position;
                Intent intent = new Intent(MainActivity.this, editactivity.class);
                tddiary = data.get(mday);
                tddiary.setdaycount(getweek(2016,tddiary.getmonth(),tddiary.getdayNum()));
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("today", tddiary);
                intent.putExtras(mBundle);
                startActivityForResult(intent, 1);
            }
        });

        //点击加号，添加当天日记
        Button button_add=(Button)findViewById(R.id.title_add);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,editactivity.class);
                mday=getdaytime()-1;
                tddiary=data.get(mday);
                tddiary.setdaycount(getweek(2016,tddiary.getmonth(),tddiary.getdayNum()));
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("today",tddiary);
                intent.putExtras(mBundle);
                startActivityForResult(intent,1);
            }
        });

    }

    //返回主界面，传出日记内容
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent datait) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String new_diary = datait.getStringExtra("new_diary");
                    //Diary newdiary = new Diary();
                    //newdiary= (Diary) datait.getSerializableExtra("new_diary");
                    tddiary.setType(1);
                    tddiary.setdiaryText(new_diary);
                    data.set(mday,tddiary);
                    saveObject("object_2016_9");
                    adpter.notifyDataSetChanged();
                }
                break;
            default:
        }
    }

    //将数据保存到本地
    private void saveObject(String name){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = this.openFileOutput(name,MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
        } catch (Exception e) {
            e.printStackTrace();
            //这里是保存文件产生异常
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    //fos流关闭异常
                    e.printStackTrace();
                }
            }
            if (oos != null){
                try {
                    oos.close();
                } catch (IOException e) {
                    //oos流关闭异常
                    e.printStackTrace();
                }
            }
        }
    }

    private Object getObject(String name){
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = this.openFileInput(name);
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            //这里是读取文件产生异常
        } finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    //fis流关闭异常
                    e.printStackTrace();
                }
            }
            if (ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    //ois流关闭异常
                    e.printStackTrace();
                }
            }
        }
        //读取产生异常，返回null
        return null;
    }

    //初始化一个月数据
    private void tminit(int monthNum)
    {
        String emptystr="";
        String monthcount=String.valueOf(monthNum);
        int daymax=daynum_month[monthNum-1];
        for(int i=0;i<daymax;i++)
        {
            String daynum=String.valueOf(i+1);
            data.add(new Diary(0,daynum,monthcount,emptystr,emptystr));
        }
    }

    String getweek(int year,String wmonth,String wday)
    {
        String weekOfday[]={"SUN","MON","TUE","WED","THU","FRI","SAT"};
        int wmonthInt=Integer.parseInt( wmonth );
        int wdayInt=Integer.parseInt( wday);
        Calendar cal = Calendar.getInstance();
        cal.set(year,wmonthInt-1,wdayInt);//这里的月份是从0开始算起的
        Date dt = cal.getTime();
        cal.setTime(dt);
        int week_selected = cal.get(Calendar.DAY_OF_WEEK);
        return weekOfday[week_selected-1];
    }

    //得到今天日期
    private int getdaytime()
    {
        int mday;
        final Calendar c = Calendar.getInstance();
        mday=c.get(Calendar.DAY_OF_MONTH);
        return mday;
    }
}

