package com.example.usuario.ludiuca.clases;

/**
 * Created by Usuario on 17/02/2016.
 */
public class Avatar {
    private int idAvatar;
    private String photo;
    private Boolean flag = false;

    public Avatar(int idAvatar, String foto){
        photo = foto;
        this.idAvatar = idAvatar;

    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public int getIdAvatar() {
        return idAvatar;
    }

    public String getPhoto() {
        return photo;
    }

    public void setIdAvatar(int idAvatar) {
        this.idAvatar = idAvatar;
    }
}
