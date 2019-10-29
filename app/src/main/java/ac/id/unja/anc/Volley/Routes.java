package ac.id.unja.anc.Volley;

public class Routes {
    public String base_URL;
    public static String baseUrl = "http://192.168.43.28";
    public String checkLogin, login, register, imgProfile, editProfile, serba;
    public String forum, newForum, newResponse, imgForum;
    public String getId, newChat, imgChat, chats;

    public Routes(){
        this.base_URL  = baseUrl + "/api";
        this.login  = base_URL + "/login";
        this.checkLogin  = base_URL + "/checkLogin";
        this.register  = base_URL + "/register";
        this.editProfile  = base_URL + "/editProfile";
        this.imgProfile  = base_URL + "/imgProfile/";
        this.imgForum  = base_URL + "/imgForum/";
        this.serba  = base_URL + "/serba";
        this.forum = base_URL + "/forum/";
        this.newForum = base_URL + "/newForum";
        this.newResponse = base_URL + "/newResponse";
        this.newChat = base_URL + "/newChat";
        this.chats = base_URL + "/chats/";
        this.imgChat = base_URL + "/imgChat/";
        this.getId = base_URL + "/getId/";
    }
}
