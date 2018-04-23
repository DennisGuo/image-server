# Image Server Version 1.1.0 API

其他接口不变，与 [v1.0.0 api](./api-v1.0.0.md) 一致；

## 主要变化接口如下：

- `/image` 图片信息保存
    - HTTP POST
    - 图片信息使用json数据上传`content-type:application/json`
    - 图片信息上传json格式：
    ```json
    {
        
        "path":"/20180419/1524141827445.jpeg",
        "pathThumbnail":"/20180419/1524141827445_thumb.jpeg",
        "content":{
          "source":"测绘局",
          "level":"零级产品",
          "coordinate":[118.00018387498755,38.99993870833748,119.00018796109839,39.999942794448316]
        }        
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
            "content":{
              "source":"测绘局",
              "level":"零级产品",
              "coordinate":[118.00018387498755,38.99993870833748,119.00018796109839,39.999942794448316]
            },
            "createTime": "2018-04-23T01:47:43.270+0000"
        }
    }
    ```
- `/image?page={n}&size={m}&sort={xxx.desc,xxx.asc}` 分页查询图片数据
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
                    "content":{
                      "source":"测绘局",
                      "level":"零级产品",
                      "coordinate":[118.00018387498755,38.99993870833748,119.00018796109839,39.999942794448316]
                    }    
                }              
            ]
        }
    }
    
    ```