# FABMenu

引用：
Step 1. Add it in your root build.gradle at the end of 

    repositories:
    	allprojects {
    		repositories {
    			...
    			maven { url 'https://jitpack.io' }
    		}
    	}
  
Step 2. Add the dependency

	dependencies {
	compile 'com.github.corgi7donkey:FABMenu:v1.1'
	}
	
	
![](https://github.com/corgi7donkey/FABMenu/tree/master/mylibrary/src/main/res/drawable/imag.gif)
  
  ```
<com.sjb.bupt.mylibrary.FloatingActionMenu
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:fab_expandDirection="down"
        app:add_fab_src="@drawable/add"
        app:add_fab_color="#1743f5"
        >
       <android.support.design.widget.FloatingActionButton
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:fabSize="mini"
            app:backgroundTint="#fff"/>
       <android.support.design.widget.FloatingActionButton
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:fabSize="mini"
            app:backgroundTint="#fff"/>
       <android.support.design.widget.FloatingActionButton
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:fabSize="mini"
            app:backgroundTint="#fff"/>
</com.sjb.bupt.mylibrary.FloatingActionMenu>
```
