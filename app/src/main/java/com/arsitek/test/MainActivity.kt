package com.arsitek.test

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.arsitek.test.TextUndoRedo.TextChangeInfo

// tidak merekomendasikan doble click digunakan untuk action ini
// dikarenakan Menerapkan Sentuhan Ganda akan memengaruhi penanganan Sentuhan Tunggal, karena Anda
// harus menunggu untuk melihat apakah setiap Sentuhan Tunggal berubah menjadi Sentuhan Ganda sebelum Anda
// dapat memprosesnya.

class MainActivity : Activity(), TextChangeInfo {
    private var TUR: TextUndoRedo? = null
    private var btn_undo: Button? = null
    private var btn_redo: Button? = null
    private  var btn_revert : Button? = null
    private var tv : TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_undo = findViewById(R.id.btn_undo)
        btn_redo = findViewById(R.id.btn_redo)
        btn_revert= findViewById(R.id.btn_revert)
        tv = findViewById(R.id.textView)
        TUR = TextUndoRedo(findViewById<View>(R.id.et_area) as EditText, this)
        textAction()
        bot()
    }

    override fun textAction() {
        btn_undo!!.isEnabled = TUR!!.canUndo()
        btn_redo!!.isEnabled = TUR!!.canRedo()
    }

    fun bot(){
        btn_redo?.setOnClickListener {
            TUR!!.exeRedo()
        }
        btn_revert?.setOnClickListener {
            var txt = findViewById<EditText>(R.id.et_area).text
            var get = reverse(txt.toString())
            tv?.setText(get)
        }

        btn_undo?.setOnTouchListener(object : View.OnTouchListener{
            val gestureDetector = GestureDetector(object : GestureDetector.SimpleOnGestureListener(){
                override fun onSingleTapUp(e: MotionEvent?): Boolean {
                    TUR!!.exeUndo()
                    Toast.makeText(this@MainActivity, "Singgle Click", Toast.LENGTH_SHORT).show()
                    return super.onSingleTapUp(e)
                }
                override fun onDoubleTap(e: MotionEvent?): Boolean {
                    TUR!!.exeRedo()
                    Toast.makeText(this@MainActivity, "Double Click", Toast.LENGTH_SHORT).show()
                    return super.onDoubleTap(e)
                }
            })

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                //do something here
                gestureDetector.onTouchEvent(event)
                return true
            }
        })
    }

    fun reverse(sentence: String): String {
        if (sentence.isEmpty())
            return sentence

        return reverse(sentence.substring(1)) + sentence[0]
    }
}