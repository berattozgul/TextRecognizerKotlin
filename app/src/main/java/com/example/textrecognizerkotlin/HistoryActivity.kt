package com.example.textrecognizerkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView

class HistoryActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var navigationView: NavigationView

    lateinit var sqLiteHelper: SQLiteHelper
    lateinit var recyclerView: RecyclerView
    private var adapter: HistoryAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        drawerLayout = findViewById(R.id.drawerLayoutHistory)
        navigationView = findViewById(R.id.nav_view_history)
        sqLiteHelper = SQLiteHelper(this)
        recyclerView = findViewById(R.id.recyclerView)
        initRecyclerView()
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_text_recognizer -> {
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    startActivity(Intent(this@HistoryActivity, MainActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_history -> {
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    true
                }
                else -> {
                    false
                }
            }

        }
        getHistory()
        adapter?.setOnClickItem {
            Toast.makeText(this, it.text, Toast.LENGTH_SHORT).show()
            val value: String = it.text// or just your string
            val intent = Intent(this, ScannedTextActivity::class.java)
            intent.putExtra("value", value)
            startActivity(intent)
        }
        adapter?.setOnClickDeleteItem {
            deleteHistoryItem(it.id)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun deleteHistoryItem(id: Int) {
        if (id == null) return
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure do you want to delete item?")
        builder.setCancelable(true)
        builder.setPositiveButton("yes") { dialog, _ ->
            sqLiteHelper.deleteHistoryByID(id)
            getHistory()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    private fun getHistory() {
        val historyList = sqLiteHelper.getAllTexts()
        adapter?.addItems(historyList)
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = HistoryAdapter()
        recyclerView.adapter = adapter

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

}