package com.brkcnszgn.bottomdropdown

import com.brkcnszgn.dropdownhash.model.DropdownModel
import java.util.*

open class UserModel{
    var name: String?= null
    var surname: String? = null


   open fun create(list: List<UserModel>?): HashMap<String, DropdownModel>? {
        val map: HashMap<String, DropdownModel> = HashMap<String, DropdownModel>()
        if (list != null && list.size > 0) {
            map["0"] = DropdownModel("", "Seçiniz")
            for (i in list.indices) {
                val item: UserModel = list[i]
                map[(i + 1).toString()] = DropdownModel(item.name, item.surname)
            }
        }
        return map
    }
}