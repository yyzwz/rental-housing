package app.com.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import app.com.model.param.AppAdmimParameter;

/**
 * @author 郑为中
 * @房屋实体类
 */
@Entity
@Table(name = "sys_user")
@Cache(region = "all", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AppAdmim extends AppAdmimParameter {
	
	private static final long serialVersionUID = 9030294844559970211L;
	
	@Id
	@GeneratedValue
	private Long id;
	

	@Column(name = "email", length = 30)
	private String email; 
	
	@Column(name = "last_logintime", length = 30)
	private Date last_logintime; 
	
	@Column(name = "password", length = 32)
	private String password; 
	
	@Column(name = "real_name", length = 30)
	private String real_name; 
	
	@Column(name = "role", length = 6)
	private int role; 
	
	@Column(name = "tel", length = 15)
	private String tel; 
	
	@Column(name = "user_name", length = 50)
	private String user_name; 
	
	@Column(name = "departmentId", length = 20)
	private Long departmentId; 
	
	@Column(name = "departmentName", length = 50)
	private String departmentName; 
	
	@Column(name = "userGrade", length = 30)
	private String userGrade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getLast_logintime() {
		return last_logintime;
	}

	public void setLast_logintime(Date last_logintime) {
		this.last_logintime = last_logintime;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getUserGrade() {
		return userGrade;
	}

	public void setUserGrade(String userGrade) {
		this.userGrade = userGrade;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((departmentId == null) ? 0 : departmentId.hashCode());
		result = prime * result + ((departmentName == null) ? 0 : departmentName.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((last_logintime == null) ? 0 : last_logintime.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((real_name == null) ? 0 : real_name.hashCode());
		result = prime * result + role;
		result = prime * result + ((tel == null) ? 0 : tel.hashCode());
		result = prime * result + ((userGrade == null) ? 0 : userGrade.hashCode());
		result = prime * result + ((user_name == null) ? 0 : user_name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppAdmim other = (AppAdmim) obj;
		if (departmentId == null) {
			if (other.departmentId != null)
				return false;
		} else if (!departmentId.equals(other.departmentId))
			return false;
		if (departmentName == null) {
			if (other.departmentName != null)
				return false;
		} else if (!departmentName.equals(other.departmentName))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (last_logintime == null) {
			if (other.last_logintime != null)
				return false;
		} else if (!last_logintime.equals(other.last_logintime))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (real_name == null) {
			if (other.real_name != null)
				return false;
		} else if (!real_name.equals(other.real_name))
			return false;
		if (role != other.role)
			return false;
		if (tel == null) {
			if (other.tel != null)
				return false;
		} else if (!tel.equals(other.tel))
			return false;
		if (userGrade == null) {
			if (other.userGrade != null)
				return false;
		} else if (!userGrade.equals(other.userGrade))
			return false;
		if (user_name == null) {
			if (other.user_name != null)
				return false;
		} else if (!user_name.equals(other.user_name))
			return false;
		return true;
	} 
	
	

}
