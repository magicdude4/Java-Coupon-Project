package com.anton.api.dao;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "admin", path = "admin")
public interface AdminRepository {

}
