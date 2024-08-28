package com.gaotianchi.resourceservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ImageEntity {

    @Id
    @GeneratedValue
    private Long id;

}
