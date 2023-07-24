import React,{useState} from 'react'
import { UseMutation, useMutation, useQueryClient,useQuery } from 'react-query'
import PostService from '../../services/PostService'
import Check from "../../assets/Post/check.svg"
import AuthService from '../../services/AuthService'
import Loading from '../Loading/Loading'


export default function PostForm() {

    const [postTitle,setTitle] = useState("")
    const [postText,setText] = useState("")
    const [isSent, setSent] = useState(false)

    const client = useQueryClient();

    const postMutation = useMutation((newPost)=>{
        return PostService.savePost(newPost)
        
    },{  
        onSuccess : () => {
            client.invalidateQueries("post-data")
            setTitle("")
            setText("")
        },

        onError: (err) =>{
            AuthService.refreshToken()
            .then(() => {
              client.invalidateQueries('post-data');
            })
            .then(async () => {
                 savePost()
                 client.invalidateQueries('post-data');
            })
            .catch((err) => {
              // Token yenileme sırasında oluşabilecek hataları ele al
              console.err('Token yenileme hatası:', err);
            });
        },
       
    })



    const savePost = () =>{

        const newPost =  {

            text :  postText,
            title  : postTitle,
            user_id : localStorage.getItem("currentUser"),

        }

        postMutation.mutate(newPost)
        changeSent();
    }

    const changeSent = () =>{
        setSent(true)

        setTimeout(()=>{ setSent(false)},1500)
    }
    
    if(postMutation.isLoading){
        return <Loading/>
      }

  return (
    
        <div className='postForm' >
            {isSent && <div className='post-popup'><img src={Check}/> your post has sent</div>}
            <div className='pp'><span>{}</span></div>
            <input value={postTitle} id='title-input' type="text" placeholder='Başlık Giriniz' maxLength="52" onChange={(e) =>{setTitle(e.target.value)}}/>
            <textarea rows={10} value={postText} id='text-input' type="text" placeholder='İçeriği Giriniz' aria-multiline maxLength="255" onChange={(e) =>{setText(e.target.value)}}/>

                <button onClick={()=>{savePost()}} id='post-btn'><img src="" alt="" />Gönder</button>
           
          </div>
    
  )
}
