package com.example.homeworrrrrk9.Model;

import com.example.homeworrrrrk9.Model.Database.GreenDaoHandler.UUIDConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

import java.util.UUID;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {

    @Property (nameInDb = "Uuid")
    @Index (unique = true)
    @Convert(converter = UUIDConverter.class, columnType = String.class)
    private UUID id;

    @Id (autoincrement = true)
    private Long _userId;

    @Property (nameInDb = "Username")
    @NotNull
    private String username;

    @Property (nameInDb = "Password")
    @NotNull
    private String password;

    @Property (nameInDb = "signup_date")
    private Date signupDate;

    public User() {
        id = UUID.randomUUID();
    }

    public User(UUID id) {
        this.id = id;
    }

    @Generated(hash = 333422426)
    public User(UUID id, Long _userId, @NotNull String username,
            @NotNull String password, Date signupDate) {
        this.id = id;
        this._userId = _userId;
        this.username = username;
        this.password = password;
        this.signupDate = signupDate;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long get_userId() {
        return _userId;
    }

    public void set_userId(Long _userId) {
        this._userId = _userId;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getSignupDate() {
        return this.signupDate;
    }

    public void setSignupDate(Date signupDate) {
        this.signupDate = signupDate;
    }
}
