package cn.geobeans.server.image.store;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;


public interface StorageService {

    String store(MultipartFile file);

    String getRootLocation();

    File getByImagePath(String imagePath);
}
