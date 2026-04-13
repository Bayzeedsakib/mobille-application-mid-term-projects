package com.example.ecommerceapp

import android.content.Intent
import android.os.*
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ProductAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView

    private val allProducts = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        emptyView = findViewById(R.id.emptyView)

        adapter = ProductAdapter {
            CartManager.cartList.add(it)
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        loadData()
        setupTouchHelper()
        setupSearch()
    }

    private fun loadData() {
        val skeleton = List(3) { Product(0,"",0.0,0f,"",0) }
        adapter.submitList(skeleton)

        Handler(Looper.getMainLooper()).postDelayed({
            allProducts.addAll(listOf(
                Product(1,"Laptop",1200.0,4.5f,"Electronics",0),
                Product(2,"T-Shirt",20.0,4.0f,"Clothing",0),
                Product(3,"Book",15.0,5.0f,"Books",0),
                Product(4,"Pizza",8.0,4.2f,"Food",0),
                Product(5,"Toy Car",12.0,4.3f,"Toys",0)
            ))

            adapter.submitList(allProducts)
        },1500)
    }

    private fun setupSearch() {
        val search = findViewById<SearchView>(R.id.searchView)

        search.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String?) = false

            override fun onQueryTextChange(q: String?): Boolean {
                val filtered = allProducts.filter {
                    it.name.contains(q ?: "", true)
                }

                adapter.submitList(filtered)
                emptyView.visibility =
                    if (filtered.isEmpty()) View.VISIBLE else View.GONE

                return true
            }
        })
    }

    private fun setupTouchHelper() {
        val helper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {

            override fun onMove(
                rv: RecyclerView,
                vh: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val list = adapter.currentList.toMutableList()
                val from = vh.bindingAdapterPosition
                val to = target.bindingAdapterPosition

                java.util.Collections.swap(list, from, to)
                adapter.submitList(list)
                return true
            }

            override fun onSwiped(vh: RecyclerView.ViewHolder, dir: Int) {
                val pos = vh.bindingAdapterPosition
                val item = adapter.currentList[pos]

                val list = adapter.currentList.toMutableList()
                list.removeAt(pos)
                adapter.submitList(list)

                Snackbar.make(recyclerView,"Deleted",Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        list.add(pos,item)
                        adapter.submitList(list)
                    }.show()
            }
        })

        helper.attachToRecyclerView(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        menu.findItem(R.id.menuCart).setOnMenuItemClickListener {
            startActivity(Intent(this, CartActivity::class.java))
            true
        }

        menu.findItem(R.id.menuToggle).setOnMenuItemClickListener {
            adapter.isGrid = !adapter.isGrid

            recyclerView.layoutManager =
                if (adapter.isGrid) GridLayoutManager(this,2)
                else LinearLayoutManager(this)

            adapter.notifyDataSetChanged()
            true
        }

        return true
    }
}