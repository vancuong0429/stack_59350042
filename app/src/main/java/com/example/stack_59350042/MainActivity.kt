package com.example.stack_59350042

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*


class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private var productList: ArrayList<Product> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = FirebaseDatabase.getInstance().getReference("Sales")
        database.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                productList.clear()
                for (snapshot in p0.children) {
                    if (snapshot.hasChild("items")) {
                        val generic: GenericTypeIndicator<List<Product>> =
                            object : GenericTypeIndicator<List<Product>>() {}
                        val items = snapshot.child("items").getValue(generic)
                        items?.let { productList.addAll(it) }
                    }
                }
            }

        })
    }
    class Product {
        var pName: String? = null
        var pUnit: Int? = null
        var pPrice: Double? = null
        var pAmount: Double? = null
    }
}
