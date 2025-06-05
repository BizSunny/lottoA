package biz.sunny.path.lottoa.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import biz.sunny.path.lottoa.comm.RetrofitClient
import biz.sunny.path.lottoa.model.LottoNumber
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

class SplashViewModel(application: Application) : AndroidViewModel(application) {
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _lottoNumbers = MutableStateFlow<List<LottoNumber>>(emptyList())
    val lottoNumbers: StateFlow<List<LottoNumber>> = _lottoNumbers

    private val lottoDataFileName = "lottoNumbers.txt"
    private val apiService = RetrofitClient.lottoApiService
    private val gson = Gson()

    init{
        loadLottoData()
    }

    private fun loadLottoData(){
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try{
                val existingData = readLottoNumbersFromFile()
                val currentHighestDrwNo = existingData.maxOfOrNull{
                    it.drwNo
                } ?: 0

                val updatedData = mutableListOf<LottoNumber>()
                updatedData.addAll(existingData)

                var nextDrwNoToFetch: Long = (currentHighestDrwNo + 1).toLong()
                var hasNewData = false

                while (true){
                    val response = apiService.getLottoNumber(nextDrwNoToFetch)

                    if(response.isSuccessful && response.body() != null){
                        val lottoNum = response.body()!!

                        if(lottoNum.returnValue == "success"){
                            updatedData.add(lottoNum)
                            hasNewData = true
                            nextDrwNoToFetch++
                        }
                        else{
                            Log.d("SplashViewModel", "API Response: ${lottoNum.returnValue} for drwNo: $nextDrwNoToFetch")
                            break
                        }
                    }
                    else{
                        Log.e("SplashViewModel", "API call failed for drwNo: $nextDrwNoToFetch - ${response.code()}")
                        break
                    }
                }

                if(hasNewData || existingData.isEmpty()){
                    saveLottoNumbersToFile(updatedData)
                }
                else{
                    saveLottoNumbersToFile(updatedData)
                }

                _lottoNumbers.value = readLottoNumbersFromFile().sortedBy{ it.drwNo}
                _isLoading.value = false
            }
            catch(e: Exception){
                Log.e("SplashViewModel", "Failed to load lotto data: ${e.message}", e)
                _errorMessage.value = "로또 데이터를 로드하는데 실패했습니다: ${e.localizedMessage}"
                _isLoading.value = false
            }
        }
    }

    private suspend fun readLottoNumbersFromFile(): List<LottoNumber> = withContext(Dispatchers.IO){
        val file = File(getApplication<Application>().filesDir, lottoDataFileName)

        if(!file.exists()){
            return@withContext emptyList()
        }

        try{
            val jsonString = file.readText()
            val type = object : TypeToken<List<LottoNumber>>() {}.type
            gson.fromJson(jsonString, type) ?: emptyList()
        }
        catch (e: IOException){
            Log.e("SplashViewModel", "Error reading lotto data from file: ${e.message}")
            emptyList()
        }
        catch(e: Exception){
            Log.e("SplashViewModel", "Error parsing lotto data from file: ${e.message}")
            emptyList()
        }
    }

    private suspend fun saveLottoNumbersToFile(lottoNumbers: List<LottoNumber>) = withContext(Dispatchers.IO){
        val file = File(getApplication<Application>().filesDir, lottoDataFileName)

        try{
            val jsonString = gson.toJson(lottoNumbers)
            file.writeText(jsonString)
            Log.d("SplashViewModel", "Lotto data saved to file: ${file.absolutePath}")
        }
        catch(e: IOException){
            Log.e("SplashViewModel", "Error saving lotto data to file: ${e.message}")
        }
    }
}