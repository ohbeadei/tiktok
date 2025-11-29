# 高仿抖音“经验”频道 (Android 考核作业)

## 项目简介
本项目基于 Android 原生开发 (Kotlin + XML)，实现了抖音“经验”频道的双列瀑布流界面。项目包含核心的卡片展示、网络图片加载、点赞交互以及下拉刷新/上拉加载更多功能。

## 功能列表
- [x] **双列瀑布流布局**：支持不同高度图片的错落展示。
- [x] **经验卡片 UI**：包含封面图、标题、用户头像、用户名、点赞数。
- [x] **点赞交互**：点击心形图标切换实心/空心状态，数字实时增减。
- [x] **数据刷新**：支持下拉刷新重置数据，滑动到底部自动加载模拟数据。
- [x] **网络资源**：使用 Glide 加载网络图片，支持圆角头像处理。

## 技术栈
- **语言**：Kotlin
- **架构**：单 Activity + RecyclerView
- **UI 组件**：ConstraintLayout, CardView, SwipeRefreshLayout
- **图片加载**：Glide 4.16
- **列表管理**：StaggeredGridLayoutManager (瀑布流)

## 开发总结

在开发过程中，我主要解决了以下三个技术难点：

### 1. 瀑布流布局的“图片跳动”与错位问题
**问题描述**：在使用 `StaggeredGridLayoutManager` 时，由于网络图片异步加载，ImageView 初始高度为 0。当图片加载完成后，布局高度发生变化，导致 item 位置重新计算，出现视觉上的跳动和列表重排。
**优化思路**：
- 采用 **"预设高度占位"** 策略。
- 在数据模型 (`ExperienceItem`) 生成时，预先随机生成图片的高度值。
- 在 `onBindViewHolder` 中，在图片加载**之前**，先通过 `layoutParams.height` 强制设置 ImageView 的高度。这样无论图片何时加载完成，布局空间已经固定，彻底解决了跳动问题。

### 2. RecyclerView 复用机制导致的点赞状态错乱
**问题描述**：快速滑动列表时，新出现的 Item 复用了滚出屏幕的 ViewHolder。如果没有正确重置状态，新 Item 可能会错误地显示旧 Item 的“已点赞”红色心形。
**优化思路**：
- 严格遵循 **"视图与数据绑定"** 原则。
- 在 `onBindViewHolder` 方法中，不依赖视图的默认状态，而是每次都根据当前数据对象的 `isLiked` 字段，显式地设置 `setImageResource`（实心或空心）和颜色过滤器。确保 View 的状态永远由 Model 决定。

### 3. 滑动流畅度与内存优化
**问题描述**：加载大量高分辨率网络图片时，列表滑动出现卡顿。
**优化思路**：
- 引入 **Glide** 图片加载库。利用其内置的三级缓存机制（内存、磁盘、网络）。
- 在 `StaggeredGridLayoutManager` 配置中，设置 `gapStrategy = GAP_HANDLING_NONE`，减少不必要的布局重绘计算，提升滑动帧率。

## 运行截图
![6a2e7a44d31e53b2fe27acc201deccb5](https://github.com/user-attachments/assets/6f107804-eb80-4b3b-a747-a6fe4ed29647)
