import React,{useState,useEffect} from 'react'
import { BrowserRouter, Routes,Route } from 'react-router-dom';
import "./css/app.css";
import Post from './components/Post/Post';
import Home from "./components/Home/Home"
import Navbar from './components/Navbar/Navbar';
import User from './components/User/User';
import Auth from './components/Auth/Auth';
import {useNavigate } from "react-router-dom"


function App() {

  const navigate = useNavigate();

  return (
    <div className="App">
      
      <Navbar/>
      <div class="container">
        <Routes >
          <Route path='/' Component={Home}></Route>
          <Route path='/user/:userId' Component={User}></Route>
          
           {localStorage.getItem("currentUser") == null && <Route path='/auth' Component={Auth}></Route>}
          
        </Routes>
      </div>

    </div>
  );
}

export default App;
