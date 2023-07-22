import React, { useState,useEffect } from 'react'
import { useParams } from 'react-router-dom'
import { useQuery, useMutation, useQueryClient } from 'react-query'
import UserService from '../../services/UserService'
import AvatarMenu from './AvatarMenu'
import UserActivity from './UserActivity'
import Loading from '../Loading/Loading'
import AuthService from '../../services/AuthService'

export default function User() {
  const {userId} = useParams();
  const [isOpen,setOpen] = useState(false);
  const [isRefreshing, setIsRefreshing] = useState(false);

  const client = useQueryClient();

  const profileData = useQuery(`profile-data`, () => {
    return UserService.getUserById(userId);
  },{
    cacheTime: 0,
    
  });

 
  if (profileData.isLoading ||profileData.isRefetching || profileData.isFetching) {
    return <Loading/>
  }

 


  if (profileData.isError) {

    if (profileData.error && profileData.error.response.status === 401 && !profileData.isRefreshing) {
      setIsRefreshing(true);
      AuthService.refreshToken().then(() => {
        
        client.invalidateQueries('profile-data');     
      }).then(() => {
        setIsRefreshing(false);
      }).catch((error) => {
        // Token yenileme sırasında oluşabilecek hataları ele al
        setIsRefreshing(false);
        console.error('Token yenileme hatası:', error);
      });
    }
    
  }

  useEffect(() => {
    if (!isRefreshing) {
      profileData.refetch();
    }
  }, [isRefreshing]);


  const handlePopup = () =>{
    setOpen(true)
  } 

  return (
    <div className='user'>
        <div className='profile-card'>
          
            <img className='profile-card-pp' src={require(`../../assets/User/Avatar/avatar${profileData.data?.data.avatar_id}.png`)} alt="sdas" />

            <h5 className='profile-card-name'>User Name : {profileData.data?.data.userName} </h5>
            
            {profileData.data?.data.id == localStorage.getItem("currentUser") && <p onClick={handlePopup} className='avatar-button'>Change Avatar</p>}
            {isOpen && <AvatarMenu id={profileData.data?.data.avatar_id} setOpen={setOpen}/>}
        </div>
        <UserActivity id={profileData.data?.data.avatar_id} />
    </div>
  )
}