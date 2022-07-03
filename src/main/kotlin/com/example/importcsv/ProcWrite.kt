package com.example.importcsv

import org.springframework.batch.item.ItemWriter

class ProcWrite<T>: ItemWriter<T> {
    override fun write(items: MutableList<out T>) {
        for (item in items) {
            println(item.toString())
        }
    }

}
