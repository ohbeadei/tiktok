package com.example.douyinexperience // 保持你的包名

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.douyinexperience.R
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private val dataList = ArrayList<ExperienceItem>() // 存放数据的列表
    private lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. 找到控件
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val swipeRefresh = findViewById<SwipeRefreshLayout>(R.id.swipeRefresh)

        // 2. 设置瀑布流布局 (2列)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        // 防止图片加载时跳动
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView.layoutManager = layoutManager

        // 3. 绑定适配器
        adapter = MyAdapter(dataList)
        recyclerView.adapter = adapter

        // 4. 生成初始数据
        generateMockData(true)

        // 5. 下拉刷新逻辑
        swipeRefresh.setOnRefreshListener {
            // 模拟网络延迟 1秒
            Handler(Looper.getMainLooper()).postDelayed({
                generateMockData(true) // true表示清空旧数据
                swipeRefresh.isRefreshing = false // 停止转圈
            }, 1000)
        }

        // 6. 上拉加载更多逻辑
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // 简单的判断：如果在底部滚动
                if (!recyclerView.canScrollVertically(1)) {
                     // 模拟加载更多
                     generateMockData(false) // false表示追加数据
                }
            }
        })
    }

    // 造假数据的方法
    private fun generateMockData(isRefresh: Boolean) {
        if (isRefresh) {
            dataList.clear()
        }

        // 每次造 10 条数据
        for (i in 0..9) {
            // 随机高度 (400到800之间)，这是瀑布流好看的关键
            val randomHeight = (400..800).random()
            
            // 使用 Picsum 提供的随机图片服务
            val imgUrl = "https://picsum.photos/400/$randomHeight?random=${System.currentTimeMillis() + i}"
            
            val item = ExperienceItem(
                title = "这是第 ${dataList.size} 条经验分享，你可以多写点字看看效果。",
                imageUrl = imgUrl,
                avatarUrl = "https://picsum.photos/100/100?random=${i}", // 随机头像
                userName = "用户 ${dataList.size}",
                likeCount = (0..999).random(),
                isLiked = false,
                height = randomHeight
            )
            dataList.add(item)
        }
        
        // 告诉适配器数据变了，赶紧刷新界面
        adapter.notifyDataSetChanged()
    }
}