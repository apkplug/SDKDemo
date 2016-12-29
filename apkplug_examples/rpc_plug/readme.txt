这个插件提供一个服务个宿主,用于实现具体的登陆操作

开发步骤:

1.导入osgi3.9.0.jar包
2.实现插件入口类 SimpleBundle
3.实现RPC服务类 LoginRPC
4.创建插件配置文件assets/plugin.xml
5.通过plugin.xml注册apkplug://rpc/login服务并使这个服务与LoginRPC类绑定

还用到的技巧:
1. org.apkplug.Bundle.bundlerpc.ObjectPool 可以帮助用来在Intent中传递不可序列化的java对象

2. 将宿主中的rpc/...目录下的接口文件拷贝到插件中

但你可能发现宿主的rpc目录下的接口类与插件的rpc目录下的接口类根本完全不一样*就连包名都不同*。
但他们仍然能正常通讯,这就是rpc的优点,可让您不用考虑宿主与插件的类冲突问题