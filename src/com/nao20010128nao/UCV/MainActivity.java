package com.nao20010128nao.UCV;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.view.View.*;
import java.util.*;
import java.io.*;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
	EditText text;
	Button check;
	ListView details;
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		text=(EditText)findViewById(R.id.text);
		check=(Button)findViewById(R.id.check);
		details=(ListView)findViewById(R.id.details);
		check.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				char[] ct=text.getText().toString().toCharArray();
				details.setAdapter(new InternalListAdapter(ct));
			}
		});
    }
	class InternalListAdapter extends ArrayAdapter<Character>{
		public InternalListAdapter(char[] a){
			super(MainActivity.this,0,list(a));
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			// TODO: Implement this method
			if(convertView==null)
				convertView=getLayoutInflater().inflate(R.layout.component,null);
			TextView bigchar=(TextView)convertView.findViewById(R.id.bigchar);
			TextView utf8cp=(TextView)convertView.findViewById(R.id.utf8cp);
			TextView charcode=(TextView)convertView.findViewById(R.id.charcode);
			char data=getItem(position);
			bigchar.setText(new String(new char[]{data}));
			utf8cp.setText("UTF-8:"+utf8(data));
			charcode.setText("UTF-16:"+((int)data));
			return convertView;
		}
	}
	public int utf8(char data){
		try{
			byte[] b=new String(new char[]{data}).getBytes("UTF-8");
			switch (b.length){
				case 1:return b[0];
				case 2:return b[0] | b[1] << 8;
				case 3:return b[0] | b[1] << 8 | b[2] << 16;
				case 4:return b[0] | b[1] << 8 | b[2] << 16 | b[3] << 24;
				default:return 0;
			}
		}catch (UnsupportedEncodingException e){
			e.printStackTrace();
			return 0;
		}
	}
	public List<Character> list(char[] c){
		ArrayList<Character> list=new ArrayList<>();
		for(char d:c)list.add(d);
		return list;
	}
}
