# Shadow

## 介绍

这个项目是 fork 自腾讯的开源框架 Shadow, 源地址: [https://github.com/Tencent/Shadow](https://github.com/Tencent/Shadow)

本项目的目的是简化一下shadow的使用, 并记录一下使用中遇到的问题, 原项目中结构还是相对比较复杂

## 编译通过

clone 项目之后, 如果你的Android Studio比较新, 默认使用的gradle编译环境应该是jdk17, 需要修改为 jdk 11才能编译, 修改位置:

![](./pics/修改gradle11.png)

修改之后应该就可以直接 Sync 通过, 可以直接跑起来 `sample-app` 了

## 运行一个自己的宿主和插件

在简单的印象中, 插件化就应该是一个宿主,对应多个插件的结构, 第一眼使用Shadow的时候还是有点懵, 不知道应该怎么操作. 可以参考源地址: [README](./projects/sample/README.md)

我们这里以不修改 Shadow 框架的情况, 直接依赖对应产物的方式来实现1个宿主对应2个插件的例子

### 1. 编译自己的shadow sdk版本

参考 [README](./projects/sample/README.md), 修改 `buildScripts/maven.gradle` 中的 `coreGroupId` 和 `dynamicGroupId` 将发布包名修改为自己的
当然也可以什么都不修改直接发布, 包名默认用的就是tencent的

在根目录下执行 `./gradlew publish` 可以在maven local中看到生成的产物, 主要是 `core` 和 `dynamic` 

### 2. 添加宿主工程

在项目中的 demo 目录下, 我添加了 `demo-host` 作为宿主, 代码可以直接参考 `projects/sample/maven/host-project` 下的代码
主要依赖 `com.tencent.shadow.dynamic:host:2.3.0`

### 3. 创建插件工程

这里需要注意, 插件中下载器(plugin-manager)和插件的代码是分开的, 所以我们先创建插件下载器. 我这里采用的结构是 宿主一个module, 插件的都放在一个文件夹下,如下:

```
demo
    - demo-host
    - demo-plugin1
        - plugin1-manager # 插件下载器
        
        - plugin1-app     # 插件需要拆分成 app, loader, runtime
        - plugin1-loader
        - plugin1-runtime
```

`plugin1-manager` 里面的代码需要注意: 这个类下面的包名是不能修改的 :[ManagerFactoryImpl.java](demo/demo-plugin1/plugin1-manager/src/main/java/com/tencent/shadow/dynamic/impl/ManagerFactoryImpl.java)



