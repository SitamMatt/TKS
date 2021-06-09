package repositories.library.mappers

import core.domain.common.valueobjects.AccessionNumber
import core.domain.resource.Book
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class BookMapperTest {
    private val book = Book(AccessionNumber("EEEE-221"), "Pan Tadeusz", false, "Adam Mickiewicz")

    @Test
    fun bookToEntityTest(){
        val dto = book.toEntity()
        dto.accessionNumber shouldBe book.accessionNumber?.value
        dto.title shouldBe book.title
        dto.locked shouldBe book.locked
        dto.id shouldBe 0
    }

    @Test
    fun bookEntityToDomainTest() {
        val dto = book.toEntity()
        val domain = dto.toDomain()

        if (domain != null) {
            domain.accessionNumber shouldBe book.accessionNumber
            domain.locked shouldBe book.locked
            domain.title shouldBe book.title
        }
    }
}