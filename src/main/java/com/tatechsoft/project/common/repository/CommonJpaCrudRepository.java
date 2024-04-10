package com.tatechsoft.project.common.repository;

import com.tatechsoft.project.common.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface CommonJpaCrudRepository<T extends BaseEntity, ID extends Serializable> extends JpaRepository<T, ID> {

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    @Query("select e from #{#entityName} e where e.isDeleted <> 'Y' ")
    List<T> findAll();

    @Query("select e from #{#entityName} e where e.isDeleted = 'N' ")
    List<T> findAllFilterActive();

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    @Query("select count(1) from #{#entityName} e where e.isDeleted <> 'Y'")
    long count();

    @Modifying
    @Query(value = "update  #{#entityName} e set e.isDeleted = 'Y' where e.id=?1 ")
    void deleteSoft(long id);
}