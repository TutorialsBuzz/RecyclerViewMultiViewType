package com.tutorialsbuzz.kotlinhelloworld

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tutorialsbuzz.recyclerviewmultipleview.Comments
import com.tutorialsbuzz.recyclerviewmultipleview.PostInfo
import com.tutorialsbuzz.recyclerviewmultipleview.PostModel
import com.tutorialsbuzz.recyclerviewmultipleview.R
import kotlinx.android.synthetic.main.comment_view.view.*
import kotlinx.android.synthetic.main.post_view.view.*


class CustomAdapter(val postModel: PostModel, val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val postView: Int = 1
    val commentView: Int = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        if (viewType == postView) {
            //postview
            return PostViewType(layoutInflater.inflate(R.layout.post_view, parent, false))
        } else {
            //commentview
            return CommentViewType(layoutInflater.inflate(R.layout.comment_view, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PostViewType) {
            (holder as PostViewType).bindPostView(postModel.postInfo, context)
        } else {
            (holder as CommentViewType).bindCommentView(
                postModel.comments.get(position - 1),
                context
            )
        }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return postModel.comments.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            //postView
            return postView
        } else {
            //commentView
            return commentView
        }
    }

    //ViewHolder for PostView
    inner class PostViewType(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.addComment.setOnClickListener(View.OnClickListener {
                postModel.comments.add(
                    Comments(
                        "Guest",
                        "Just Now",
                        itemView.comment_box.text.toString()
                    )
                )
                itemView.comment_box.setText("")
                notifyItemChanged(0)
                notifyItemInserted(postModel.comments.lastIndex)
            })
        }

        fun bindPostView(postInfo: PostInfo, context: Context): Unit {
            val id =
                context.resources.getIdentifier(postInfo.postImage, "drawable", context.packageName)
            itemView.profile_pic.setImageResource(id)
            itemView.commentLabel.text = "Comments : " + (itemCount - 1)
            itemView.post_img.setImageResource(id)
            itemView.post_time.text = postInfo.postTime
            itemView.userName.text = postInfo.userName
        }
    }

    //ViewHolder for CommentView
    inner class CommentViewType(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindCommentView(comments: Comments, context: Context): Unit {
            var id = context.resources.getIdentifier(
                comments.userName.toLowerCase(),
                "drawable",
                context.packageName
            )
            //if id is 0 then set default launcher icon for comment user dp
            if (id == 0)
                id = context.resources.getIdentifier("ic_launcher", "mipmap", context.packageName)

            itemView.comment_profile_pic.setImageResource(id)
            itemView.comment_username.text = comments.userName
            itemView.commentDesc.text = comments.comment
            itemView.commentTime.text = comments.postTime

        }
    }
}