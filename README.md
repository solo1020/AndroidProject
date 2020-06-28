Android:
====
四大组件：Activity Service Broadcast Receiver Content Provider
---
SQLite </br>
活动intent:
---
主activity 须继承 AppCompatActivity 重写onCreate方法  
在重写方法中super 调用父类的onCreate  
设置当前activity的页面布局：setContentView(R.layout.布局页面名)  


创建按钮对象：Button btn1 = (Button)findViewById(R.id.页面中设置的按钮id)  
设置按钮的点击监听事件 重写onclick()方法:   
```
btn1.setOnclickListener(new View.OnclickListenr(){
    @Override
    public void onClick(View v){
        // 点击事件中显式启动另一activity
        Intent intent = new Intent(MainAcitvity.this, SecondActivity.class);
        startActivity(intent);

    }
});
```
显示Toast提示：Toast.makeText(当前activity, "提示内容", Toast.LENGTH_SHORT).show();  

显式启动另一activity: startActivity(new Intent(当前activity.this, 目标activity.class))  

启动网络intent:  
Intent intent = new Intent(Intent.ACTION_VIEW);  
intent.setData(Uri.parse("http://www.baidu.com));

启动电话intent:  
Intent intent = new Intent(Intent.ACTION_DIAL);  
intent.setDate(Uri.parse("tel:10086));


```
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button)findViewById(R.id.button_1);
        // 绑定按钮监听事件
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "You clicked Button 1",
//                        Toast.LENGTH_SHORT).show();
//                finish();

                // 显式启动SecondActivity
//                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                // 隐式启动SecondActivity
//                Intent intent = new Intent("com.example.activitytest.ACTION_START");
//                intent.addCategory("com.example.activitytest.MY_CATEGORY");

//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("http://www.baidu.com"));

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:10086"));
                startActivity(intent);
            }
        });
    }
```

创建右上角菜单项：  
---
1.在res下新建menu文件夹  
2.New resource file  main.xml:  
在其中item就是菜单项
```
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">

    <item
        android:id="@+id/add_item"
        android:title="Add" />

    <item
        android:id="@+id/remove_item"
        android:title="Remove" />

</menu>
```

局域网多客户端群聊天app  

取消控件文字大写：
在value/styles.xml中添加：  
<item name="android:textAllCaps">false</item>  

向活动传递数据: intent.putExtra(key, value);

// 启动活动并在目标活动销毁时返回结果给当前活动  
startActivityForResult(intent,1);  

销毁活动并返回数据给前一活动：  
```
Intent intent = new Intent();
                intent.putExtra("data_return", "hello MainActivity");
                setResult(RESULT_OK, intent);
                finish();
```
因为是使用startActivityForResult()来启动的  
所以在目标活动销毁后会回调 本活动的 onActivityResult()方法  
所以需要在 本活动中重写该方法：  
```
@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String returnData = data.getStringExtra("data_return");
                    Log.d(TAG, returnData);
                }
                break;
            default:
                break;
        }
    }
```

Activity生命周期：  
---
onCreate() onStart(变为可见) onResume(继续 恢复另一活动时) onPause(暂停) onStop() onDestroy()  

活动被回收：
---
为了防止后台活动被回收时丢失临时数据和状态  
使用 onSaveInstanceState()回调方法  
保证在活动被回收之前一定被调用  
```
@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String tempData = "something you just typed";
        outState.putString("data_key", tempData);
    }
```
这样将临时数据保存到onSaveInstanceState中  
并可以在 活动再次启动时 存取该状态：  
```
if(savedInstanceState != null){
            String tempData = savedInstanceState.getString("data_key");
            Log.d(TAG,tempData);
        }
```