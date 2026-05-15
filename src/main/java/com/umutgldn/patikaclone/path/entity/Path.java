package com.umutgldn.patikaclone.path.entity;

import com.umutgldn.patikaclone.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "paths")
public class Path extends BaseEntity{
    
    @Column(nullable = false,unique = true,length = 100)
    private String name;
}
