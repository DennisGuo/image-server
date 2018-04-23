# Image Server

基于HTTP 的图片存储服务

[下载地址](https://github.com/DennisGuo/image-server/releases)

解压后的目录：

```sh
imageserver
    ├── application.yml
    ├── image-server.jar
    ├── startup.bat
    └── startup.sh

```

## 配置文件

- `application.yml`配置文件中
    －　`app.store.path` 设置图片存放在磁盘的路径

## 启动方式

- 预先安装`java`执行环境 `jre` 或者 `jdk` `7.x` 已上
- 修改存路径配置
- `linux` 执行  `startup.sh`
- `window` 执行 `startup.bat`
    
## 文件存储规则

在`app.store.path`目录下会存在一下内容

```sh
├── h2data.mv.db
└── images
    └── 20180420
        ├── 1524210523437.jpg
        └── 1524210523437_thumb.jpg

```

- `h2data.mv.db` 用于存储图片属性和路径信息
- `images` 目录根据图片上传时间按月份文件夹存放图片文件
- `_thumb` 后缀的文件是图片的缩略图

## API

- [ v1.0.0 api](./api-v1.0.0.md)
- [ v1.1.0 api](./api-v1.1.0.md)
    - 修改图片信息上传接口，所有信息字段存入`content`属性中，可以包括任意多的信息：`json`格式