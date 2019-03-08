package usst.dysrc.kaoyan

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.alibaba.fastjson.JSON
import kotlinx.android.synthetic.main.activity_post_detail.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import usst.dysrc.kaoyan.adapters.CommentListRecyclerViewAdapter
import usst.dysrc.kaoyan.entities.Collection
import usst.dysrc.kaoyan.entities.Comment
import usst.dysrc.kaoyan.entities.PostDetail
import usst.dysrc.kaoyan.utils.OKHTTPUtils
import usst.dysrc.kaoyan.utils.ServerUtil
import java.text.SimpleDateFormat
import java.util.*

class PostDetailActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)
        val applicationData:ApplicationData=application as ApplicationData
        val postId=this.intent.getLongExtra("postId",-1)
        if (postId!=(-1).toLong()){
            refreshPostDetail(postId,applicationData.userId)
            create_new_comment_action_button.setOnClickListener {
                if (create_new_comment_editText.text==null||create_new_comment_editText.text.isEmpty()){
                    Toast.makeText(this,"不能为空",Toast.LENGTH_SHORT).show()
                }else{
                    doAsync {
                        val newComment=Comment()
                        newComment.postId=postId
                        newComment.userId=applicationData.userId
                        newComment.content=create_new_comment_editText.text.toString()
                        newComment.createDate=Date()
                        val response=OKHTTPUtils.postData(
                                ServerUtil.SERVER_HOST_URL+ServerUtil.SERVER_POST_INCREASE_COMMENT_URL,
                                JSON.toJSONString(newComment))
                        uiThread {
                            Toast.makeText(it,response.body()!!.string(),Toast.LENGTH_SHORT).show()
                        }
                    }
                    refreshPostDetail(postId,applicationData.userId)
                }
            }
            admire_button.setOnClickListener{ _ ->
                doAsync {
                    val response=OKHTTPUtils.postData(
                            ServerUtil.SERVER_HOST_URL+ServerUtil.SERVER_POST_ADMIRE_URL,
                            JSON.toJSONString(postId))
                    uiThread {
                        detailPost_admireNum_textView.text=response.body()!!.string()
                    }
                }
            }
            collect_button.setOnClickListener { _ ->
                doAsync {
                    val newCollection=Collection()
                    newCollection.postId=postId
                    newCollection.userId=applicationData.userId
                    val response=OKHTTPUtils.postData(
                            ServerUtil.SERVER_HOST_URL+ServerUtil.SERVER_POST_COLLECT_POST_URL,
                            JSON.toJSONString(newCollection))
                    uiThread {
                        Toast.makeText(it, response.body()!!.string(),Toast.LENGTH_SHORT).show()
                    }
                }
            }
            delete_post_button.setOnClickListener{
                doAsync {
                    val response=OKHTTPUtils.postData(
                            ServerUtil.SERVER_HOST_URL+ServerUtil.SERVER_POST_DELETE_POST_URL,
                            JSON.toJSONString(postId))
                    //todo
                    uiThread {
                        Toast.makeText(it, response.body()!!.string(),Toast.LENGTH_SHORT).show()
                        finish()
                        startActivity(Intent().setClass(it,MainActivity::class.java))
                    }
                }
            }
        }
    }

    //function to refresh the data that responses from server
    private fun refreshPostDetail(postId:Long,applicationUserId:Long)=
        doAsync {
            val response=OKHTTPUtils.getData(ServerUtil.SERVER_HOST_URL+ServerUtil.SERVER_POST_SEARCH_POST_DETAIL+"/"+postId)
            val postDetail:PostDetail= JSON.parseObject(response.body()!!.string(),PostDetail::class.java)
            uiThread {
                detailPost_userName_textView.text=postDetail.post!!.userName
                detailPost_title_textView.text=postDetail.post!!.title
//                detailPost_createDate_textView.text=postDetail.post!!.createDate.toString()
                detailPost_createDate_textView.text=SimpleDateFormat("yyyy-MM-dd").format(postDetail.post!!.createDate)
                detailPost_content_textView.text=postDetail.post!!.content
                detailPost_admireNum_textView.text= postDetail.post!!.admireNum.toString()
                detailPost_commentList_recyclerView.layoutManager=LinearLayoutManager(it)
                detailPost_commentList_recyclerView.adapter=CommentListRecyclerViewAdapter(it, postDetail.commentList!!)
                if (applicationUserId== postDetail.post!!.userId){
                    delete_post_button.visibility= View.VISIBLE
                }
            }
        }


    //when the back key were pressed,turn to mainActivity
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        startActivity(Intent().setClass(this,MainActivity::class.java))
    }
}