package usst.dysrc.kaoyan.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.fastjson.JSON
import kotlinx.android.synthetic.main.fragment_post_management.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import usst.dysrc.kaoyan.ApplicationData
import usst.dysrc.kaoyan.CreateNewPost
import usst.dysrc.kaoyan.LoginActivity
import usst.dysrc.kaoyan.R
import usst.dysrc.kaoyan.adapters.PostListRecyclerViewAdapter
import usst.dysrc.kaoyan.entities.Post
import usst.dysrc.kaoyan.utils.OKHTTPUtils
import usst.dysrc.kaoyan.utils.ServerUtil

class PostManagementFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_post_management, null)
        val applicationData= activity!!.application as ApplicationData
//        view.create_new_post_imageView.setOnClickListener {
//            if (applicationData.userId==(-1).toLong()){
//                startActivity(Intent().setClass(activity,LoginActivity::class.java))
//            }else{
//                startActivity(Intent().setClass(activity,CreateNewPost::class.java))
//            }
//        }
        view.mine_postList_recyclerView.layoutManager=LinearLayoutManager(activity)
        doAsync {
            val response=OKHTTPUtils.getData(
                    ServerUtil.SERVER_HOST_URL+ServerUtil.SERVER_POST_SEARCH_POST_URL+"/"+applicationData.userId)
            val myPostList=JSON.parseArray(response.body()!!.string(),Post::class.java)
            uiThread {
                view.mine_postList_recyclerView.adapter=PostListRecyclerViewAdapter(it.context!!,myPostList)
            }
        }
        return view
    }
}