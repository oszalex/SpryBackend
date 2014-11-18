package rest.service;

import org.springframework.data.repository.PagingAndSortingRepository;
import rest.domain.Invitation;

/**
 * Created by chris on 31/10/14.
 */


/**
 * curl --user 4369911602033:1234 http://localhost:8080/happening
 */
//@RepositoryRestResource(collectionResourceRel = "happening", path = "happening")
public interface InvitationRepository extends PagingAndSortingRepository<Invitation, Long> {

    //List<Happening> findByDatetime(@Param("name") String name);

}
