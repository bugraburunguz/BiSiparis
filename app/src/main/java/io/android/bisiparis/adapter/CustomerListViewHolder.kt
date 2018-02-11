package io.android.bisiparis.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import io.android.bisiparis.R


class CustomerListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val txtCustomerName by lazy { itemView.findViewById<TextView>(R.id.rcyc_row_item_txtPersonalName) }
    val txtSoup by lazy { itemView.findViewById<TextView>(R.id.rcyc_row_item_txtSoup) }
    val txtFood by lazy { itemView.findViewById<TextView>(R.id.rcyc_row_item_txtFood) }
    val txtDrink by lazy { itemView.findViewById<TextView>(R.id.rcyc_row_item_txtDrink) }
    val txtDessert by lazy { itemView.findViewById<TextView>(R.id.adapter_item_guest_list_txtDesserts) }

}