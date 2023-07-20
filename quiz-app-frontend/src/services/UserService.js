import axios from "axios";


const USERS_URL = "/users/"


class UsersService {
    
    
    getAllUsers()
    {
        return ;
    }

    
    createUser( newUser)
    {
        return ;
    }

    ///{userId}")
     getUserById( userId)
    {
        
        return axios.get(USERS_URL + userId,{
            headers: {
              'Authorization': localStorage.getItem("tokenKey")
            }
          })
    }

    //"/{userId}")
    updateUserById( userId,user)
    {
        return 
    }

    
    updateAvatarById( userId,avatarId)
    {
        return axios.put(USERS_URL + "?userid="+userId+"&avatarId="+avatarId,{},{
            headers: {
              'Authorization': localStorage.getItem("tokenKey")
            }
          });
    }

    //"/{userId}")
     deleteOneUser(userId)
    {
        axios.delete(USERS_URL + userId,{
            headers: {
              'Authorization': localStorage.getItem("tokenKey")
            }
          });
    }

}

export default new UsersService();