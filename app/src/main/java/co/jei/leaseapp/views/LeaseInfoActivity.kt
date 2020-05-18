package co.jei.leaseapp.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import co.jei.leaseapp.R
import co.jei.leaseapp.models.LeaseInfo
import co.jei.leaseapp.utils.CommonUtils
import co.jei.leaseapp.viewmodels.LeaseViewModel
import kotlinx.android.synthetic.main.activity_lease_info.*

class LeaseInfoActivity : AppCompatActivity() {

    private lateinit var leaseViewModel: LeaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lease_info)

        if (intent.extras != null) {
            val id = intent.getStringExtra("id")
            observeViewModel()
            getLeaseInfo(id!!)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setLeaseInfo(leaseInfo: LeaseInfo) {
        txt_start_date.text = leaseInfo.startDate
        txt_end_date.text = leaseInfo.endDate
        txt_rent.text = "$${leaseInfo.rent}"
        txt_frequency.text = leaseInfo.frequency
        txt_payment_day.text = leaseInfo.payment_day
    }

    private fun observeViewModel() {
        leaseViewModel = ViewModelProviders.of(this).get(LeaseViewModel::class.java)

        leaseViewModel.leaseInfo.observe(this, Observer { info ->
            layout_progress.visibility = View.GONE
            info?.let {
                setLeaseInfo(it)
            }
        })

        leaseViewModel.isLeaseInfoError.observe(this, Observer { _ ->
            layout_progress.visibility = View.GONE
            CommonUtils.showToast(this, getString(R.string.server_error))
        })
    }

    private fun getLeaseInfo(id: String) {
        if (CommonUtils.isNetworkAvailable(this@LeaseInfoActivity)) {
            layout_progress.visibility = View.VISIBLE
            leaseViewModel.getLeaseInfo(id)
        } else {
            CommonUtils.showToast(this, getString(R.string.error_no_internet))
        }
    }
}
