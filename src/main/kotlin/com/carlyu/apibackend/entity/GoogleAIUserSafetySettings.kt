package com.carlyu.apibackend.entity

import com.nimbusds.jose.shaded.gson.annotations.Expose
import jakarta.persistence.*

@Entity
@Table(name = "google_user_safety_settings_table")
data class GoogleAIUserSafetySettings(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "safety_setting_id_seq")
    @SequenceGenerator(name = "safety_setting_id_seq", allocationSize = 1)
    @Expose
    var id: Long = 0,

    @Column
    @Expose
    var category: String = "",

    @Column
    @Expose
    var threshold: String = "",

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    var user: User? = null
)