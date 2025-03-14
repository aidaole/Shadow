# Shadow

## 介绍

这个项目是 fork 自腾讯的开源框架 Shadow, 源地址: [https://github.com/Tencent/Shadow](https://github.com/Tencent/Shadow)

本项目的目的是简化一下shadow的使用, 并记录一下使用中遇到的问题, 原项目中结构还是相对比较复杂

## 编译通过

clone 项目之后, 如果你的Android Studio比较新, 默认使用的gradle编译环境应该是jdk17, 需要修改为 jdk 11才能编译, 修改位置:

![](./pics/CleanShot%202025-03-14%20at%2014.53.53@2x.png)

修改之后应该就可以直接 Sync 通过, 可以直接跑起来 `sample-app` 了

## 运行一个自己的宿主和插件

在简单的印象中, 插件化就应该是一个宿主,对应多个插件的结构, 第一眼使用Shadow的时候还是有点懵, 不知道应该怎么操作. 可以参考源地址: [README](./projects/sample/README.md)

我们这里以不修改 Shadow 框架的情况, 直接依赖对应产物的方式来实现1个宿主对应2个插件的例子

### 1. 编译自己的shadow sdk版本

参考 [README](./projects/sample/README.md), 修改 `buildScripts/maven.gradle` 中的 `coreGroupId` 和 `dynamicGroupId`, 然后将 `mavenLocal()` 替换为 `maven { url = "./sdk" }` 
可以将编译的产物就放到当前项目目录下的 sdk 文件夹下, 可以看到两个库 `core` 和 `dynamic`, 
当然也可以什么都不修改直接发布, 包名用的就是tencent的

