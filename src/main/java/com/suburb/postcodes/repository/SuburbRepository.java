package com.suburb.postcodes.repository;

import com.suburb.postcodes.entity.Suburb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface SuburbRepository extends JpaRepository<Suburb, Long> {

    boolean existsByName(String name);

    Set<Suburb> findAllByNameIn(Set<String> names);

    @Query(value = """
            select s.name
            from suburb s,
                 postcode p,
                 postcode_suburb ps
            where s.id = ps.suburb_id
            and ps.postcode_id = p.id
            and p.code between ?1 and ?2""", nativeQuery = true)
    Set<String> findAllSuburbNamesByPostcodesRange(Short from, Short to);
}
