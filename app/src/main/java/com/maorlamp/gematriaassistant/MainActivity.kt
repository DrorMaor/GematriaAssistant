package com.maorlamp.gematriaassistant

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.text.Editable
import android.widget.EditText
import android.widget.TextView
import android.widget.Button
import java.io.File
import kotlin.math.abs


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val GimText1 = findViewById<EditText>(R.id.GimText1)
        val GimResult1 = findViewById<TextView>(R.id.GimResult1)
        val GimText2 = findViewById<EditText>(R.id.GimText2)
        val GimResult2 = findViewById<TextView>(R.id.GimResult2)
        val btnClear = findViewById<Button>(R.id.btnClear)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnRead = findViewById<Button>(R.id.btnRead)
        val btnSend = findViewById<Button>(R.id.btnSend)

        GimText1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Add your code here if needed, or leave it empty if not required
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                GimResult1.text = CalcGim(s)
                if (GimText1.text.length > 0 && GimText2.text.length > 0)
                    CheckForEqual()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        GimText2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Add your code here if needed, or leave it empty if not required
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                GimResult2.text = CalcGim(s)
                if (GimText1.text.length > 0 && GimText2.text.length > 0)
                    CheckForEqual()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        btnClear.setOnClickListener {
            GimText1.setText("")
            GimText2.setText("")
        }

        btnSave.setOnClickListener {
            SaveGim()
        }

        btnRead.setOnClickListener {
            ReadGim()
        }

        btnSend.setOnClickListener {
           SendGim()
        }
    }

    fun SendGim() {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, "testing")
        intent.type="text/plain"
        startActivity(intent)
    }

    fun SaveGim() {
        val GimText1 = findViewById<EditText>(R.id.GimText1)
        val GimText2 = findViewById<EditText>(R.id.GimText2)
        /*
        val sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)
        sharedPreferences.edit().putString("Gim",GimText1.text.toString() + "|" + GimText2.text.toString()).apply()
         */

        val f = File("jjj.txt"!!)
        f.appendText(GimText1.text.toString() + "|" + GimText2.text.toString() + System.getProperty("line.separator"))
    }

    fun ReadGim() {
        /*
        val sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)
        val name = sharedPreferences.getString("Gim", "")
        val btnSave = findViewById<Button>(R.id.btnSave)
        btnSave.setText(name)

         */
        val btnSave = findViewById<Button>(R.id.btnSave)
        val f = File("jjj.txt"!!)
        btnSave.setText(f.readText())

    }

    fun CalcGim(s: CharSequence?): CharSequence? {
        var gim: Int = 0
        if (s != null) {
            for (i in 0 until s.length) {
                val char = s[i]
                when (char) {
                    'a' -> gim += 1
                    'b' -> gim += 2
                    'c' -> gim += 3
                    'd' -> gim += 4


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
        val GimResult1 = findViewById<TextView>(R.id.GimResult1)
        val GimResult2 = findViewById<TextView>(R.id.GimResult2)
        var results1: Int = GimResult1.text.toString().toInt()
        var results2: Int = GimResult2.text.toString().toInt()

        if (results1 == results2 ) {
            GimResult1.setTextColor(Color.parseColor("#7B68EE"))
            GimResult2.setTextColor(Color.parseColor("#7B68EE"))
        }
        else if (abs(results1 - results2) == 1 ) {
            GimResult1.setTextColor(Color.parseColor("#FF4500"))
            GimResult2.setTextColor(Color.parseColor("#FF4500"))
        }
        else {
            GimResult1.setTextColor(Color.parseColor("#000000"))
            GimResult2.setTextColor(Color.parseColor("#000000"))
        }
    }
}