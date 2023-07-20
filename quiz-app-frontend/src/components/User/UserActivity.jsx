import React, { useState } from 'react'
import UserActive from './UserActivity/UserActive';

export default function UserActivity() {

    const [selected,setSelected] = useState(2);

  

  return (
    <div className='user-activity'>
        <div className="user-activity-navbar">
            <div onClick={()=>{setSelected(1)}} className={selected == 1 ? "selected-lists" : "lists"}>
                Posts
            </div>
            <div onClick={()=>{setSelected(2)}} className={selected == 2 ? "selected-lists" : "lists"}>
                Comments 
            </div>
            <div onClick={()=>{setSelected(3)}} className={selected == 3 ? "selected-lists" : "lists"}>
                Likes
            </div>
        </div>

        <UserActive val={selected} />
    </div>
  )
}
