package co.jei.leaseapp.views.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import co.jei.leaseapp.R
import co.jei.leaseapp.utils.CommonUtils
import co.jei.leaseapp.viewmodels.LeaseViewModel
import co.jei.leaseapp.views.LeaseInfoActivity
import co.jei.leaseapp.views.adapters.LeaseListAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var leaseListAdapter: LeaseListAdapter
    private lateinit var leaseViewModel: LeaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeViewModel()
        getLeaseList()
    }

    private fun initViews() {
        leaseListAdapter = LeaseListAdapter(requireActivity(), arrayListOf()) { info ->
            val infoIntent = Intent(requireActivity(), LeaseInfoActivity::class.java)
            infoIntent.putExtra("id", info.id)
            startActivity(infoIntent)
        }

        rv_lease.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = leaseListAdapter
        }
    }

    private fun observeViewModel() {
        leaseViewModel = ViewModelProviders.of(this).get(LeaseViewModel::class.java)

        leaseViewModel.leaseList.observe(requireActivity(), Observer { list ->
            layout_progress.visibility = View.GONE
            list?.let {
                leaseListAdapter.updateLeaseList(it)
            }
        })

        leaseViewModel.isLeaseListError.observe(requireActivity(), Observer { error ->
            layout_progress.visibility = View.GONE
            CommonUtils.showToast(requireContext(), getString(R.string.server_error))
        })
    }

    private fun getLeaseList() {
        if (CommonUtils.isNetworkAvailable(requireContext())) {
            layout_progress.visibility = View.VISIBLE
            leaseViewModel.getLeaseList()
        } else {
            CommonUtils.showToast(requireContext(), getString(R.string.error_no_internet))
        }
    }
}
