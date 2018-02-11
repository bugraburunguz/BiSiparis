package io.android.bisiparis.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.android.bisiparis.R
import io.android.bisiparis.interfaces.CustomAdapterClickListener
import io.androidedu.datapersistance.ui.sql.model.CustomerOrderInfo


class CustomerListAdapter(private var cutomerList: ArrayList<CustomerOrderInfo>, private val customAdapterClickListener: CustomAdapterClickListener)

    : RecyclerView.Adapter<CustomerListViewHolder>() {

    fun setCustomerList(cutomerList: ArrayList<CustomerOrderInfo>) {

        this.cutomerList = cutomerList
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CustomerListViewHolder {

        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.rcyc_row_item, parent, false)
        return CustomerListViewHolder(itemView)
    }

    override fun getItemCount(): Int = cutomerList.size

    override fun onBindViewHolder(holder: CustomerListViewHolder?, position: Int) {

        val customerOrderInfo = cutomerList[position]

        holder?.txtCustomerName?.text = customerOrderInfo.customerName
        holder?.txtSoup?.text = customerOrderInfo.soup
        holder?.txtFood?.text = customerOrderInfo.food
        holder?.txtDrink?.text = customerOrderInfo.drink
        holder?.txtDessert?.text = customerOrderInfo.desserts


        holder?.itemView?.tag = customerOrderInfo.customerID
        holder?.itemView?.setOnClickListener { customAdapterClickListener.onCustomItemClickListener(customerOrderInfo, position) }
    }
}