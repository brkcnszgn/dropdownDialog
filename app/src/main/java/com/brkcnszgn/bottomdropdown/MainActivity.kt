package com.brkcnszgn.bottomdropdown

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.brkcnszgn.dropdownhash.model.DropdownModel
import com.brkcnszgn.dropdownhash.listeners.ClickListener
import com.brkcnszgn.dropdownhash.Constants
import com.brkcnszgn.dropdownhash.DropdownHashActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

     var userList  : ArrayList<UserModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userList = ArrayList<UserModel>()

        userList!!.add(UserModel().apply {
            name = "deneme1"
            surname = "deneme2"
        })

       clickFunc()

    }

    fun clickFunc(){

        dp.setOnClickListener(ClickListener {
            val model = UserModel()
            model.create(userList)?.let { openDropdown(it, "deneme", 101, 2) }


        })



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {

            val dropdownKey = data.getStringExtra(Constants.SELECTED_DROPDOWN)
            val dropdownText = data.getStringExtra(Constants.SELECTED_DROPDOWN_TEXT)

            if (requestCode == 101){
                dp.text = dropdownKey
                dp.key = dropdownText
            }
        }
    }

    fun openDropdown(
        hashMap: HashMap<String, DropdownModel>,
        title: String?,
        requestCode: Int,
        minLengthForSearch: Int
    ) {
        val i = Intent(this, DropdownHashActivity::class.java)
        Constants.mParameters = hashMap
        if (hashMap.size >= 10) {
            i.putExtra(DropdownHashActivity.SEARCH, true)
        }
        i.putExtra(DropdownHashActivity.MIN_LENGTH_FOR_SEARCH, minLengthForSearch)
        i.putExtra(DropdownHashActivity.TITLE, title)
        startActivityForResult(i, requestCode)
    }


}