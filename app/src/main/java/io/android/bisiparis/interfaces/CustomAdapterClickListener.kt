package io.android.bisiparis.interfaces

import io.androidedu.datapersistance.ui.sql.model.CustomerOrderInfo

interface CustomAdapterClickListener {

    fun onCustomItemClickListener(customerOrderInfo: CustomerOrderInfo, position: Int)
}