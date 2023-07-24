import React,{useState} from 'react'
import { UseMutation, useMutation, useQueryClient } from 'react-query'
import CommentSerive from '../../services/CommentService'
import AuthService from '../../services/AuthService'
import Loading from '../Loading/Loading';


export default function CommentForm({postId}) {

  
  const [postText,setText] = useState("")
  const client = useQueryClient();

  const commentMutation = useMutation((newComment)=>{

    return CommentSerive.saveComment(newComment)

},{
    onSuccess : () => {
      client.invalidateQueries("post-comment")
        setText("")
    },
    onError: (err) =>{
      AuthService.refreshToken()
      .then(() => {
        client.invalidateQueries('post-comment');
      })
      .then(async () => {
            addComment();
           client.invalidateQueries('post-comment');
      })
      .catch((err) => {
        // Token yenileme sırasında oluşabilecek hataları ele al
        console.err('Token yenileme hatası:', err);
      });
  },

})


const addComment = () =>{

  setText("");

  const newComment =  {

      text :  postText,
      post_ıd: postId,
      user_id : localStorage.getItem("currentUser"),
  }
    if(postText !== ""){
      commentMutation.mutate(newComment)
    }
}

if(commentMutation.isLoading){
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
