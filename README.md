# 在线教育系统

## 系统介绍

该项目是一个前后端分离，后端使用SpringBoot，前端使用Vue和Element-UI开发。

针对当前学习通主观题判题功能和AI问答功能的缺失提出解决方案

## 特色功能

###主观题判题 

通过连接AI智能体进行判题并生成反馈内容，让学生更好的了解自己的学习情况。

流程如图所示：

![](M:\SpringBoot-Vue-OnlineEdu\markdownImage\aiJudgeFlowchart.png)

效果：

<img title="" src="file:///M:/SpringBoot-Vue-OnlineEdu/markdownImage/answerEffectDrawing.png" alt="" data-align="inline">

###AI对话

构建支持openAI接口的对话模块，实现对话请求的转发与对话消息存储

针对题目的AI对话

![](M:\SpringBoot-Vue-OnlineEdu\markdownImage\qusAichatEffectDrawing.png)

开放性场景AI对话

![](M:\SpringBoot-Vue-OnlineEdu\markdownImage\openChatEffectDrawing.png)

## 页面截图

#### 学生端：

课程页面

![](M:\SpringBoot-Vue-OnlineEdu\markdownImage\courseEffectDrawing.png)

学习页面

![](M:\SpringBoot-Vue-OnlineEdu\markdownImage\studyEffectDrawing.png)

练习选项卡页面

![](M:\SpringBoot-Vue-OnlineEdu\markdownImage\testEffectDrawing.png)

练习详情页面

![](M:\SpringBoot-Vue-OnlineEdu\markdownImage\testInfoEffectDrawing.png)

答题页面

![](M:\SpringBoot-Vue-OnlineEdu\markdownImage\doTestEffectDrawing.png)

#### 教师端：

章节/任务点发布界面(科目模块)

![](M:\SpringBoot-Vue-OnlineEdu\markdownImage\chapterEffectDrawing.png)

章节/任务点管理界面(科目模块)

![](M:\SpringBoot-Vue-OnlineEdu\markdownImage\taskEffectDrawing.png)

练习发布界面(科目模块)

![](M:\SpringBoot-Vue-OnlineEdu\markdownImage\testReleaseEffectDrawing.png)

题库管理界面(科目模块)

![](M:\SpringBoot-Vue-OnlineEdu\markdownImage\queHubEffectDrawing.png)

## 有待完善的地方

本项目仍处于实验性开发阶段，未实现教务管理端、未完全实现在线教育平台全场景功能、也没有使用针对主观题判题训练的AI模型。

## 结语

本项目是本人的本科毕业作品，起因是在跟老师的交谈中了解到在目前学习通没有针对主观题的自动判题功能，每到期末都要面临很大的改卷压力，如果能引入AI进行判题能够极大的减轻工作量。

本项目花费了我两个半月的时间进行系统设计和编码实现，争取获得优秀毕业论文，但由于种种原因未能实现，目前作者面临巨大的就业压力，无力再对系统进行开发，遂开源到github以作纪念。
