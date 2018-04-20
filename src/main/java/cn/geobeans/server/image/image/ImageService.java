package cn.geobeans.server.image.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class ImageService {

    @Autowired
    ImageRepo repo;

    public Image save(Image image){
        return  repo.save(image);
    }

    public Iterable<Image> getAll(){
        return repo.findAll();
    }

    public long countAll(){
        return repo.count();
    }
}
