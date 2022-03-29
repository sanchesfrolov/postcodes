package com.suburb.postcodes.repository;

import com.suburb.postcodes.entity.Postcode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostcodeRepository extends JpaRepository<Postcode, Long> {
    boolean existsByCode(short code);

    Postcode findByCode(Short code);
}
