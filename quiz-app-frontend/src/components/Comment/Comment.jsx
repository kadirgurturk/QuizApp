import React from 'react'
import CommentForm from './CommentForm'
import { useQuery } from 'react-query'
import CommentService from '../../services/CommentService';
import AuthService from '../../services/AuthService';
import {useNavigate } from "react-router-dom"
import Loading from '../Loading/Loading';

export default function Comment({postId}) {

  const { isLoading, data, isError, error,refetch } = useQuery(`post-comment`, () => {
    
    return CommentService.getCommentsByPostId(postId)
  });


  const navigate = useNavigate();

  const handleClick = (userId) => {
    navigate('/user/' + userId);
  };

  if (isError) {
    
    console.log(error);

    if (error && error.status === 401) {
      AuthService.refresh();
    }

    return <div>An error occurred while fetching the data.</div>;
  }

  if(isLoading){
    return <Loading/>
  }


  return (
    <div className='commentList'>
        {localStorage.getItem("currentUser") != null && <CommentForm postId={postId} />  }
        {data?.data.map((comment) => {
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
