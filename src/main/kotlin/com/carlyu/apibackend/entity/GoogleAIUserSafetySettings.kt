package com.carlyu.apibackend.entity

import jakarta.persistence.*

@Entity
@Table(name = "google_user_safety_settings_table")
data class GoogleAIUserSafetySettings(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "safety_setting_id_seq")
    @SequenceGenerator(name = "safety_setting_id_seq", allocationSize = 1)
    var id: Long = 0,

    @Column
    var category: String = "",

    @Column
    var threshold: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null
)