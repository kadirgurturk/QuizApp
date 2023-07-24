import React from 'react'
import CommentForm from './CommentForm'
import { useQuery,useQueryClient } from 'react-query'
import CommentService from '../../services/CommentService';
import AuthService from '../../services/AuthService';
import {useNavigate } from "react-router-dom"
import Loading from '../Loading/Loading';

export default function Comment({postId}) {

  const postData = useQuery(`post-comment`, () => {
    
    return CommentService.getCommentsByPostId(postId)
  });

  const client = useQueryClient();


  const navigate = useNavigate();

  const handleClick = (userId) => {
    navigate('/user/' + userId);
  };

  if (postData.isError) {
    
    if (
      postData.error &&
      postData.error.response.status === 401 &&
      !postData.isRefreshing
    ) {
      AuthService.refreshToken()
        .then(() => {
          client.invalidateQueries('post-comment');
        })
        .then(async () => {
          await postData.refetch();
        })
        .catch((error) => {
          
        });
    }
  }

  if(postData.isLoading){
    return <Loading/>
  }


  return (
    <div className='commentList'>
        {localStorage.getItem("currentUser") != null && <CommentForm postId={postId} />  }
        {postData.data?.data.map((comment) => {
        return (
          <div className="commentCard" key={comment.id}>
            <div onClick={() => handleClick(comment.userId)} className="pp">
              <img src={require(`../../assets/User/Avatar/avatar${comment.avatarId}.png`)} alt="" />
            </div>
            <p className="comment-text">{comment.text}</p>
          </div>
        );
      })}
        
    </div>
  )
}
