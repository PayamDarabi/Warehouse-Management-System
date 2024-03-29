package com.wms.wms.ui.receiving

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wms.wms.data.api.ReceivingList.ReceivingListApi
import com.wms.wms.data.model.response.ApiResult

class ReceivingViewModel(private val receivingListApi: ReceivingListApi) : ViewModel() {

    private val _receivingResult = MutableLiveData<ReceivingResult>()
    val receivingResult: LiveData<ReceivingResult> = _receivingResult

    suspend fun getReceivingList() {
        val result = receivingListApi.getReceivingList()
        var receivingList = arrayListOf<ReceivingView>()

        if (result.isSuccessful && result.data !==null) {
            for (item in result.data!!) {
                receivingList.add(
                    ReceivingView(
                        item.driverFullName,
                        item.dockCode,
                        item.receivingNumber,
                        item.containerNumber,
                        item.plaqueNumber,
                        item.carTypeId,
                        item.carTypeTitle,
                        item.createdOn,
                        item.receivingId,
                        item.receivingTypeId
                    )
                )
            }
            _receivingResult.value = ReceivingResult(success = receivingList)
        } else {
            _receivingResult.value = ReceivingResult()
        }
    }
}