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

    refresh()
    {
        var refreshRequest = {
            userId : localStorage.getItem("currentUser"),
            refreshToken : localStorage.getItem("refreshToken")
        }
        return axios.post(AUTH_URL + "refresh", refreshRequest)
    }

}

export default new AuthService();