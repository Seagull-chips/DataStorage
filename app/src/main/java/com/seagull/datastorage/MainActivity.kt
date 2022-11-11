package com.seagull.datastorage

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.seagull.datastorage.databinding.ActivityMainBinding

/**
 * 使用Shared Preferences 进行数据存储(轻量级数据存储)
 *
 * - 保存：
 *  1.context.getSharedPreferences(name,mode)
 *      name: sp的文件名字
 *      mode：sp的存储模式(MODE_PRIVATE)
 *  2.activity.getPreferences(mode)
 *      name就是默认的Activity的名字
 *
 *  获取到sp之后 -> sp.edit() editor对象 ->利用editor。putString、putInt... -> editor.apply()
 *
 * - 读取：
 *  1.sp = context.getSharedPreferences(name,mode)
 *  2.sp = activity.getPreferences(mode)
 *
 *  获取sp之后 -> sp.getString/getInt getFloat
 *
 * 使用文件存储：文件读写协议由我们自己去确定
 *
 * 设置：颜色主题为浅色，轻量级（颜色主题key： 浅色value）
 *
 * 适用：轻量级的数据存储
 * 存储模式： 键-值 对
 *
 * 存储位置：/data/data/包名/shared_prefs/test_sp.xml
 *
 * demo：保存用户的登陆状态
 */

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.textSave.setOnClickListener {
//            // sp的保存
//            val sp = getSharedPreferences("test_sp", Context.MODE_PRIVATE)
////            val sp = getPreferences(Context.MODE_PRIVATE)
//            val editor = sp.edit()
//            editor.putString("name", "张三")
//            editor.apply()
//        }
//
//        binding.textRead.setOnClickListener {
//            // sp的读取
//            val sp = getSharedPreferences("test_sp", Context.MODE_PRIVATE)
//            val content = sp.getString("age", "默认实现")
//            Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
//        }


        //先检查一下登录状态
        val sp = getSharedPreferences("login", Context.MODE_PRIVATE)
        val name = sp.getString("name", "")
        val passWord = sp.getString("passWord", "")
        binding.userName.setText(name)
        binding.userPassword.setText(passWord)

        if (sp.getBoolean("status", false)){
            binding.loginButton.isClickable = false
            binding.loginButton.isEnabled = false
            Toast.makeText(this, "你已经登陆成功", Toast.LENGTH_SHORT).show()
        }

        binding.loginButton.setOnClickListener {
            val name = binding.userName.text.toString()
            val passWord = binding.userPassword.text.toString()
            val editor = getSharedPreferences("login", Context.MODE_PRIVATE).edit()
            if (name == "张三" && passWord == "123") {
                //登陆成功：将登陆保存至sp中
                editor.putBoolean("status", true)
                Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show()
            } else {
                editor.putBoolean("status", false)
                Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show()
            }
            editor.putString("name", name)
            editor.putString("passWord", passWord)

            editor.apply()
        }
    }

}