import React, { useState } from 'react'
import AuthService from '../../services/AuthService';
import {useNavigate } from "react-router-dom"
import Loading from '../Loading/Loading';



export default function Auth() {

  const [username,setUsername] = useState("");
  const [password,setPassword] = useState("");
  const [isLoading,setLoading] = useState(false);

  const navigate = useNavigate();
  
  
  const handleLogin = () =>{
    const userLogin = {
        userName: username,
        password: password
    }
    setLoading(true)

    AuthService.login(userLogin).then(res => res.data)
    .then(rest => { 
                   localStorage.setItem("tokenKey",rest.accessToken)
                   localStorage.setItem("refreshToken",rest.refreshToken)
                   localStorage.setItem("currentUser",rest.userId)
                   localStorage.setItem("UserName",username)
                   
                  }).then(() => {
                    setUsername("");
                    setPassword("");
                    setLoading(false)
                    navigate("/");
                  })
    .catch(err => console.log(err))

   
  } 

  const handleRegister = () =>{

    const userRegister= {
      userName: username,
      password: password
  }

  AuthService.register(userRegister).then(res => res.json())
  .then(rest => {localStorage.setItem("tokenKey",rest.messege)
                 localStorage.setItem("currentUser",rest.userId)
                 localStorage.setItem("UserName",username)
                })
  .catch(err => console.log(err))

    setUsername("")
    setPassword("")
  }

  return (
    <div className='Auth'>
        {isLoading && <Loading/>}
        <form className='Auth-form'  >
            <label htmlFor="userName">Kullanıcı Adı</label>
            <input value={username}  onChange={(e)=>{setUsername(e.target.value)}} className='form_input' type="text" id="userName" name="userName" minLength="3" placeholder='Kullancı Adı Giriniz' required/>
  
            <label htmlFor="password">Şifre</label>
            <input value={password}  onChange={(e)=>{setPassword(e.target.value)}} className='form_input' type="password" id="password" name="password" placeholder='Şifrenizi Giriniz' minLength="3" required/>

            
            <button onClick={handleLogin}  className='form_btn' id='login-btn'  type="button" value="Gönder">Giriş Yap</button>
            <button onClick={handleRegister} className='form_btn' id='register-btn'  type="button" value="Gönder">Kayıt Ol</button>
        </form>

    </div>
  )
}
