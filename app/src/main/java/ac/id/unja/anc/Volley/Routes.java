package ac.id.unja.anc.Volley;

public class Routes {
    public String base_URL, login, register, imgProfile, editProfile, serba;

    public Routes(){
        this.base_URL  = "http://192.168.100.6:8000/api";
        this.login  = base_URL + "/login";
        this.register  = base_URL + "/register";
        this.editProfile  = base_URL + "/editProfile";
        this.imgProfile  = base_URL + "/imgProfile/";
        this.serba  = base_URL + "/serba";
    }
}
