package repositories.library.mappers

import core.domain.common.valueobjects.AccessionNumber
import core.domain.resource.Book
import core.domain.resource.Magazine
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class BookMapperTest {
    private val book = Book(AccessionNumber("EEEE-221"), "Pan Tadeusz", false, "Adam Mickiewicz")
    private val magazine = Magazine(AccessionNumber("MMMM-102"), "Super Gigant", false, "Nowa Era")

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

    @Test
    fun magazineToEntityTest(){
        val dto = magazine.toEntity()
        dto.accessionNumber shouldBe magazine.accessionNumber?.value
        dto.title shouldBe magazine.title
        dto.locked shouldBe magazine.locked
        dto.id shouldBe 0
    }
}