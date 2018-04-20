package cn.geobeans.server.image.store;

import org.springframework.web.multipart.MultipartFile;


public interface StorageService {

    String store(MultipartFile file);

    String getRootLocation();
}
