package fillers

import model.Book
import model.Magazine
import model.Resource
import java.util.*

class NewResourcesFiller {
    fun fill() : List<Resource>{
        val result = ArrayList<Resource>()
        result.add(Book().apply {
            guid= UUID.fromString("1514f5ae-f54d-4b4f-ac97-97f32fe18cb0")
            title = "Hurry Potter"
            pagesCount = 231
            publishingHouse = "Big J Publish"
            author = "J.K Rollin"
        })
        result.add(Book().apply {
            guid= UUID.fromString("6d094ca9-94d1-480f-96ca-fce609ac4c44")
            title = "Wieśmin"
            pagesCount = 333
            publishingHouse = "MegaNova"
            author = "Andrew Sapkowsky"
        })
        result.add(Magazine().apply {
            guid= UUID.fromString("e80e22a3-5399-4a63-bbf1-b6afc9646380")
            title = "Las Palmas"
            pagesCount = 23
            publishingHouse = "Maga Zin"
            issueDate = "2017/05/01/K/1"
        })
        result.add(Book().apply { guid= UUID.fromString("d929c7e8-cd04-4296-be9e-d890e9fcf19e")
            title = "O cudownym kebabie"
            pagesCount = 666
            publishingHouse = "House of orcs"
            author = "Norbert Gierczyk"
        })
        result.add(Magazine().apply { guid= UUID.fromString("f4308c34-4df3-48fe-a830-90ad4946acab")
            title = "Las Palmas"
            pagesCount = 21
            publishingHouse = "Maga Zin"
            issueDate = "2019/05/01/S/1"
        })
        result.add(Magazine().apply { guid= UUID.fromString("5af7025e-12a4-4726-925d-b7de8f9def94")
            title = "Świat sportu"
            pagesCount = 17
            publishingHouse = "Big J Publish"
            issueDate ="2018/05/01/M/1"
        })
        result.add(Book().apply { guid= UUID.fromString("c8168b00-b3e9-42da-afb9-1be9c44ceb44")
            title = "Subway 2033"
            pagesCount = 231
            publishingHouse= "MegaNova"
            author = "Dimitri Deafosky"
        })
        return result
    }
}