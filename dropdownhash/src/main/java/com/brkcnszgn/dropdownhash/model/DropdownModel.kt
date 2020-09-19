package com.brkcnszgn.dropdownhash.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DropdownModel(
     var title: String? = null,
     var description: String? = null,
     var color: String? = null,
     var secondKey: String? = null,
     var thirdKey: String? = null
): Parcelable