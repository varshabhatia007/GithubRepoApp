package com.example.repo.ui

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import kotlinx.android.synthetic.main.layout_menu.view.*

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class SubMenu(context: Context, view: View, val actionCallback: (String) -> Unit) :
    PopupWindow(context) {

    companion object {
        val NAME = "NAME"
    }

    init {

        width = WindowManager.LayoutParams.WRAP_CONTENT
        height = WindowManager.LayoutParams.WRAP_CONTENT

        elevation = 10f
        contentView = view
        setBackgroundDrawable(null)


        isOutsideTouchable = true

        with(view) {
            sortByName.setOnClickListener {
                actionCallback(NAME)
            }
        }
    }
}