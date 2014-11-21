package com.gospry.api.service;

import com.gospry.api.domain.Happening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "happening", path = "happening")
public interface HappeningRepository extends JpaRepository<Happening, Long> {
}
