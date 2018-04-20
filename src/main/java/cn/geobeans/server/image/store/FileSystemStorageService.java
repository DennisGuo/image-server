package cn.geobeans.server.image.store;


import cn.geobeans.server.image.common.ImageUtil;
import cn.geobeans.server.image.config.AppProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.OneToMany;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class FileSystemStorageService implements StorageService {

    private static final Logger log = LoggerFactory.getLogger(FileSystemStorageService.class);

    private final String rootLocation;

    @Autowired
    public FileSystemStorageService(AppProperty properties) {
        this.rootLocation = properties.getStorePath()+"/images";
    }

    @Override
    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file " + file.getOriginalFilename());
            }
            String oName = file.getOriginalFilename();
            String[] arr = oName.split("\\.");
            String type = arr[arr.length-1];
            Calendar cal = Calendar.getInstance();
            String date = String.format("%s%s%s",
                    cal.get(Calendar.YEAR),
                    new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1),
                    new DecimalFormat("00").format(cal.get(Calendar.DATE))
                    );
            String dir = String.format("%s/%s/", rootLocation, date);
            String name = String.format("%s.%s",new Date().getTime(),type);

            if(!new File(dir).exists()){
                Files.createDirectories(Paths.get(dir));
            }

            Files.copy(file.getInputStream(), Paths.get(dir+name));

            log.info("store image to : " + dir+name);

            return "/"+date+"/"+name;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public String getRootLocation() {
        return rootLocation;
    }

    @Override
    public File getByImagePath(String imagePath) {
        return new File(rootLocation+imagePath);
    }
}