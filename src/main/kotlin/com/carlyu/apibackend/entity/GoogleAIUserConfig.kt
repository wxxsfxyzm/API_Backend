package com.carlyu.apibackend.entity

import jakarta.persistence.*

@Entity
@Table(name = "google_ai_config_table")
data class GoogleAIUserConfig(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "google_ai_config_id_seq")
    @SequenceGenerator(name = "google_ai_config_id_seq", allocationSize = 1)
    var id: Long = 0,

    @Column(name = "temperature")
    var temperature: Double = 1.00,

    @Column(name = "candidate_count")
    var candidateCount: Int = 1,

    @Column(name = "top_p")
    var topP: Double = 1.00,

    @Column(name = "top_k")
    var topK: Int = 64,

    @OneToOne(mappedBy = "googleAIUserConfig")
    var user: User? = null
)