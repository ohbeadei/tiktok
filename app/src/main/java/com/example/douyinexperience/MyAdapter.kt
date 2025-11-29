package com.example.douyinexperience 

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.douyinexperience.R
import java.util.ArrayList

class MyAdapter(private val dataList: ArrayList<ExperienceItem>) : 
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    // 1. 定义这里面有哪些控件
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage: ImageView = view.findViewById(R.id.ivImage)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val ivAvatar: ImageView = view.findViewById(R.id.ivAvatar)
        val tvUser: TextView = view.findViewById(R.id.tvUser)
        val ivLike: ImageView = view.findViewById(R.id.ivLike)
        val tvLikeCount: TextView = view.findViewById(R.id.tvLikeCount)
    }

    // 2. 创建样子
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_experience, parent, false)
        return MyViewHolder(view)
    }

    // 3. 绑定数据
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = dataList[position]

        // 设置高度实现瀑布流
        val params = holder.ivImage.layoutParams
        params.height = item.height // 设置成我们在数据里随机生成的高度
        holder.ivImage.layoutParams = params

        //加载文字
        holder.tvTitle.text = item.title
        holder.tvUser.text = item.userName
        holder.tvLikeCount.text = item.likeCount.toString()

        // 加载图片 
        Glide.with(holder.itemView)
            .load(item.imageUrl)
            .into(holder.ivImage)
            
        // 头像切圆角
        Glide.with(holder.itemView)
            .load(item.avatarUrl)
            .transform(CircleCrop())
            .into(holder.ivAvatar)

        //  处理点赞逻辑
        if (item.isLiked) {
            holder.ivLike.setImageResource(R.drawable.ic_heart_filled) 
            holder.ivLike.setColorFilter(Color.RED)
        } else {
            holder.ivLike.setImageResource(R.drawable.ic_heart_outline)
            holder.ivLike.clearColorFilter()
        }

        //点击点赞
        holder.ivLike.setOnClickListener {
            // 改变状态
            if (item.isLiked) {
                item.isLiked = false
                item.likeCount -= 1
            } else {
                item.isLiked = true
                item.likeCount += 1
            }
            // 通知这一行刷新
            notifyItemChanged(position)
        }
    }

    override fun getItemCount() = dataList.size
}
