package fillers

import model.UserRole
import model.User
import java.util.*

class NewUsersFiller {
    fun fill(): List<User> {
        val result = ArrayList<User>()
        result.add(User().apply {
            guid = UUID.fromString("f9b3a442-b637-4e23-ad71-910ee816453e")
            isActive = true
            role = UserRole.CLIENT
            firstname = "Łukasz"
            lastname = "Stanisławowski"
            login = "testo"
            password = "test0"
        })
        result.add(User().apply {
            guid = UUID.fromString("48bb061d-0a01-4f60-bdfc-f6bac839b107")
            isActive = true
            role = UserRole.CLIENT
            firstname = "Jayne"
            lastname = "Najera"
            login = "user"
            password = "user0"
        })
        result.add(User().apply {
            guid = UUID.randomUUID()
            isActive = false
            role = UserRole.WORKER
            firstname = "Adam"
            lastname = "Mickiewicz"
            login = "worker1"
            password = "worker1"
        })
        result.add(User().apply {
            guid = UUID.fromString("411fc900-f762-4081-90b6-e17fb32d127f")
            isActive = true
            role = UserRole.WORKER
            firstname = "John"
            lastname = "Cena"
            login = "worker"
            password = "worker0"
        })
        result.add(User().apply {
            guid = UUID.fromString("3fbabdb6-7a44-4b9e-be8d-dd120a271b5b")
            isActive = true
            role = UserRole.ADMIN
            firstname = "Janusz"
            lastname = "Pawlacz"
            login = "admin"
            password = "admin0"
        })
        result.add(User().apply {
            guid = UUID.fromString("9c47f5f4-cbdc-414d-8f17-3c4b035f8899")
            isActive = true
            role = UserRole.CLIENT
            firstname = "Mateusz"
            lastname = "Szewc"
            login = "matt"
            password = "1234"
        })
        result.add(User().apply {
            guid = UUID.fromString("3fbdbdb6-7a44-4b9e-be8d-dd120a271b5b")
            isActive = true
            role = UserRole.ADMIN
            firstname = "Sebas"
            lastname = "Chan"
            login = "root"
            password = "root"
        })
        return result
    }
}