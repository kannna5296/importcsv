package com.example.importcsv

import org.springframework.batch.item.ItemProcessor
import java.util.Locale

class ProcProcess: ItemProcessor<SyainCsvModel, SyainResultModel> {
    override fun process(syain: SyainCsvModel): SyainResultModel {
        val id = syain.id
        val romaji = syain.romaji?.uppercase(Locale.getDefault())

        //ほんまはあかんけど!!
        return SyainResultModel(id!!, romaji!!)
    }
}
