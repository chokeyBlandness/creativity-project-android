package usst.dysrc.kaoyan.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_account_logged.view.*
import kotlinx.android.synthetic.main.fragment_account_unlog.view.*
import usst.dysrc.kaoyan.*

class AccountFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View
        val applicationData:ApplicationData= activity!!.application as ApplicationData
        if (applicationData.userId==(-1).toLong()){
            //show unlog fragment
            view=inflater.inflate(R.layout.fragment_account_unlog, null)
            view.trans_login_button.setOnClickListener {
                startActivity(Intent().setClass(activity,LoginActivity::class.java))
            }
            view.trans_register_button.setOnClickListener {
                startActivity(Intent().setClass(activity,RegisterActivity::class.java))
            }
        }else{
            //show logged fragment
            view=inflater.inflate(R.layout.fragment_account_logged,null)
            view.showing_userName_textView.text=applicationData.userName
            view.showing_targetSchool_textView.text=applicationData.targetSchool
            view.showing_targetProfession_textView.text=applicationData.targetProfession
            view.logout_button.setOnClickListener {
                applicationData.userId=(-1).toLong()
                startActivity(Intent().setClass(activity,MainActivity::class.java))
            }
            view.change_user_info_button.setOnClickListener {
                startActivity(Intent().setClass(activity,ChangeUserInfoActivity::class.java))
            }
            view.clockIn_button.setOnClickListener {
                startActivity(Intent().setClass(activity,ClockInActivity::class.java))
            }
            view.mine_collections_button.setOnClickListener {
                startActivity(Intent().setClass(activity,MineCollectionListActivity::class.java))
            }
        }
        return view
    }
}
