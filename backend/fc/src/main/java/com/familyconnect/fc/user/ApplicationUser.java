package com.familyconnect.fc.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.familyconnect.fc.authentication.Role;
import com.familyconnect.fc.utils.Enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name="users")
public class ApplicationUser implements UserDetails{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	
    private Integer userId;
	@Column(unique=true)
	@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",flags = Pattern.Flag.CASE_INSENSITIVE)
    private String username;
    private String password;
    private Integer familyId = -1;

	private String name;

	private int profilePictureId;

	private String profilePictureUrl;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
        name="user_role_junction",
        joinColumns = {@JoinColumn(name="user_id")},
        inverseJoinColumns = {@JoinColumn(name="role_id")}
    )
    private Set<Role> authorities;

    public ApplicationUser() {
		super();
		authorities = new HashSet<>();
	}

	public ApplicationUser( String username,String name, String password, Set<Role> authorities) {
		super();
		this.username = username;
		this.name = name;
		this.password = password;
		this.authorities = authorities;
	}

    public Integer getUserId() {
		return this.userId;
	}
	
	public void setId(Integer userId) {
		this.userId = userId;
	}
	
	public void setAuthorities(Set<Role> authorities) {
		this.authorities = authorities;
	}

	public void setFamilyId(Integer familyId) {
		this.familyId = familyId;
	}
	
	public Integer getFamilyId() {
		return this.familyId;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	public boolean isChild() {
		for(Role role : authorities) {
			if(role.getAuthority().equals(UserRole.CHILD.name())) {
				return true;
			}
		}
		return false;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	/* If you want account locking capabilities create variables and ways to set them for the methods below */
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	public int getProfilePictureId() {
		return this.profilePictureId;
	}

	public void setProfilePictureId(int profilePictureId) {
		this.profilePictureId = profilePictureId;
	}

	// check if user is a parent
	public boolean isParent() {
		for(Role role : authorities) {
			if(role.getAuthority().equals(UserRole.PARENT.name())) {
				return true;
			}
		}
		return false;
	}

	public String getProfilePictureUrl() {
		return this.profilePictureUrl;
	}

	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}


}
