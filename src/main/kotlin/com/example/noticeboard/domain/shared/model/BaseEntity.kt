package com.example.noticeboard.domain.shared.model

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
class BaseEntity {

    @Column(updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    var lastModifiedAt: LocalDateTime = LocalDateTime.now()
        protected set
}
