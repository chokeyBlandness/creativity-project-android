package usst.dysrc.kaoyan.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import usst.dysrc.kaoyan.R
import usst.dysrc.kaoyan.entities.Comment
import java.text.SimpleDateFormat

class CommentListRecyclerViewAdapter(private val context: Context,private val commentList:List<Comment>)
    :RecyclerView.Adapter<CommentListRecyclerViewAdapter.CommentItemViewHolder>(){
    override fun getItemCount()=commentList.size

    override fun onBindViewHolder(holder: CommentItemViewHolder, position: Int) {
        holder.userName_textView.text=commentList[position].userName
        holder.content_textView.text=commentList[position].content
        holder.createDate_textView.text=SimpleDateFormat("yyyy-MM-dd").format(commentList[position].createDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentItemViewHolder {
        return CommentItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_comment,parent,false))
    }


    inner class CommentItemViewHolder(val view: View): RecyclerView.ViewHolder(view){
        var userName_textView=view.findViewById<TextView>(R.id.item_comment_userName)
        var content_textView=view.findViewById<TextView>(R.id.item_comment_content)
        var createDate_textView=view.findViewById<TextView>(R.id.item_comment_createDate)
    }
}