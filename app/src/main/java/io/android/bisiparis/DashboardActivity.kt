package io.android.bisiparis

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import io.android.bisiparis.adapter.CustomerListAdapter
import io.android.bisiparis.helper.DatabaseHelper
import io.android.bisiparis.interfaces.CustomAdapterClickListener
import io.androidedu.datapersistance.ui.sql.model.CustomerOrderInfo

class DashboardActivity : AppCompatActivity(), View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, CustomAdapterClickListener {

    private val swipeRefreshLayout by lazy { findViewById<SwipeRefreshLayout>(R.id.activity_dashboard_swipeRefreshLayout) }
    private val recycGuestList by lazy { findViewById<RecyclerView>(R.id.activity_dashboard_recycPersonalLaunchList) }

    private val addDialog by lazy { AlertDialog.Builder(this).create() }
    private val btnSave by lazy { addDialog.findViewById<Button>(R.id.alert_dialog_add_customer_btnOrder) }
    private val edtCustomerName by lazy { addDialog.findViewById<EditText>(R.id.alert_dialog_add_customer_edtCustomerName) }
    private val databaseHelper by lazy { DatabaseHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        Log.e("Personel Adı", intent.getStringExtra("PersonalName"))
        initEvent()
    }

    private fun initEvent() {

        swipeRefreshLayout.setOnRefreshListener(this)

        val linearLayoutManager = LinearLayoutManager(this)
        val guestListAdapter = CustomerListAdapter(databaseHelper.getAllCustomer(), this)

        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val deletedGuestInfo = CustomerOrderInfo(viewHolder.itemView.tag.toString().toLong(), "", "", "")
                databaseHelper.deleteCustomer(deletedGuestInfo)

                (recycGuestList.adapter as CustomerListAdapter).setCustomerList(databaseHelper.getAllCustomer())
                recycGuestList.adapter.notifyDataSetChanged()
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)

        recycGuestList.layoutManager = linearLayoutManager
        recycGuestList.adapter = guestListAdapter
        itemTouchHelper.attachToRecyclerView(recycGuestList)
    }


    private fun createAlertDialog(buttonText: String
                                  , customerKey: Long = 0L
                                  , customerName: String = "") {

        val layoutInflater = LayoutInflater.from(this)
        val alertView = layoutInflater.inflate(R.layout.alert_dialog, null, false)

        addDialog.setView(alertView)
        addDialog.show()

        btnSave?.text = buttonText
        edtCustomerName?.setText(customerName)


        btnSave?.tag = customerKey
        btnSave?.setOnClickListener(this)
    }

    private fun eventSaveButton() {


        val customerOrderInfo = CustomerOrderInfo(customerName = edtCustomerName?.text.toString())

        databaseHelper.addCustomer(customerOrderInfo)
        addDialog.dismiss()
    }

    private fun eventUpdateButton(updateButton: Button) {

        val customerOrderInfo = CustomerOrderInfo(customerID = updateButton.tag.toString().toLong()
                , customerName = edtCustomerName?.text.toString())

        databaseHelper.updateCustomer(customerOrderInfo)
        addDialog.dismiss()
    }


    override fun onClick(view: View?) {
        when ((view as Button).id) {

            R.id.alert_dialog_add_customer_btnOrder -> {

                if ((view).text.toString() == resources.getString(R.string.continue_order)) {

                    eventSaveButton()

                } else if ((view).text.toString() == resources.getString(R.string.update)) {

                    eventUpdateButton(view)
                }
            }
        }
    }

    override fun onCustomItemClickListener(customerOrderInfo: CustomerOrderInfo, position: Int) {

        createAlertDialog(resources.getString(R.string.update),
                customerOrderInfo.customerID,
                customerOrderInfo.customerName)
    }

    override fun onRefresh() {

        (recycGuestList.adapter as CustomerListAdapter).setCustomerList(databaseHelper.getAllCustomer())
        recycGuestList.adapter.notifyDataSetChanged()
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_activity_dashboard, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item!!.itemId) {

            R.id.menu_activity_dashboard_add_order -> {

                createAlertDialog(resources.getString(R.string.continue_order))
            }
        }

        return super.onOptionsItemSelected(item)
    }


}
