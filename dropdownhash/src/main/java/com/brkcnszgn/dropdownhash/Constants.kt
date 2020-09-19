package com.brkcnszgn.dropdownhash

import android.content.Intent
import androidx.core.app.ActivityCompat.startActivityForResult
import com.brkcnszgn.dropdownhash.model.DropdownModel
import java.util.*

object Constants {
    var SELECTED_DROPDOWN = "selected"
    var SELECTED_DROPDOWN_SECOND_KEY = "selected_second_key"
    var SELECTED_DROPDOWN_THRID_KEY = "selected_third_key"
    var SELECTED_DROPDOWN_TEXT = "selectedText"
    var mParameters: HashMap<String, DropdownModel>? = null
    val TURKISH = Locale.forLanguageTag("tr")



}