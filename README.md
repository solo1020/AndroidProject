Android:
====
四大组件：
---
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
2.

局域网多客户端群聊天app  

取消控件文字大写：
在value/styles.xml中添加：  
<item name="android:textAllCaps">false</item>  
