import axios from "axios";


const COMMENT_URL = "/comments/"


class CommentService {
    
    getCommentsByPostId( 
        postId)
    {
        return axios.get(COMMENT_URL + "?post=" + postId,{
            headers: {
              'Authorization': localStorage.getItem("tokenKey")
            }
          })
    }

    getCommentsByUserId(userId)
    {
        return axios.get(COMMENT_URL + "?user=" + userId,{
            headers: {
              'Authorization': localStorage.getItem("tokenKey")
            }
          })
    }

    saveComment(newComment)
    {
        return  axios.post(COMMENT_URL, newComment,{
            headers: {
              'Authorization': localStorage.getItem("tokenKey")
            }
          });
    }

    getCommentById( postId)
    {
        return 
    }

     updateCommentById(commentId,commentUpdate)
    {
        return 
    }

    deleteOneComment( postId)
    {
        
    }


}

export default new CommentService();