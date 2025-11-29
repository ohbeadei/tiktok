package com.example.douyinexperience // 你的包名，保持原样不要动

// 这是定义一张卡片里有什么数据
data class ExperienceItem(
    val title: String,      // 标题
    val imageUrl: String,   // 大图链接
    val avatarUrl: String,  // 头像链接
    val userName: String,   // 用户名
    var likeCount: Int,     // 点赞数 (用var因为会变)
    var isLiked: Boolean,   // 是否点赞 (用var因为会变)
    val height: Int         // 图片高度 (为了瀑布流高低错落)
)
