apkplugDemov1.6.3 2014-3-20

实现功能: 演示apkplug基本功能的demo

插件工程源码移交到  http://git.oschina.net/plug/apkplugBundles

请大家自行下载

app默认自动安装了9个插件，所以在启动是速度会比较慢。

app同时提供安装本地插件功能,开发者可以开发出自己的插件-->apk放入SD卡中-->用app找到apk-->安装成功运行

工程目录结构

根目录 
├ bundles – 插件项目 
├ libs – 存放项目引用的第三方包 
├ res – 存放工程用到的图片,布局 
├ src – 存放工程的包及java源码文件 
libs目录

libs目录用于存放项目引用的第三方jar包。

libs目录里的jar包文件：

libs 
├ armeabi-v7a-libndkoo.so –armv7a架构 apkplug的.so文件 
├ armeabi -libndkoo.so –arm架构 apkplug的.so文件 
├ x86 -libndkoo.so –x86架构 apkplug的.so文件 
├ Bundle1.5.6.jar –apkplug 的jar包 
├ android-support-v4.jar –android兼容的包 (插件工程中不可以再编译该包)

src目录

src目录用于存放工程的包及java源码文件。

下面是src目录的子目录：

src 
├ com.example.apkplugdemo –存放程序启动类 
├ com.apkplugdemo.adapter –存放适配器的实现类的包 
├ com.apkplugdemo.adapter.base –存放适配器基类的包 
├ com.apkplugdemo.FileUtil –文件操作包 
├ com.apkplugdemo.FileUtil.filter –过滤文件类型的包 
├ com.apkplugdemo.util –工具类包 
├ com.apkplugdemo.util.Observer –观测者模式 
├ com.apkplugdemo.util.preferencesFactory –preferences简单封装