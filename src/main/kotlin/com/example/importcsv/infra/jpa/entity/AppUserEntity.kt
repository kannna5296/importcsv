package com.example.importcsv.infra.jpa.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "app_user")
class AppUserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id :Int? = null,
    var name: String? = null,
)