package ac.id.unja.anc.Volley;

public class Routes {
    public String base_URL, login, register, imgProfile, editProfile, serba;
    public String forum, newForum, imgForum;

    public Routes(){
        this.base_URL  = "http://192.168.100.6:8000/api";
        this.login  = base_URL + "/login";
        this.register  = base_URL + "/register";
        this.editProfile  = base_URL + "/editProfile";
        this.imgProfile  = base_URL + "/imgProfile/";
        this.imgForum  = base_URL + "/imgForum/";
        this.serba  = base_URL + "/serba";
        this.forum = base_URL + "/forum/";
        this.newForum = base_URL + "/newForum";
    }
}
