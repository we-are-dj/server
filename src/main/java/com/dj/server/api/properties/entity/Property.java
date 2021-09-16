package com.dj.server.api.properties.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@NoArgsConstructor
@Table
public class Property {

    @Id
    @Column
    private Integer propKey;

    @Column(length = 100)
    private String propValue;

    @Column(length = 70)
    private String propContent;

}
