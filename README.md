# Image Server

基于HTTP 的图片存储服务

## API

- `/image/upload?width={xxx}` 图片上传服务
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
    - 返回信息：
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
            "xx": 118.00018387498756,
            "xy": 38.99993870833748,
            "yx": 119.00018796109839,
            "yy": 39.999942794448316,
            "createTime": "2018-04-19T13:04:42.089+0000"
        }
    }
    ```