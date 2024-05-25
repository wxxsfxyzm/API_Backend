package com.carlyu.apibackend.entity

import com.nimbusds.jose.shaded.gson.annotations.Expose
import jakarta.persistence.*

@Entity
@Table(name = "google_ai_config_table")
data class GoogleAIUserConfig(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "google_ai_config_id_seq")
    @SequenceGenerator(name = "google_ai_config_id_seq", allocationSize = 1)
    @Expose
    var id: Long = 0,

    @Column(name = "temperature")
    @Expose
    var temperature: Double = 1.00,

    @Column(name = "candidate_count")
    @Expose
    var candidateCount: Int = 1,

    @Column(name = "top_p")
    @Expose
    var topP: Double = 1.00,

    @Column(name = "top_k")
    @Expose
    var topK: Int = 64,

    @OneToOne(mappedBy = "generationConfig")
    var user: User? = null
) {
    override fun toString(): String {
        return "GoogleAIUserConfig(id=$id, temperature=$temperature, candidateCount=$candidateCount, topP=$topP, topK=$topK)"
    }
}