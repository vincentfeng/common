###logback + mongodb
通过Appender 把日志写入 mongodb

网上是有一些方案，例如 logback contrib，但是配合新的mongodb 的jar，会出错。主要错误是在mongoclient ，所以从先编写了一个mongoclient 的 manager 。

后期版本会改进，目前先这样子吧。