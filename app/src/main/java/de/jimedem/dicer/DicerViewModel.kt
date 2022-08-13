package de.jimedem.dicer

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.jimedem.dicer.data.Animation
import de.jimedem.dicer.data.BaseUrl
import de.jimedem.dicer.states.CheckConnectionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DicerViewModel : ViewModel() {

    private var checkingConnection = false

    var selectedDevice: MutableStateFlow<BaseUrl> = MutableStateFlow(BaseUrl.RaspberryPi)

    var animation: MutableStateFlow<Animation> = MutableStateFlow(Animation.None)

    private var _runs: SnapshotStateList<SnapshotStateList<Int>> =
        mutableStateListOf(mutableStateListOf(1))

    // val targets: List<Run> = _targets
    val runs: List<List<Int>> = _runs

    fun sendConfiguration(){
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

    fun saveConfiguration(context: Context){

    }

    fun restoreDefaultConfiguration(context: Context){

    }

    fun addRun() {
        _runs.add(mutableStateListOf(1))
    }

    fun addTarget(run: Int, number: Int) {
        if (number in 1..6) {
            targetList(run)?.add(number)
        }
    }

    fun modifyTarget(run: Int, index: Int, number: Int) {
        targetList(run)?.set(index, number)
    }

    fun removeTarget(run: Int, index: Int) {
        targetList(run)?.removeAt(index)
        if (targetList(run)?.isEmpty() == true) {
            _runs.removeAt(run)
        }
    }

    fun removeLastRun() {
        _runs.removeAt(_runs.size - 1)
    }

    fun checkConnection() {
        if(!checkingConnection){
            checkingConnection = true
            viewModelScope.launch {
                while (checkingConnection){
                    delay(2000)
                    selectedDevice.value.reachable.value = true
                }
            }
        }

    }

    fun targetList(run: Int) =
        _runs.filterIndexed { index, _ -> index == run }.firstOrNull()

}