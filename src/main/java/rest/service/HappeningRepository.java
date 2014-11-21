package rest.service;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import rest.domain.Happening;

@RepositoryRestResource(collectionResourceRel = "happening", path = "happening")
public interface HappeningRepository extends PagingAndSortingRepository<Happening, Long> {
}
