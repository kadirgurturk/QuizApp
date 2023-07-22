import axios from "axios";


const AUTH_URL = "/auth/"


class AuthService {
    
    login(loginRequest)
    {
        return axios.post(AUTH_URL + "login", loginRequest)
    }

    register(registerRequest)
    {
        return axios.post(AUTH_URL + "register", registerRequest)
    }

    refreshToken()
    {
        var refreshRequest = {
            userId : localStorage.getItem("currentUser"),
            refreshToken : localStorage.getItem("refreshToken")
        }
        return axios.post(AUTH_URL + "refresh", refreshRequest).then(res => res.data)
        .then(rest => { 
                        
                        localStorage.setItem("tokenKey",rest.accessToken)
                      })    
    }
}

export default new AuthService();