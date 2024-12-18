package com.maorlamp.gematriaassistant

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupMenu
import kotlin.math.abs


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        val etGimText1 = findViewById<EditText>(R.id.GimText1)
        val etGimResult1 = findViewById<TextView>(R.id.GimResult1)
        val etGimText2 = findViewById<EditText>(R.id.GimText2)
        val etGimResult2 = findViewById<TextView>(R.id.GimResult2)

        /*
        var popupMenu: PopupMenu
        val CurrGimMenu = findViewById<View>(R.id.CurrGimMenu)
        popupMenu = PopupMenu(this, CurrGimMenu)
        popupMenu.inflate(R.menu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.currClear -> {
                    etGimText1.setText("")
                    etGimText2.setText("")
                    true
                }
                R.id.currSend -> {
                    SendGim()
                    true
                }
                R.id.currSave -> {
                    SaveGim()
                    true
                }
                else -> false
            }
        }

        CurrGimMenu.setOnClickListener {
            popupMenu.show()
        }
        */

        etGimText1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                etGimResult1.text = CalcGim(s)
                if (etGimText1.text.length > 0 && etGimText2.text.length > 0)
                    CheckForEqual()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        etGimText2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                etGimResult2.text = CalcGim(s)
                if (etGimText1.text.length > 0 && etGimText2.text.length > 0)
                    CheckForEqual()
            }

            override fun afterTextChanged(s: Editable?) {}
        })


        val btnSend = findViewById<Button>(R.id.btnSend)
        btnSend.setOnClickListener {
            SendGim()
        }
        val btnClear = findViewById<Button>(R.id.btnClear)
        btnClear.setOnClickListener {
            ClearGim()
        }

        val btnSave = findViewById<Button>(R.id.btnSave)
        btnSave.setOnClickListener {
            SaveGim()
        }
        val btnRead = findViewById<Button>(R.id.btnRead)
        btnRead.setOnClickListener {
            ReadGim()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu from the XML resource
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    fun SendGim() {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        var text: String = "Hey, look at this gematria!\n"
        val gimPair = GetGimPair()
        text += gimPair?.split('|')?.get(0) ?: ""
        text += (" = " + gimPair?.split('|')?.get(1)) ?: ""
        intent.putExtra(Intent.EXTRA_TEXT, text)
        intent.type = "text/plain"
        startActivity(intent)
    }

    fun ClearGim() {
        val etGimText1 = findViewById<EditText>(R.id.GimText1)
        val etGimResult1 = findViewById<TextView>(R.id.GimResult1)
        val etGimText2 = findViewById<EditText>(R.id.GimText2)
        val etGimResult2 = findViewById<TextView>(R.id.GimResult2)
        etGimText1.setText("")
        etGimText2.setText("")
        etGimResult1.text = ""
        etGimResult2.text = ""
        etGimResult1.setTextColor(Color.parseColor("#ffffff"))
        etGimResult2.setTextColor(Color.parseColor("#ffffff"))
    }

    fun GetGimPair(): CharSequence? {
        val etGimText1 = findViewById<EditText>(R.id.GimText1)
        val etGimText2 = findViewById<EditText>(R.id.GimText2)
        return etGimText1.text.toString() + "|" + etGimText2.text.toString()
    }

    fun SaveGim() {
        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        //val sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gimPair = GetGimPair()
        editor.putString("Gim1", gimPair?.split('|')?.get(0) ?: "")
        editor.putString("Gim2", gimPair?.split('|')?.get(1) ?: "")
        editor.apply()
    }

    fun ReadGim() {
        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val Gim1 = sharedPreferences.getString("Gim1", null)
        val Gim2 = sharedPreferences.getString("Gim2", null)
        val tvReadGims = findViewById<TextView>(R.id.tvReadGims)
        tvReadGims.append("$Gim1 = $Gim2")
    }

    fun CalcGim(s: CharSequence?): CharSequence? {
        var gim: Int = 0
        if (s != null) {
            for (i in 0 until s.length) {
                val char = s[i]
                when (char) {
                    'א' -> gim += 1
                    'ב' -> gim += 2
                    'ג' -> gim += 3
                    'ד' -> gim += 4
                    'ה' -> gim += 5
                    'ו' -> gim += 6
                    'ז' -> gim += 7
                    'ח' -> gim += 8
                    'ט' -> gim += 9
                    'י' -> gim += 10
                    'כ', 'ך' -> gim += 20
                    'ל' -> gim += 30
                    'מ', 'ם' -> gim += 40
                    'נ', 'ן' -> gim += 50
                    'ס' -> gim += 60
                    'ע' -> gim += 70
                    'פ', 'ף' -> gim += 80
                    'צ', 'ץ' -> gim += 90
                    'ק' -> gim += 100
                    'ר' -> gim += 200
                    'ש' -> gim += 300
                    'ת' -> gim += 400
                }
            }
        }
        return gim.toString()
    }

    fun CheckForEqual() {
        val etGimResult1 = findViewById<TextView>(R.id.GimResult1)
        val etGimResult2 = findViewById<TextView>(R.id.GimResult2)
        var results1: Int = etGimResult1.text.toString().toInt()
        var results2: Int = etGimResult2.text.toString().toInt()

        if (results1 == results2) {
            etGimResult1.setTextColor(Color.parseColor("#ffd700"))
            etGimResult2.setTextColor(Color.parseColor("#ffd700"))
        } else if (abs(results1 - results2) == 1) {
            etGimResult1.setTextColor(Color.parseColor("#ff751a"))
            etGimResult2.setTextColor(Color.parseColor("#ff751a"))
        } else {
            etGimResult1.setTextColor(Color.parseColor("#ffffff"))
            etGimResult2.setTextColor(Color.parseColor("#ffffff"))
        }
    }
}
