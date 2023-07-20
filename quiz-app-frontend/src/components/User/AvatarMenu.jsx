import React from 'react'
import UserService from '../../services/UserService'
import AuthService from '../../services/AuthService';
import { useQuery, useMutation, useQueryClient } from 'react-query'
import Loading from '../Loading/Loading'


export default function AvatarMenu({id,setOpen}) {

    const client = useQueryClient();

    const avatarMutate = useMutation((num)=>{
        console.log(localStorage.getItem("currentUser") + num);
        UserService.updateAvatarById(localStorage.getItem("currentUser"),num)
    },{
        onSuccess : () => {
            client.invalidateQueries("profile-data")
        },
        onError : (error) =>{
          if (error?.response?.status === 401) {
            AuthService.refresh();
          }
        }
    })



    const closePopUp = (e) =>{
       
        if(e.target !== e.currentTarget){
        e.stopPropagation();
       }else{
        setOpen(false)
       }
       
    }

    const changeAvatar = (num) =>{

        avatarMutate.mutate(num)
    }

    if(avatarMutate.isLoading){
      return <Loading/>
    }



  return (
    <div className='popup'  onClick={closePopUp}>
        <div className="avatar-menu">
           <div className="logos">
           {[0 ,1, 2, 3, 4, 5, 6, 7 ,8 ,9 , 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]
            .map(num => (
                    <div key={num} onClick={() =>changeAvatar(num)} className={ num == id ? "avatar-menu-logo-active":'avatar-menu-logo'}>
                         <img className='avatar-menu-pp' src={require(`../../assets/User/Avatar/avatar${num}.png`)} alt="sdas" />
                    </div>
            ))}
           </div>
        </div>
    </div>
  )
}
