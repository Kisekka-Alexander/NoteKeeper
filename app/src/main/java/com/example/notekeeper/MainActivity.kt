package com.example.notekeeper

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.notekeeper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var notePosition = POSITION_NOT_SET

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

//        val dm = DataManager()
        val adapterCourses = ArrayAdapter<CourseInfo>(this, android.R.layout.simple_spinner_item,
//        dm.courses.values.toList()
          DataManager.courses.values.toList()
        )
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.spinnerCourses).adapter = adapterCourses

        notePosition = intent.getIntExtra(EXTRA_NOTE_POSITION, POSITION_NOT_SET)
        if(notePosition != POSITION_NOT_SET)
            displayNote()

    }

    private fun displayNote() {
        val note = DataManager.notes[notePosition]
        findViewById<EditText>(R.id.textNoteTitle).setText(note.title)
        findViewById<EditText>(R.id.textNoteText).setText(note.text)

        val coursePosition = DataManager.courses.values.indexOf(note.course)
        findViewById<Spinner>(R.id.spinnerCourses).setSelection(coursePosition)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_next ->{
                moveNext()
                true
            }
            R.id.action_previous -> {
                movePrevious()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveNext() {
        ++notePosition
        displayNote()
        invalidateOptionsMenu()
    }

    private fun movePrevious(){
        --notePosition
        displayNote()
        invalidateOptionsMenu()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if(notePosition >= DataManager.notes.lastIndex){
            val menuItem = menu?.findItem(R.id.action_previous)
            val menuItemNext = menu?.findItem(R.id.action_next)

            if (menuItem != null){
                menuItem.icon = getDrawable(R.drawable.ic_baseline_arrow_left_24)
                menuItemNext?.isEnabled = false
                menuItemNext?.isVisible = false
            }

        }

        if(notePosition == 1){
            val menuItem = menu?.findItem(R.id.action_next)
            val menuItemPrev = menu?.findItem(R.id.action_previous)

            if (menuItem != null){
                menuItem.icon = getDrawable(R.drawable.ic_baseline_arrow_right_24)
                menuItemPrev?.isEnabled = false
                menuItemPrev?.isVisible = false
            }
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}