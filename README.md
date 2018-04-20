# Image Server

基于HTTP 的图片存储服务

## 配置文件

- `application.yml`配置文件中
    －　`app.store.path` 设置图片存放在磁盘的路径
    
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
- `images` 目录根据图片上次时间按月份文件夹存放图片文件
- `_thumb` 后缀的文件是图片的缩略图

## API

- `/image/upload?width=?` 图片上传服务
    - HTTP POST
    - 参数`width`是用于生成缩略图的宽度，默认值：256
    - 图片文件使用表单方式提交`content-type: multipart/form-data`，字段名为`file`，
    - 返回结果：
    
    ```json
    {
        "success": true,
        "message": null,
        "data": {
            "path": "/20180419/1524141827445.jpeg",
            "pathThumbnail": "/20180419/1524141827445_thumb.jpeg"
        }
    }
    ```
    
- `/image` 图片信息保存
    - HTTP POST
    - 图片信息使用json数据上传`content-type:application/json`
    - 图片信息上传json格式：
    ```json
    {
        "source":"测绘局",
        "level":"零级产品",
        "path":"/20180419/1524141827445.jpeg",
        "pathThumbnail":"/20180419/1524141827445_thumb.jpeg",
        "coordinate":[118.00018387498755,38.99993870833748,119.00018796109839,39.999942794448316]
    }
    ```
    - 返回结果：
    ```json
    {
        "success": true,
        "message": null,
        "data": {
            "id": 1,
            "path": "/20180419/1524141827445.jpeg",
            "pathThumbnail": "/20180419/1524141827445_thumb.jpeg",
            "source": "测绘局",
            "level": "零级产品",
            "coordinate": [118.00018387498756,38.99993870833748,119.00018796109839,39.999942794448316]},
            "createTime": "2018-04-19T13:04:42.089+0000"
        }
    }
    ```
- `/image?page=?&size=?&sort=?` 分页查询图片数据
    - HTTP GET    
    - `page` 分页，起始值为 `0`
    - `size` 每页数量，默认 `10`
    - `sort` 排序字段，示例:`sort=createTime.desc`
    - 返回结果：
    ```json
    {
        "success": true,
        "message": null,
        "data":{
            "total": 1,
            "totalPage": 1,
            "rows":[
                {
                "id": 1,
                "path": "/20180420/1524210523437.jpg",
                "pathThumbnail": "/20180420/1524210523437_thumb.jpg",
                "source": "测绘局",
                "level": "零级产品",
                "createTime": "2018-04-20T07:53:34.652+0000",
                "coordinate":[118.00018387498756, 38.99993870833748, 119.00018796109839, 39.999942794448316]
                }              
            ]
        }
    }
    
    ```
    
- `/image/{path}` 返回图片信息
    - HTTP GET
    - `path` 接口返回的图片路径
    - 用法：
    ```html
    <img src="http://localhost:8888/image/20180420/1524210523437.jpg"/>
    <img src="http://localhost:8888/image/20180420/1524210523437_thumb.jpg"/>
    ```