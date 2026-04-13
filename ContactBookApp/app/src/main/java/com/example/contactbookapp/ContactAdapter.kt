package com.example.contactbookapp
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView



class ContactAdapter(
    context: Context,
    private var contacts: MutableList<Contact>
) : ArrayAdapter<Contact>(context, 0, contacts) {

    private var filteredList = contacts.toMutableList()

    override fun getCount(): Int = filteredList.size

    override fun getItem(position: Int): Contact = filteredList[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_contact, parent, false)

        val contact = getItem(position)

        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvPhone = view.findViewById<TextView>(R.id.tvPhone)
        val tvInitial = view.findViewById<TextView>(R.id.tvInitial)

        tvName.text = contact.name
        tvPhone.text = contact.phone
        tvInitial.text = contact.initial

        return view
    }

    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            contacts.toMutableList()
        } else {
            contacts.filter {
                it.name.lowercase().contains(query.lowercase())
            }.toMutableList()
        }
        notifyDataSetChanged()
    }

    fun updateList(newList: MutableList<Contact>) {
        contacts = newList
        filter("")
    }
}