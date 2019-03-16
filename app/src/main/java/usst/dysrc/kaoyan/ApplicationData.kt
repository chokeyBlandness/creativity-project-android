package usst.dysrc.kaoyan

import android.app.Application

class ApplicationData: Application() {
    var userId:Long= (-1).toLong()
    var userName:String?=null
    var userGender: Int? =null
    var targetProfession: String? = null
    var targetSchool: String? = null
}