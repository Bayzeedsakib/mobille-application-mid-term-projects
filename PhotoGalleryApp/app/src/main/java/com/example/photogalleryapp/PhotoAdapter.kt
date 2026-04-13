package com.example.photogalleryapp
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.CheckBox

class PhotoAdapter(
    private val context: Context,
    private var photoList: MutableList<Photo>
) : BaseAdapter() {

    var isSelectionMode = false
    private var originalList: MutableList<Photo> = photoList.toMutableList()

    override fun getCount(): Int = photoList.size

    override fun getItem(position: Int): Any = photoList[position]

    override fun getItemId(position: Int): Long = photoList[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_photo, parent, false)

        val img = view.findViewById<ImageView>(R.id.imgPhoto)
        val title = view.findViewById<TextView>(R.id.txtTitle)
        val checkbox = view.findViewById<CheckBox>(R.id.checkBox)

        val photo = photoList[position]

        img.setImageResource(photo.resourceId)
        title.text = photo.title

        checkbox.visibility = if (isSelectionMode) View.VISIBLE else View.GONE
        checkbox.isChecked = photo.isSelected

        return view
    }

    fun updateList(newList: MutableList<Photo>) {
        photoList = newList
        notifyDataSetChanged()
    }

    fun getSelectedItems(): List<Photo> {
        return photoList.filter { it.isSelected }
    }

    fun removeSelected() {
        val selectedIds = photoList.filter { it.isSelected }.map { it.id }

        originalList.removeAll { it.id in selectedIds }
        photoList.removeAll { it.isSelected }

        notifyDataSetChanged()
    }
}