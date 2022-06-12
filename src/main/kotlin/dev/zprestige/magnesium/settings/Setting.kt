package dev.zprestige.magnesium.settings

abstract class Setting<T>(var name: String, var value: T){
    var tab = "Feature"
}