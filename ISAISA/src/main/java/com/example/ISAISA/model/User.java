package com.example.ISAISA.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static javax.persistence.DiscriminatorType.STRING;


@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="rolee", discriminatorType=STRING)
@Table(name="users")
public class User implements UserDetails {

    @Id
    @SequenceGenerator(name="seq_user", sequenceName = "seq_user", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName",nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    @Column(name = "enabled")
    private boolean enabled;

    @Column
    private Integer flag;



    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;

    @Transient
    public String getDecriminatorValue() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Complaint> complaints;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;



    public User() {}

    public User(Integer id, String email, String password, String firstName, String lastName, String address, String phone, String city, String country) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.city = city;
        this.country = country;
    }

    public User( String email, String password, String firstName, String lastName, String address, String phone, String city, String country) {

        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.city = city;
        this.country = country;
    }

    //Settes and getters

    public Integer getId() { return id;}

    public void setId(Integer id) { this.id = id; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email;}

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) { this.address = address; }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPassword(String password) {
        Timestamp now = new Timestamp(new Date().getTime());
        this.setLastPasswordResetDate(now);
        this.password = password;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) { this.lastPasswordResetDate = lastPasswordResetDate; }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    //Nije potrebno za ovu aplikaciju konkretno
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
