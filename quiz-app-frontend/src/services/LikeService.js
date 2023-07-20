import axios from "axios";


const LIKE_URL = "/likes/"


class LikeService {
    
    getLikes(
             userId,
             postId
    )
    {
        return axios.get(LIKE_URL,{
            headers: {
              'Authorization': localStorage.getItem("tokenKey")
            }
          });
    }

   
    getLikesbyUserId(userId,)
    {
        return axios.get(LIKE_URL +"?user=" + userId,{
            headers: {
              'Authorization': localStorage.getItem("tokenKey")
            }
          });
    }

  
    getAllLikesbyPostId(
             postId
    )
    {
        return axios.get(LIKE_URL + "?post=" + postId,{
            headers: {
              'Authorization': localStorage.getItem("tokenKey")
            }
          });
    }

    
    createLike(newLike)
    {
        return axios.post(LIKE_URL, newLike, {
            headers: {
              'Authorization': localStorage.getItem("tokenKey")
            }
          })
    }

    //"/{likeId}"
    getLikeById(likeId)
    {
        return 
    }


    //"/{likeId}")
    deleteOneLike( likeId)
    {
        return
    }

    
    deleteByPostIdAndUserId(
            userId,
            postId
    )
    {
        return axios.delete(LIKE_URL+"?user="+ userId + "&post=" + postId,{
            headers: {
              'Authorization': localStorage.getItem("tokenKey")
            }
          });
    }
}

export default new LikeService();