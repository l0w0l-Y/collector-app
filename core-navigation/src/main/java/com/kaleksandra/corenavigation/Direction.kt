package com.kaleksandra.corenavigation

sealed class Direction(val path: String)
object MainDirection : Direction("main")
object CollectionDirection : Direction("collection")
object AddCollectionDirection : Direction("add-collection")