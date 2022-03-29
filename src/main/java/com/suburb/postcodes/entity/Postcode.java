package com.suburb.postcodes.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "postcode")
public class Postcode {

    public Postcode(Short code) {
        this.code = code;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", unique = true, updatable = false)
    private Short code;

    @ManyToMany
    @JoinTable(name = "postcode_suburb",
            joinColumns = @JoinColumn(name = "postcode_id"),
            inverseJoinColumns = @JoinColumn(name = "suburb_id"))
    private Set<Suburb> suburbs = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Postcode postcode = (Postcode) o;
        return code.equals(postcode.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }


}
