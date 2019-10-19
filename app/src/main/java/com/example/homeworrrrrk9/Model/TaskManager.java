package com.example.homeworrrrrk9.Model;

import com.example.homeworrrrrk9.Model.Database.GreenDaoHandler.StateConverter;
import com.example.homeworrrrrk9.State;
import com.example.homeworrrrrk9.Model.Database.GreenDaoHandler.UUIDConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity (nameInDb = "Task")
public class TaskManager implements Serializable {
    private static final long serialVersionUID = 42L;
    
    @Id (autoincrement = true)
    private Long _taskId;

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    @Property (nameInDb = "photo_path")
    private String photoPath;
    
    @Property (nameInDb = "Task_UUID")
    @Index (unique = true)
    @Convert(converter = UUIDConverter.class, columnType = String.class)
    private UUID mUUID;

    @Property (nameInDb = "Title")
    private String mTitle;

    @Property (nameInDb = "Detail")
    private String mDetail;

    @Property (nameInDb = "Date")
    private Date mDate;

    @Property (nameInDb = "State")
    @Convert(converter = StateConverter.class, columnType = String.class)
    private State mState;

    @Property (nameInDb = "User_id")
    private Long userId;

    @ToOne(joinProperty = "userId")
    private User mUser;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 640877836)
    private transient TaskManagerDao myDao;

    @Generated(hash = 1377221062)
    private transient Long mUser__resolvedKey;

    public TaskManager() {
        this(UUID.randomUUID());
        mDate = Calendar.getInstance().getTime();
    }

    public TaskManager(UUID UUID) {
        mUUID = UUID;
    }

    @Generated(hash = 269137197)
    public TaskManager(Long _taskId, String photoPath, UUID mUUID, String mTitle,
            String mDetail, Date mDate, State mState, Long userId) {
        this._taskId = _taskId;
        this.photoPath = photoPath;
        this.mUUID = mUUID;
        this.mTitle = mTitle;
        this.mDetail = mDetail;
        this.mDate = mDate;
        this.mState = mState;
        this.userId = userId;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDetail() {
        return mDetail;
    }

    public void setDetail(String detail) {
        mDetail = detail;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public State getState() {
        return mState;
    }

    public void setState(State state) {
        mState = state;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTaskId() {
        return _taskId;
    }

    public void setTaskId(Long taskId) {
        this._taskId = taskId;
    }

    public Long get_taskId() {
        return this._taskId;
    }

    public void set_taskId(Long _taskId) {
        this._taskId = _taskId;
    }

    public UUID getMUUID() {
        return this.mUUID;
    }

    public void setMUUID(UUID mUUID) {
        this.mUUID = mUUID;
    }

    public String getMTitle() {
        return this.mTitle;
    }

    public void setMTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getMDetail() {
        return this.mDetail;
    }

    public void setMDetail(String mDetail) {
        this.mDetail = mDetail;
    }

    public Date getMDate() {
        return this.mDate;
    }

    public void setMDate(Date mDate) {
        this.mDate = mDate;
    }

    public State getMState() {
        return this.mState;
    }

    public void setMState(State mState) {
        this.mState = mState;
    }

    public String getPhotoName(){
        return "Img_" + mUUID + ".jpg";
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 59229727)
    public User getMUser() {
        Long __key = this.userId;
        if (mUser__resolvedKey == null || !mUser__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User mUserNew = targetDao.load(__key);
            synchronized (this) {
                mUser = mUserNew;
                mUser__resolvedKey = __key;
            }
        }
        return mUser;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 901111778)
    public void setMUser(User mUser) {
        synchronized (this) {
            this.mUser = mUser;
            userId = mUser == null ? null : mUser.get_userId();
            mUser__resolvedKey = userId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 215802578)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTaskManagerDao() : null;
    }

}
