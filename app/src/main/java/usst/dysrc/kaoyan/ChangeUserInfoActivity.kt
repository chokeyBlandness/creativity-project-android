package usst.dysrc.kaoyan

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.alibaba.fastjson.JSON
import kotlinx.android.synthetic.main.activity_change_user_info.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import usst.dysrc.kaoyan.entities.User
import usst.dysrc.kaoyan.utils.OKHTTPUtils
import usst.dysrc.kaoyan.utils.ServerUtil

class ChangeUserInfoActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_user_info)
        var applicationData:ApplicationData=application as ApplicationData
        change_user_info_confirm_button.setOnClickListener { _ ->

            if (change_user_targetProfession_editText.text.isEmpty() ||
                    change_user_targetSchool_editText.text.isEmpty()) {
                Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show()
            } else {
                doAsync {
                    var changingUser=User()
                    changingUser.userId=applicationData.userId
                    changingUser.userGender= if(change_user_male_radioButton.isChecked) 0 else 1
                    changingUser.targetProfession=change_user_targetProfession_editText.text.toString()
                    changingUser.targetSchool=change_user_targetSchool_editText.text.toString()
                    val changeUserInfoResponse=OKHTTPUtils.postData(
                            ServerUtil.SERVER_HOST_URL + ServerUtil.SERVER_USER_CHANGE_USER_INFO_URL,
                            JSON.toJSONString(changingUser))
                    uiThread {
                        Toast.makeText(it,changeUserInfoResponse.body()!!.string(),Toast.LENGTH_SHORT).show()
                        startActivity(Intent().setClass(it,MainActivity::class.java))
                    }
                }
            }

        }

    }
}