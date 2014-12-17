package com.gospry.api.service;

import com.gospry.api.domain.Happening;
import com.gospry.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "happening", path = "happening")
public interface HappeningRepository extends JpaRepository<Happening, Long> {
    List<Happening> findByCreator(User creator);
    List<Happening> findByIsPublic(boolean bool);
}
