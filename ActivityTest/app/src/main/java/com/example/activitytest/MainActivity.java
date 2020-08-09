package com.example.activitytest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            String tempData = savedInstanceState.getString("data_key");
            Log.d(TAG,tempData);
        }

        // 点击切换图片资源
        imageView = (ImageView)findViewById(R.id.image_view);
        Button button = (Button)findViewById(R.id.changeImg);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                imageView.setImageResource(R.drawable.img_2);
            }
        });


        Button button1 = (Button)findViewById(R.id.button_1);
        // 绑定按钮监听事件
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "You clicked Button 1",
//                        Toast.LENGTH_SHORT).show();
//                finish();

                // 显式启动SecondActivity
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                // 隐式启动SecondActivity
//                Intent intent = new Intent("com.example.activitytest.ACTION_START");
//                intent.addCategory("com.example.activitytest.MY_CATEGORY");

                //
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("http://www.baidu.com"));

//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:10086"));

                // putExtra 附带数据给传递给下一个活动的intent
//                String data = "hello SecondActivity";
//                intent.putExtra("extra_data", data);

                // 启动活动并在目标活动销毁时返回结果给当前活动
                startActivityForResult(intent,1);
//                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String returnData = data.getStringExtra("data_return");
                    Toast.makeText(this, returnData,Toast.LENGTH_SHORT).show();
                    Log.d(TAG, returnData);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String tempData = "something you just typed";
        outState.putString("data_key", tempData);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.add_item:
                Toast.makeText(this,"You clicked Add", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this, "You clicked Remove", Toast.LENGTH_SHORT).show();
                break;
            case R.id.hideToIntent:
                intent = new Intent("com.example.activitytest.ACTION_START");
                startActivity(intent);
                break;
            case R.id.openBrowser:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
                break;
            case R.id.callPhoneNumber:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:10086"));
                startActivity(intent);
                break;
            default:
                Toast.makeText(MainActivity.this, "there are no such id",
                        Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onStop() {
        super.onStop();
        MainActivity.this.finish();
    }
}
