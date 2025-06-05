package biz.sunny.path.lottoa.model

import kotlinx.serialization.Serializable

@Serializable
data class LottoNumber (
    val totSellamnt: Long,      //총판매금액
    val returnValue: String,    //요청결과
    val drwNoDate: String,      //추첨날짜
    val firstWinamnt: Long,     //1등 상금액
    val drwtNo6: Int,           //로또번호 6
    val drwtNo4: Int,           //로또번호 4
    val firstPrzwnerCo: Long,   //1등 당첨인원
    val drwtNo5: Int,           //로또번호 5
    val bnusNo: Int,            //로또 보너스번호
    val firstAccumamnt: Long,   //1등 총당첨금액
    val drwNo: Long,            //로또회차
    val drwtNo2: Int,           //로또번호 2
    val drwtNo3: Int,           //로또번호 3
    val drwtNo1: Int            //로또번호 1
)