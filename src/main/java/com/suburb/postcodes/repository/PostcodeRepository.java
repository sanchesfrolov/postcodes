package com.suburb.postcodes.repository;

import com.suburb.postcodes.entity.Postcode;
import com.suburb.postcodes.entity.Suburb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface PostcodeRepository extends JpaRepository<Postcode, Long> {
    boolean existsByCode(short code);

    Postcode findByCode(Short code);

    @Query("select s.suburbs from Postcode s where s.code between ?1 and ?2")
    Set<Suburb> findAllByCodeBetween(Short from, Short to);
}
