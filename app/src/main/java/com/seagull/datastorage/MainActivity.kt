package com.seagull.datastorage

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.seagull.datastorage.databinding.ActivityMainBinding
import java.io.*

/**
 * 使用文件 进行数据的本地存储
 * - 保存：在应用退出/应用切换后台的时候，把应用数据->本地(存储方式：文件)
 * - 读取：在应用进入/应用切换前台的时候，把本地数据->应用(读取方式：文件)
 *
 * 存储/读取：协议自己定
 *
 * -保存：两种方式（private + append）
 * 1. context.openFileOutput(name,mode)
 *      name:保存文件的名字
 *      mode：文件打开的方式
 *          - MODE_PRIVATE: 覆写
 *          - MODE_APPEND: 追加
 * 2. 保存位置：/data/data/包名/files
 *
 * -读取：
 * 1.context.openFileInput(name)
 *   套接成BufferReader -> ReadLine操作
 *
 *
 * demo: 写一个EditText，应用退出的时候，把Text文字保存至文件中；下次进入app的时候，将文字恢复
 *
 */

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //恢复/读取：将本地文件的内容读取并展现在EditText上
        try {
            val sb = StringBuilder()
            val reader = BufferedReader(InputStreamReader(openFileInput("edit_text.txt")))
            reader.use {
                reader.forEachLine {
                    sb.append(it)
                    sb.append("\n")
                }
            }
            if (sb.isNotEmpty()) {
                sb.deleteCharAt(sb.length - 1)
            }
            binding.saveEditText.setText(sb.toString())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //保存：把EditText文字读取并保存至本地文件中
        val text = binding.saveEditText.text.toString()

        //保存文件的形式：1.context.openFileOutput(name,mode)
        try {
            val writer = BufferedWriter(
                OutputStreamWriter(openFileOutput("edit_text.txt", Context.MODE_PRIVATE))
            )
            writer.use {
                //使用use可以把writer字节流写完关闭,就不需要finally代码块了
                it.write(text)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}