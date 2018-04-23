package cn.geobeans.server.image.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Page<Image> getPage(Pageable page){
        return repo.findAll(page);
    }

    public long countAll(){
        return repo.count();
    }
}
