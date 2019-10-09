package com.example.homeworrrrrk9.Model;

import com.example.homeworrrrrk9.Model.GreenDaoHandler.UUIDConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

import java.util.UUID;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {

    @Property (nameInDb = "Uuid")
    @Index (unique = true)
    @Convert(converter = UUIDConverter.class, columnType = String.class)
    private UUID id;

    @Id (autoincrement = true)
    private long _userId;

    @Property (nameInDb = "Username")
    @NotNull
    private String username;

    @Property (nameInDb = "Password")
    @NotNull
    private String password;

    public User() {
        id = UUID.randomUUID();
    }

    public User(UUID id) {
        this.id = id;
    }

    @Generated(hash = 1434468888)
    public User(UUID id, long _userId, @NotNull String username,
            @NotNull String password) {
        this.id = id;
        this._userId = _userId;
        this.username = username;
        this.password = password;
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

    public long get_userId() {
        return _userId;
    }

    public void set_userId(long _userId) {
        this._userId = _userId;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
