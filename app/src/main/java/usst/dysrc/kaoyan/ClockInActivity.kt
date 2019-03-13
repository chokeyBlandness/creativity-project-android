package usst.dysrc.kaoyan

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.alibaba.fastjson.JSON
import kotlinx.android.synthetic.main.activity_clock_in.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import usst.dysrc.kaoyan.entities.Clocked
import usst.dysrc.kaoyan.utils.OKHTTPUtils
import usst.dysrc.kaoyan.utils.ServerUtil
import usst.dysrc.kaoyan.utils.SingleCalendarTransaction
import java.util.*

class ClockInActivity:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clock_in)
        val applicationData = application as ApplicationData
        var clockedList:List<Clocked> = ArrayList()
        doAsync {
            val response = OKHTTPUtils.getData(ServerUtil.SERVER_HOST_URL + ServerUtil.SERVER_USER_GET_CLOCKED_LIST+"/"+applicationData.userId)
            clockedList = JSON.parseArray(response.body()!!.string(),Clocked::class.java)
            uiThread {
                for (clocked in clockedList) {
                    if (Date().date == clocked.clockInDate!!.date) {
                        clock_in_editText.visibility = View.GONE
                        submit_clock_in_button.visibility = View.GONE
                        clock_in_text_view.text=clocked.content
                        break
                    }
                }
            }
        }
        submit_clock_in_button.setOnClickListener {
            if (clock_in_editText.text == null || clock_in_editText.text.isEmpty()) {
                Toast.makeText(this,"打卡内容不能为空",Toast.LENGTH_SHORT).show()
            } else {
                doAsync {
                    val newClocked = Clocked()
                    newClocked.userId=applicationData.userId
                    newClocked.content= clock_in_editText.text.toString()
                    newClocked.clockInDate = Date()
                    val response = OKHTTPUtils.postData(
                            ServerUtil.SERVER_HOST_URL + ServerUtil.SERVER_USER_CREATE_NEW_CLOCKED
                            , JSON.toJSONString(newClocked))
                    uiThread {
                        Toast.makeText(it, response.body()!!.string(),Toast.LENGTH_SHORT).show()
                        finish()
                        startActivity(intent)
                    }

                }
            }
        }
//        for (clocked in clockedList) {
//            if (Date().date == clocked.clockInDate!!.date) {
//                clock_in_editText.visibility = View.GONE
//                submit_clock_in_button.visibility = View.GONE
//                break
//            }
//        }
        single_calendar_view.setCalendarTransaction(object :SingleCalendarTransaction{
            override fun manipulateTextView(date: Date, textView: TextView) {
                for (clocked in clockedList) {
                    if (clocked.clockInDate!!.date == date.date)
                        textView.setOnClickListener {
                            clock_in_text_view.text=clocked.content
                        }
                }
            }

            override fun manipulateIconView(date: Date, iconView: ImageView) {
//                val calendar:Calendar= Calendar.getInstance()
                for (clocked in clockedList) {
                    if (clocked.clockInDate!!.date == date.date)
                        iconView.visibility=View.VISIBLE
                }
            }
        })

    }
}