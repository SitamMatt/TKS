package repositories.library.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Book(@Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                var id: Long,
                var title: String,
                var pages: Int = 0)