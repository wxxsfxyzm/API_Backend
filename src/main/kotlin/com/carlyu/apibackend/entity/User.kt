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

    @Column(name = "google_text_history")
    var googleTextHistory: String = "",

    @Column(name = "google_system_instruction")
    var googleSystemInstruction: String = "",

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "generation_config_id")
    var generationConfig: GoogleAIUserConfig? = null,


    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "user")
    var safetySettings: List<GoogleAIUserSafetySettings> = mutableListOf()
)