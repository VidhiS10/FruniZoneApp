package com.example.frunizone

import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frunizone.adapter.FurnitureAdapter1
import com.example.frunizone.api.FurnitureApi
import com.example.frunizone.model.FurnitureModel
import com.example.frunizone.model.FurnitureOutputModel

class SearchActivity : AppCompatActivity() {

    private lateinit var recylsearch: RecyclerView
    private lateinit var edsearch: EditText
    private lateinit var toolbar: Toolbar
    private var searchStr = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recylsearch = findViewById(R.id.recylsearch)
//        edsearch = findViewById(R.id.edsearch)
//        toolbar = findViewById(R.id.toolbar)

       /* toolbar.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {

                R.id.menu_search -> {
                    searchStr = edsearch.text.toString()
                    if (searchStr.trim().isEmpty()) {
                        Toast.makeText(
                            this,
                            "Please Enter Search....",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        FurnitureApi().getFurnitureBasedOnSearch(this, searchStr)
                    }
                }

                R.id.menu_cart -> {
                    val intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

        toolbar.setBackInvokedCallbackEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }*/
    }

    fun setFurniture(model: FurnitureOutputModel) {

        val furnitureAdapter = FurnitureAdapter1(
            this,
            model.sub_category,
            object : FurnitureAdapter1.ItemClickListener {
                override fun onClick(position: Int, model: FurnitureModel) {

                    val bundle = Bundle().apply {
                        putSerializable("product", model)
                    }

                    val fragment = SubCategoryFragment()
                    fragment.arguments = bundle

                    openFragment(fragment)
                }
            }
        )

        recylsearch.layoutManager = GridLayoutManager(this, 2)
        recylsearch.adapter = furnitureAdapter
    }

    fun openFragment(fragment: Fragment) {
        val frameLayout: FrameLayout = findViewById(R.id.frame)
        frameLayout.visibility = View.VISIBLE

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, fragment)
            .commit()
    }

}