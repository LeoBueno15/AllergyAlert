package com.example.allergyalert

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable

class ProfilesListAdapter(items: ArrayList<ProfilesView>, context: Context) :
    ArrayAdapter<ProfilesView>(context, R.layout.profiles_row, items) {

    class ProfilesItemViewHolder {
        internal var imageView: ImageView? = null
        internal var textName: TextView? = null
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup) : View {
        var view = view

        val viewHolder: ProfilesItemViewHolder

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.profiles_row, viewGroup, false)

            viewHolder = ProfilesItemViewHolder()
            viewHolder.imageView = view!!.findViewById<View>(R.id.row_image) as ImageView
            viewHolder.textName = view.findViewById<View>(R.id.row_text) as TextView
        } else {
            viewHolder = view.tag as ProfilesItemViewHolder
        }
//
//        val profile = getItem(i)
//        viewHolder.imageView!!.setImageResource(profile.)
//        viewHolder.textName!!.text = profile

        view.tag = viewHolder

        return view
    }

}