package com.social.entities;

import java.util.ArrayList;
import java.util.Collection;

import java.util.Date;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public  class User implements UserDetails{



	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id ;

	@Column(unique = true)
	private String username ;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String password ;

    private String role;

    private String fullName;

	private String nfcTag;

	private Integer maxWeight;
	private Integer saldo;

	private String status;

	private Date startActive;

	private Date endActive;

	private Double lat;
	private Double lng;
	private String phone;


	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role));
		return authorities;
	}

}
