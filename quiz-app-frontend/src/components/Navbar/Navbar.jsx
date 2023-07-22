import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import Power from "../../assets/NavBar/power.svg"
import {useNavigate } from "react-router-dom"
export default function Navbar() {


  const [flag, setFlag] = useState(true);

  const navigate = useNavigate();

  useEffect(() => {
    const currentUser = localStorage.getItem("currentUser");
    setFlag(currentUser === null);
  }, [localStorage.getItem("currentUser")]); // currentUser değiştiğinde useEffect'i tetikle


  const Quit = () => {
    localStorage.removeItem("refreshToken");
    localStorage.removeItem("tokenKey");
    localStorage.removeItem("currentUser");
    localStorage.removeItem("UserName");

    navigate("/auth");
  }

  
  return (
    <div className='navbar'>
      <ul className='links'>
        {flag ? (
          <li><Link to={"/auth"}>Login/Register</Link></li>
        ) : (
          <div className='quit-user'>
            <img onClick={Quit} src={Power} alt="Power" />
            <li><Link to={{ pathname: "/user/" + localStorage.getItem("currentUser") }}>User</Link></li>
          </div>
        )}
        <li><Link to={"/"}>Home</Link></li>
      </ul>
    </div>
  );
}