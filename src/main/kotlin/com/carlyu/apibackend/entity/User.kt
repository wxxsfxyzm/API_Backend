package com.carlyu.apibackend.entity

import jakarta.persistence.*

@Entity
@Table(name = "user_table")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "api_user_id_seq")
    @SequenceGenerator(name = "api_user_id_seq", allocationSize = 1)
    var id: Long = 0,

    @Column
    var username: String = "",

    @Column
    var password: String = "",

    @Column(name = "google_api_key")
    var googleApiKey: String = "",

    @Column(name = "google_session_is_active")
    var googleSessionIsActive: Boolean = false,

    @Column(name = "google_prompt")
    var googlePrompt: String = "",
)