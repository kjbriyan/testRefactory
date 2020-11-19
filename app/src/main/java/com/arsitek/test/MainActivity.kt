package com.arsitek.test

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.arsitek.test.TextUndoRedo.TextChangeInfo


class MainActivity : Activity(), TextChangeInfo, View.OnClickListener {
    private var TUR: TextUndoRedo? = null
    private var btn_undo: Button? = null
    private var btn_redo: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        btn_undo = findViewById<View>(R.id.btn_undo) as Button
        btn_undo!!.setOnClickListener(this)
        btn_redo = findViewById<View>(R.id.btn_redo) as Button
        btn_redo!!.setOnClickListener(this)
        findViewById<View>(R.id.btn_clean).setOnClickListener(this)
        TUR = TextUndoRedo(findViewById<View>(R.id.et_area) as EditText, this)
        textAction()
    }

    override fun textAction() {
        btn_undo!!.isEnabled = TUR!!.canUndo()
        btn_redo!!.isEnabled = TUR!!.canRedo()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_undo -> btn()
            R.id.btn_redo -> TUR!!.exeRedo()
            R.id.btn_clean -> {
                TUR!!.cleanRecord()
                textAction()
            }
        }
    }

    fun btn (){
        btn_undo?.setOnClickListener {
            Toast.makeText(this@MainActivity,"singgle",Toast.LENGTH_SHORT).show()
        }
        btn_undo?.setOnClickListener(object : DoubleClickListener() {
            override fun onDoubleClick(v: View?) {
                Toast.makeText(this@MainActivity,"Double Click",Toast.LENGTH_SHORT).show()
            }
        })
    }

    abstract class DoubleClickListener : View.OnClickListener {
        var lastClickTime: Long = 0
        override fun onClick(v: View?) {
            val clickTime = System.currentTimeMillis()
            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                onDoubleClick(v)
            }
            lastClickTime = clickTime
        }

        abstract fun onDoubleClick(v: View?)

        companion object {
            private const val DOUBLE_CLICK_TIME_DELTA: Long = 300 //milliseconds
        }
    }
}