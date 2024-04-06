package com.familyconnect.fc.authentication;

import org.springframework.security.core.GrantedAuthority;

import com.familyconnect.fc.utils.RoleEnum.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="role_id")
    private Integer roleId;

    private String authority;

    public Role(){
        super();
    }

    public Role(UserRole authority){
        this.authority = authority.name();
    }

    public Role(Integer roleId, UserRole authority){
        this.roleId = roleId;
        this.authority = authority.name();
    }

    @Override
    public String getAuthority() {
        // TODO Auto-generated method stub
        return authority.toString();
    }

    public void setAuthority(UserRole authority){
        this.authority = authority.name();
    }

    public Integer getRoleId(){
        return this.roleId;
    }

    public void setRoleId(Integer roleId){
        this.roleId = roleId;
    }
}
