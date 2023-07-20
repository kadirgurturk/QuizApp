import React,{useState,useEffect} from 'react'
import { useQuery, useMutation, useQueryClient } from 'react-query'
import {useNavigate } from "react-router-dom"
import PostService from '../../services/PostService'
import AuthService from '../../services/AuthService'
import Like from "../../assets/Post/like.svg"
import CommentImg from "../../assets/Post/comment.svg"
import PostForm from './PostForm'
import Comment from '../Comment/Comment'
import LikeService from '../../services/LikeService'
import Loading from '../Loading/Loading'


export default function Post() {
  const [openId, setOpenId] = useState(null);

  const { isLoading, data, isError } = useQuery(`post-data`, () => {
    return PostService.getAllPosts();
  });

  const client = useQueryClient();

    const addLike = useMutation((newLike)=>{
       LikeService.createLike(newLike)
       
    },{
        onSuccess : () => {
            client.invalidateQueries("post-data")
        },
        onError : (error) =>{
          if (error?.response?.status === 401) {
            AuthService.refresh();
          }
        }
    })

    const deleteLike = useMutation((likeToDelete)=>{

      LikeService.deleteByPostIdAndUserId(likeToDelete.user_id,likeToDelete.post_ıd)

   },{
       onSuccess : () => {
         
           client.invalidateQueries("post-data")
       },
        onError : (error) =>{
          if (error?.response?.status === 401) {
            AuthService.refresh();
          }
        }
   })

  const navigate = useNavigate();

  const handleClick = (userId) => {
    navigate('/user/' + userId);
  };

  const handleComment = (id) => {
    setOpenId((prevId) => (prevId === id ? null : id));
  };

  if (isLoading || deleteLike.isLoading || addLike.isLoading) {
    return <Loading/>
  }

  if (isError) {
    const errorResponse = isError.response;

    if (errorResponse && errorResponse.status === 401) {
      AuthService.refresh().then(() => {
         // refresh işleminden sonra veriyi yeniden getir
      });
    }

    return <div>An error occurred while fetching the data.</div>;
  }

  const isLiked = (post) =>{
   if(localStorage.getItem("currentUser") != null){
    return post.likes?.find(p => p.userId == localStorage.getItem("currentUser"))
   }
   
  }

  const likePost = (post) =>{

    if(isLiked(post)){

      const likeToDelete = {
        post_ıd :post.id,
        user_id : localStorage.getItem("currentUser")
      }
      
      deleteLike.mutate(likeToDelete)
    }else{
      const newLike = {
        post_ıd :post.id,
        user_id : localStorage.getItem("currentUser")
      }
      addLike.mutate(newLike)
    }
  }


  return (
    <div className="postList">
      {localStorage.getItem("currentUser") != null && <PostForm/>  }
      {data?.data.map((post) => {
        return (
          <div className="postCard" key={post.id}>
            <div onClick={() => handleClick(post.userId)} className="pp">
              <img src={require(`../../assets/User/Avatar/avatar${post.avatarId}.png`)} alt="" />
            </div>
            <h3>{post.title}</h3>
            <p className="post-text">{post.text}</p>
            <div className="icons">
              <img
                onClick={()=>likePost(post)}
                id="like"
                src={Like}
                alt="like"
                style={isLiked(post) ? {opacity: 1} : { opacity: 0.2 } }
              />
              <span id='likes'>
                {post.likes.length}
              </span>
              <img
                className={openId === post.id ? "activeCommentImg" : "commentImg"}
                onClick={() => handleComment(post.id)}
                src={CommentImg}
                alt="comment"
              />
            </div>
            {openId === post.id && <Comment postId={post.id} />}
          </div>
        );
      })}
    </div>
  );
}
