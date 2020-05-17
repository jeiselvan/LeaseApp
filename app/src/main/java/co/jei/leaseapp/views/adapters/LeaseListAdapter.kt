package co.jei.leaseapp.views.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.jei.leaseapp.R
import co.jei.leaseapp.models.LeaseList
import kotlinx.android.synthetic.main.item_lease_data.view.*

class LeaseListAdapter(
    private val activity: Activity,
    private val leaseList: ArrayList<LeaseList>, var listener: (LeaseList) -> Unit
) : RecyclerView.Adapter<LeaseListAdapter.LeaseListViewHolder>() {

    fun updateLeaseList(updatedLeaseList: List<LeaseList>) {
        leaseList.clear()
        leaseList.addAll(updatedLeaseList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaseListViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.item_lease_data, parent, false)
        return LeaseListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return leaseList.size
    }

    override fun onBindViewHolder(holder: LeaseListViewHolder, position: Int) {
        holder.bind(leaseList[position], listener)
    }

    class LeaseListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val txtTenant = view.txt_tenant

        fun bind(data: LeaseList, listener: (LeaseList) -> Unit) {
            txtTenant.text = data.tenant

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }
}