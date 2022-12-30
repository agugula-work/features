package com.gugula.features.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PERMISSION")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;

    @Column(name = "NAME", unique = true, nullable = false)
    String name;

    @Builder.Default
    @Column(name = "GLOBAL", nullable = false)
    Boolean global = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return name.equals(that.name) && global.equals(that.global);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, global);
    }
}
