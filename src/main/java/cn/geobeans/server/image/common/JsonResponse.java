package cn.geobeans.server.image.common;

import lombok.Data;

@Data
public class JsonResponse {

    private Boolean success;
    private String message;
    private Object data;

    public JsonResponse() {
    }

    public JsonResponse(Object data) {
        this.success = true;
        this.data = data;
    }

    public JsonResponse(String message) {
        this.message = message;
    }

    public JsonResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public JsonResponse(Boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

}
