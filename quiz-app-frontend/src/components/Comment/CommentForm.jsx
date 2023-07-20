import React,{useState} from 'react'
import { UseMutation, useMutation, useQueryClient } from 'react-query'
import CommentSerive from '../../services/CommentService'
import AuthService from '../../services/AuthService'
import Loading from '../Loading/Loading';


export default function CommentForm({postId}) {

  
  const [postText,setText] = useState("")
  const client = useQueryClient();

  const {mutate,isLoading} = useMutation((newComment)=>{

    CommentSerive.saveComment(newComment)

},{
    onSuccess : () => {
      client.invalidateQueries("post-comment")
        setText("")
    },
    onError : (error) =>{
      if(error.response?.status === 401){
        AuthService.refresh()
      }
    }

})


const addComment = () =>{

  setText("");

  const newComment =  {

      text :  postText,
      post_ıd: postId,
      user_id : localStorage.getItem("currentUser"),
  }
    if(postText !== ""){
      mutate(newComment)
    }
}

if(isLoading){
  return <Loading/>
}

  return (
    <div className='comment-form'>
      <div className='pp-comment'><span>{}</span></div>
      <textarea rows={3} className="comment-text"  placeholder='Yorum giriniz' onChange={(e) =>{setText(e.target.value)}}/>
      <button onClick={()=>{addComment()}} id='post-btn'><img src="" alt="" />Gönder</button>
    </div>
  )
}
