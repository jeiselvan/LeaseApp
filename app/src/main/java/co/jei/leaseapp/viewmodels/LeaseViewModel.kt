package co.jei.leaseapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.jei.leaseapp.LeaseApp
import co.jei.leaseapp.models.LeaseInfo
import co.jei.leaseapp.models.LeaseList
import co.jei.leaseapp.network.LeaseService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class LeaseViewModel : ViewModel() {

    init {
        LeaseApp.appComponent.inject(this)
    }

    @Inject
    lateinit var leaseService: LeaseService

    @Inject
    lateinit var disposable: CompositeDisposable

    val isLeaseListError = MutableLiveData<Boolean>()
    val leaseList = MutableLiveData<List<LeaseList>>()
    val isLeaseInfoError = MutableLiveData<Boolean>()
    val leaseInfo = MutableLiveData<LeaseInfo>()

    fun getLeaseList() {
        disposable.add(
            leaseService.getLeaseList().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<LeaseList>>() {
                    override fun onSuccess(list: List<LeaseList>) {
                        leaseList.value = list
                    }

                    override fun onError(e: Throwable) {
                        isLeaseListError.value = true
                    }
                })
        )
    }

    fun getLeaseInfo(id: String) {
        disposable.add(
            leaseService.getLeaseInfo(id).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<LeaseInfo>() {
                    override fun onSuccess(data: LeaseInfo) {
                        leaseInfo.value = data
                    }

                    override fun onError(e: Throwable) {
                        isLeaseInfoError.value = true
                    }
                })
        )
    }

    fun getLeaseListData(): LiveData<List<LeaseList>> {
        return leaseList
    }

    fun getLeaseInfoData(): LiveData<LeaseInfo> {
        return leaseInfo
    }

    fun getLeaseListError(): LiveData<Boolean> {
        return isLeaseListError
    }

    fun getLeaseInfoError(): LiveData<Boolean> {
        return isLeaseInfoError
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}