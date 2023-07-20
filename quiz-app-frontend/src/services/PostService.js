import axios from "axios";


const POST_URL = "/posts/"

class PostService {

    getAllPosts()
    {
        return axios.get(POST_URL);
    }

    getAllPostsByUserId(userId)
    {
        return axios.get(POST_URL + "?user=" + userId,{
            headers: {
              'Authorization': localStorage.getItem("tokenKey")
            }
          });
    }

    
    savePost(newPost)
    {
        return axios.post(POST_URL, newPost,{
            headers: {
              'Authorization': localStorage.getItem("tokenKey")
            }
          });
    }

    //"/{postId}"
   getPostById(postId)
    {
        return axios.get(POST_URL + "/" + postId);
    }

    //"/{postId}")
    updatePostById( postId, postUpdate)
    {
        return 
    }

    //"/{postId}")
    eleteOnePost( postId)
    {
        return
    }




}

export default new PostService();