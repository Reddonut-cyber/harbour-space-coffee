package space.harbour.kotlin.menu

import org.springframework.web.bind.annotation.*

data class MenuItem(
    val id: Int,
    var name: String,
    var price: Double,
    var description: String? = null,
    var category: String? = null,
    var available: Boolean = true
)

@RestController
@RequestMapping("/menu")
class MenuController {
    private val items = mutableListOf<MenuItem>()
    private var nextId = 1

    @GetMapping
    fun getAll(): List<MenuItem> = items

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): MenuItem? =
        items.find { it.id == id }

    @PostMapping
    fun addItem(@RequestBody newItem: MenuItem): MenuItem {
        val item = newItem.copy(id = nextId++)
        items.add(item)
        return item
    }

    @PutMapping("/{id}")
    fun updateItem(@PathVariable id: Int, @RequestBody updated: MenuItem): MenuItem? {
        val idx = items.indexOfFirst { it.id == id }
        if (idx == -1) return null
        val merged = items[idx].copy(
            name = updated.name,
            price = updated.price,
            description = updated.description,
            category = updated.category,
            available = updated.available
        )
        items[idx] = merged
        return merged
    }

    @DeleteMapping("/{id}")
    fun deleteItem(@PathVariable id: Int): Boolean =
        items.removeIf { it.id == id }
}
