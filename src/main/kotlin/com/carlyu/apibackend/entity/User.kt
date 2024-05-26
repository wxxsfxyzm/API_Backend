package com.carlyu.apibackend.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "user_table")
data class User(
    @Id
    @GeneratedValue
    var id: UUID? = null,

    @Column
    var username: String = "",

    @Column
    var password: String = "",

    @Column(name = "google_api_key")
    var googleApiKey: String = "",

    @Column(name = "google_session_is_active")
    var googleSessionIsActive: Boolean = false,

    @Column(name = "google_text_history", columnDefinition = "LONGTEXT")
    var googleTextHistory: String = "",

    @Column(name = "google_system_instruction", columnDefinition = "LONGTEXT")
    var googleSystemInstruction: String = "",

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "generation_config_id")
    var generationConfig: GoogleAIUserConfig? = null,


    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, mappedBy = "user")
    var safetySettings: MutableList<GoogleAIUserSafetySettings> = mutableListOf()
) {
    override fun toString(): String {
        return "GoogleAIUserConfig(id=$id, userName='$username')"
    }
}