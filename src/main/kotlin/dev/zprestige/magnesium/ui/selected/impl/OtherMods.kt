package dev.zprestige.magnesium.ui.selected.impl

import dev.zprestige.magnesium.ui.selected.Selected

class OtherMods : Selected("Other Mods") {

    init {
        tab = "All"
        this.buttons = arrayOf(createButton("All") { tab = "All" }, createButton("Performance") { tab = "Performance" }, createButton("Extra") { tab = "Extra" })
    }
}