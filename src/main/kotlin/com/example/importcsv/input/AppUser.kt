package com.example.importcsv.input

import org.springframework.jdbc.core.BeanPropertyRowMapper

class AppUser(
    var id: Int? = null,
    var name: String? = null,
) {
    companion object{
        val appUserRowMapper = BeanPropertyRowMapper<AppUser>(AppUser::class.java)
    }
}
