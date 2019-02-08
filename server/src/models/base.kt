package models

import java.util.*

interface PersistanceStrategy<T> {
    fun persistObject(toPersist: T)
}

interface Persistable {
    fun registerPersistenceStrategy()
    fun persist()
}

abstract class IModel : Observable(), Persistable

