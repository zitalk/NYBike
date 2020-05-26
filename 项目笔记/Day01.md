# NYBike：

## 课程目标：

- 大数据概念
- 掌握大数据的数据平台技术（存储+计算）
  - 基本概念和原理
  - 常用操作命令

## 统一开发环境：

- JDK1.8
  - cmd    （java -version）验证一下![image-20200525100029894](https://img.99couple.top/20200525100038.png)
- IDE：Eclipse/IDEA（为了配合教学老师的要求，这里采用eclipse，方便答辩时跑代码）
- 服务器：Tomcat8.5（解压即用，在eclipse中配置）
- jar包管理平台：Maven3.6.2（解压即用，需将远程库更改为阿里云，在eclipse中进行关联）
- MySQL5.5

Eclipse有很多的开发版本：只进行JavaSE应用开发的版本，进行JavaEE应用开发的版本，其他开发的版本...

Sun公司设计了Java的3个版本：JavaSE(桌面应用程序)，JavaEE(Web应用程序)，JavaME(嵌入式开发)

- Eclipse关联本地JDK：Windows->Preferences->搜索jre->Installed JRES->查看当前关联的location是否指向jdk的文件夹，实际指向的是jre的文件夹，需要选中该项的配置，点击右侧的edit，进行编辑。
- Eclipse关联本地Tomcat：Windows->Preferences->搜索server->选择server下的Runtime Environments->在右侧列表中通过add按钮添加一个新的server配置，选择对应好的版本即可。
- 修改Tomcat的server locations，改成use tomcat installation，保存。这样做是为了打包部署到Tomcat上面时文件结构更佳清晰。
- Eclipse关联本地Maven：将Maven解压缩到本地。
  - 对Maven进行配置
    - 指定本地库（conf/settings.xml）
    - 修改镜像
    - 修改JDK默认版本，Maven是默认的jdk1.4，我们用的1.8
  - 然后还是跟上面一样的搜索，搜索maven->user settings然后绑定刚才写好的settings.xml即可，update setting看看下面的地址是不是自己变成了你自己设置的.m2/repository。
- ![](https://img.99couple.top/20200525202330.png)

## 新建项目：

1. 新建一个Maven Project，勾选上create a simple ...省去模板选择，再设置公司名，项目名，打包方式即可。我们开发的是web项目，所以选war方式。
2. 新建的web项目会默认报错，因为缺少web.xml文件。可以通过Eclipse快速生成一个xml文件，右键Deployment Descriptor->选择Generate xxxx那个在webapp/web-inf下自动生成web.xml文件。
3. ![](https://img.99couple.top/20200525202411.png)
   - 常见问题：
     - 打包方式有问题，这个需要删除项目重新来，记得勾选delete源文件，否则只是在eclipse列表中删掉了。新建时会提示已经存在，无法创建。
     - 左上角视图是Package Explorer而不是Project Explorer，也会少Deployment Descriptor这一行。
     - pom.xml第一行上来就报错，是因为eclipse会自动检查xml文件是否正确，检查时需要基于一个模板，网络不好会导致模板无法下载，导致eclipse检查出问题。但是这个问题不影响项目运行，可以忽略。
     - 如是缺少必要的项目结构，是网络波动导致Maven加载jar包或是项目模板出问题，我们右键项目--Maven---update---勾选上forcexxx那个选项，重新强制加载项目。
4. 在Deployed Resources/webapp下创建一个新的html文件来看看项目能否正常跑起来。

## 需求分析：

- 原则：环境越接近实际应用，记忆效果越久。
- 需求：想要开发国内某城市的共享单车地图服务平台，目前没有国内的数据。因此对标纽约市共享单车地图服务[纽约共享单车地图]("https://member.citibikenyc.com/map/")，开发一个一样的项目。
- 问题：需要实现哪些功能/效果？
  - 地图，在网页上显示地图
  - 显示当前已存在站点
  - 点击站点，显示实时情况，如可用车和可用桩，站点名称
  - 根据站点颜色实时显示可用车情况
  - 在部分站点可以提供电动车/充电站的站带图标旁加以特殊标志区分
  - 添加可用车和可用桩的切换图标，点击切换可以呈现出不同的显示，更好的满足是用车还是还车
  - 当缩放级别改变时，图标显示方式自动切换，仍要以颜色加以区分
- 拓展：
  - 基于当前位置自动推荐最优站点
  - 是否显示自行车专用车道

# 补充知识：

## Web：

- Web的发展：计算机（单机系统，仅仅用来计算）---局域网--->多台计算机通过网络进行连接进行信息传递交互--互联网（海底光缆）-->多台电脑访问一台服务器（也是电脑）
- Web的概念：web是利用互联网进行信息交互的一套完整的解决方案，其中包括htpp协议，文档格式等。
- IP：每个电脑在网络中的地址
- 协议：电脑之间交互时遵循的规范
- 域名：IP地址的字符串表示
- DNS解析：根据域名查询IP地址
- ![](https://img.99couple.top/20200525142756.png)

## 架构：

- B/S架构：通过浏览器Broswer访问服务器Server，通过浏览器访问的即为B/S，
  - 优劣：
    - 方便，不需要下载客户端，电脑都会内置浏览器程序没打开就可以访问
- C/S架构：通过客户端Client访问服务器Server，通过APP访问的即为C/S，
  - 优劣：
    - 客户端功能强大美视觉效果好

## 服务器：

![](https://img.99couple.top/20200525142845.png)

## Maven：

Maven是当前最为流行的项目管理工具，提供项目的快速构建、依赖jar包管理，在本项目中，会使用到Maven的项目构建、依赖jar包管理、打包等功能。

![](https://img.99couple.top/20200525142933.png)

