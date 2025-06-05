package biz.sunny.path.lottoa.comm

import biz.sunny.path.lottoa.model.LottoNumber
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LottoApiService {
    @GET("common.do?method=getLottoNumber")
    suspend fun getLottoNumber(@Query("drwNo") drwNo: Long): Response<LottoNumber>
}