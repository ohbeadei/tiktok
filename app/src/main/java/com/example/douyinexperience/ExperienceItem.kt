package com.example.douyinexperience 

// 定义
data class ExperienceItem(
    val title: String,      // 标题
    val imageUrl: String,   // 大图链接
    val avatarUrl: String,  // 头像链接
    val userName: String,   // 用户名
    var likeCount: Int,     // 点赞数 
    var isLiked: Boolean,   // 是否点赞 
    val height: Int         // 图片高度 
)
