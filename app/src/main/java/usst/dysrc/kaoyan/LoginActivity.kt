package usst.dysrc.kaoyan

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.alibaba.fastjson.JSON
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.Response
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import usst.dysrc.kaoyan.entities.User
import usst.dysrc.kaoyan.utils.OKHTTPUtils
import usst.dysrc.kaoyan.utils.ResultEnum
import usst.dysrc.kaoyan.utils.ServerUtil

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login_button.setOnClickListener { _ ->
            val loginUser=User()
            loginUser.email= login_email_editText.text.toString()
            loginUser.password=login_password_editText.text.toString()
            doAsync {
                val loginResponse: Response =
                        OKHTTPUtils.postData(
                                ServerUtil.SERVER_HOST_URL + ServerUtil.SERVER_USER_LOGIN_URL,
                                JSON.toJSONString(loginUser))
                val loginMessage:String=JSON.parseObject(loginResponse.body()!!.string(),String::class.java)
                uiThread {
                    if (!loginMessage.equals(ResultEnum.LOGIN_ERROR.message,false) &&
                            !loginMessage.equals(ResultEnum.LOGIN_PSW_ERROR.message,false)) {
//                        applicationData.userId=loginMessage.toLong()
                        val userInfo = JSON.parseObject(loginMessage, User::class.java)
                        val applicationData:ApplicationData=application as ApplicationData
                        applicationData.userId= userInfo.userId!!
                        applicationData.userName=userInfo.userName
                        applicationData.userGender=userInfo.userGender
                        applicationData.targetSchool=userInfo.targetSchool
                        applicationData.targetProfession=userInfo.targetProfession
                        finish()
                        startActivity(Intent().setClass(it,MainActivity::class.java))
                    }else{
                        Toast.makeText(it,loginMessage,Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }
        transRegisterButton.setOnClickListener {
            startActivity(Intent().setClass(this,RegisterActivity::class.java))
        }
    }


}
