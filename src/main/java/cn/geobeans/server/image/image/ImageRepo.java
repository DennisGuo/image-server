package cn.geobeans.server.image.image;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ImageRepo extends PagingAndSortingRepository<Image,Long> {
}
