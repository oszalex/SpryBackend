package rest;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import rest.items.Act;

import java.util.List;

/**
 * Created by chris on 31/10/14.
 */

@RepositoryRestResource(collectionResourceRel = "act", path = "act")
public interface ActRepository extends PagingAndSortingRepository<Act, Long> {

    List<Act> findByDatetime(@Param("name") String name);

}
