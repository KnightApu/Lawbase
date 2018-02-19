package com.mongo.media;

import java.util.List;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "media", path = "media")
public interface MediaRepository extends MongoRepository<Media, Long> {

	public Media findById(Long id);

	public Media findByName(String name);

	public long countByMimeType(@Param("mimeType") String mimeType);

	public List<Media> findByMimeType(String mimeType);

}
