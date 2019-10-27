package ac.id.unja.anc.Models;

public class Chat {
    private String id, sender_id, msg, time, status, img;

    public Chat(String id, String sender_id, String msg, String time, String status, String img){
        this.id = id;
        this.sender_id = sender_id;
        this.msg = msg;
        this.time = time;
        this.status = status;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
