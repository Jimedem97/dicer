package de.jimedem.dicer

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.Moshi
import de.jimedem.dicer.common.toSanitizedInt
import de.jimedem.dicer.data.Animation
import de.jimedem.dicer.data.AppConfig
import de.jimedem.dicer.data.Backend
import de.jimedem.dicer.dto.ConfigDto
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import okio.use
import java.io.File
import java.net.ConnectException

class DicerViewModel : ViewModel() {

    private var checkingConnection = false

    var selectedDevice: MutableStateFlow<Backend> = MutableStateFlow(Backend.RaspberryPi)

    var animation: MutableStateFlow<Animation> = MutableStateFlow(Animation.None)

    private var _runs: SnapshotStateList<SnapshotStateList<Int>> =
        mutableStateListOf(mutableStateListOf(1))

    val runs: List<List<Int>> = _runs

    var initialTickDurationMs: MutableStateFlow<String> = MutableStateFlow("300")
    var percentTickIncrease: MutableStateFlow<String> = MutableStateFlow("10")
    var lastTickMs: MutableStateFlow<String> = MutableStateFlow("1000")

    var isInitialized: Boolean = false
        private set

    fun init(context: Context) {
        val file = File(context.filesDir.absolutePath, "config.json")
        if (file.exists()) {
            viewModelScope.launch(Dispatchers.IO) {
                file.reader().use { reader ->
                    val appConfig = AppConfig.fromJson(reader.readText())
                    appConfig?.let { config -> setValuesOfConfig(config) }
                }
                isInitialized = true
            }
        } else {
            restoreDefaultConfiguration(context) {
                isInitialized = true
            }
        }

    }

    fun sendConfiguration(onSent: (Boolean) -> Unit) {
        val configDto = createConfigDto()
        send({ selectedDevice.value.configure(configDto) }, onSent)
    }

    fun sendStart(onSent: (Boolean) -> Unit) {
        send({ selectedDevice.value.start() }, onSent = onSent)
    }

    fun sendStop(onSent: (Boolean) -> Unit) {
        send({ selectedDevice.value.stop() }, onSent = onSent)
    }

    fun saveConfiguration(context: Context, onSaved: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val file = File(context.filesDir.absolutePath, "config.json")
            file.createNewFile()
            file.writeText(
                AppConfig(
                    device = selectedDevice.value.name,
                    configDto = createConfigDto()
                ).toJson()
            )
            withContext(Dispatchers.Main) {
                onSaved()
            }
        }
    }

    fun restoreDefaultConfiguration(context: Context, onFinished: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            context.assets.open("defaultConfig.json").use {
                it.reader().use { reader ->
                    val appConfig = AppConfig.fromJson(reader.readText())
                    appConfig?.let { config -> setValuesOfConfig(config) }
                }
            }
            withContext(Dispatchers.Main) {
                onFinished()
            }
        }
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
        if (!checkingConnection) {
            checkingConnection = true
            viewModelScope.launch(Dispatchers.IO) {
                while (checkingConnection) {
                    delay(1000)
                    selectedDevice.value.observeAlive()
                }
            }
        }
    }

    fun targetList(run: Int) =
        _runs.filterIndexed { index, _ -> index == run }.firstOrNull()

    private fun send(request: suspend () -> Boolean, onSent: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val success = request()
                withContext(Dispatchers.Main) {
                    onSent(success)
                }
            } catch (e: Exception) {
                println(e.message)
                withContext(Dispatchers.Main) {
                    onSent(false)
                }
            }
        }
    }

    private fun createConfigDto(): ConfigDto {
        return ConfigDto(
            initialTickDurationMs = initialTickDurationMs.value.toSanitizedInt(),
            percentTickIncrease = percentTickIncrease.value.toSanitizedInt(),
            lastTickMs = lastTickMs.value.toSanitizedInt(),
            targets = _runs,
            animation = animation.value.dtoText,
        )
    }

    private suspend fun setValuesOfConfig(appConfig: AppConfig) {
        withContext(Dispatchers.Main) {
            val dto = appConfig.configDto
            initialTickDurationMs.value = dto.initialTickDurationMs.toString()
            percentTickIncrease.value = dto.percentTickIncrease.toString()
            lastTickMs.value = dto.lastTickMs.toString()
            _runs.clear()
            dto.targets.forEach {
                _runs.add(it.toMutableStateList())
            }
            animation.value = Animation.of(dto.animation)
            selectedDevice.value = Backend.of(appConfig.device)
        }
    }
}