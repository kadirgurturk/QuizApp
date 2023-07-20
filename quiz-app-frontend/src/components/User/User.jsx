import React, { useState,useEffect } from 'react'
import { useParams } from 'react-router-dom'
import { useQuery, useMutation, useQueryClient } from 'react-query'
import UserService from '../../services/UserService'
import AvatarMenu from './AvatarMenu'
import UserActivity from './UserActivity'
import Loading from '../Loading/Loading'


export default function User() {

  const {userId} = useParams();
  const [isOpen,setOpen] = useState(false);


  const { isLoading, data, isError } = useQuery(`profile-data`, () => {
    return UserService.getUserById(userId);  
  });

 
  
  if (isLoading) {
    return <Loading/>
  }

  if (isError) {
    return <h2>Error 404</h2>;
  }

  const handlePopup = () =>{
    setOpen(true)
  } 

  


  return (
    <div className='user'>
        <div className='profile-card'>
          
            <img className='profile-card-pp' src={require(`../../assets/User/Avatar/avatar${data?.data.avatar_id}.png`)} alt="sdas" />

            <h5 className='profile-card-name'>User Name : {data?.data.userName} </h5>
            
            {data?.data.id == localStorage.getItem("currentUser") && <p onClick={handlePopup} className='avatar-button'>Change Avatar</p>}
            {isOpen && <AvatarMenu id={data?.data.avatar_id} setOpen={setOpen}/>}
        </div>
        <UserActivity id={data?.data.avatar_id} />
    </div>
  )
}

