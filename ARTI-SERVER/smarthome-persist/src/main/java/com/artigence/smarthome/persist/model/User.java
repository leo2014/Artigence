
package com.artigence.smarthome.persist.model;

import com.artigence.smarthome.persist.model.security.Role;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/** 
 * This class represents the basic user object.
 *
 * @author leipeng
 */
@Entity@DynamicInsert@DynamicUpdate
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 4733464888738356502L;

    @Id
    @GenericGenerator(name="idGenerator", strategy="native")
    @GeneratedValue(generator = "idGenerator")
    private Long id;

	@Column(name = "app_id", nullable = false, length = 32,unique = true)
	private String appId;

    @Size(min=4, max=20)
    @Column(name = "username", nullable = false, length = 20, unique = true)
    private String username;

    @Size(min=6)
    @Column(name = "password", nullable=false)
    private String password;

    @Email
    @Column(name = "email", length = 64, unique=true)
    private String email;

    @Column(name = "name", length = 64)
    private String name;

    @Column(name = "register_date", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date registerDate;

    @OneToMany(mappedBy="user")
    private List<UserLoginRecord> loginRecords;

    @Column(name="email_validation")
    private boolean emailValidation;
    
    @Column(name="cellphone")
    private String cellphone;
    
    @Column(name="age")
    private int age;
    
    @Column(name="address")
    private String address;
    
    @ManyToOne(targetEntity=UserGroup.class,fetch=FetchType.LAZY)
    @JoinColumn(name="user_group_id",nullable=true)
    private UserGroup userGroup;
    
    @ManyToMany(targetEntity=Role.class)
    @JoinTable(
    		name="user_role",
    		joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")},
    		inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="id")}
    		)
    private List<Role> roles;

    @ManyToMany(targetEntity=Node.class)
    @JoinTable(
    		name="user_operable_node",
    		joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")},
    		inverseJoinColumns={@JoinColumn(name="node_id", referencedColumnName="id")}
    		)
    private List<Node> operableNodes;
    
    public User() {
    }

    public User(final String username) {
        this.username = username;
    }

    
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	public List<UserLoginRecord> getLoginRecords() {
		return loginRecords;
	}

	public void setLoginRecords(List<UserLoginRecord> loginRecords) {
		this.loginRecords = loginRecords;
	}

	public boolean getEmailValidation() {
		return emailValidation;
	}

	public void setEmailValidation(boolean emailValidation) {
		this.emailValidation = emailValidation;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Node> getOperableNodes() {
		return operableNodes;
	}

	public void setOperableNodes(List<Node> operableNodes) {
		this.operableNodes = operableNodes;
	}

	@Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) {
            return false;
        }

        final User obj = (User) o;
        if (username != null ? !username.equals(obj.username)
                : obj.username != null) {
            return false;
        }
        if (!(this.registerDate.getTime() == obj.registerDate.getTime())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 29 * result + (username != null ? username.hashCode() : 0);
        result = 29 * result
                + (registerDate != null ? registerDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }

}
