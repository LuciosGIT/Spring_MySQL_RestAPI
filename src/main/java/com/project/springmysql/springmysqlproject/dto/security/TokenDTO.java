package com.project.springmysql.springmysqlproject.dto.security;
import java.util.Date;
import java.io.Serializable;
import java.util.Objects;

public class TokenDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private Boolean authenticated;
    private Date created;
    private Date expiration;
    private String accessToken;
    private String refreshToken;

    public TokenDTO(){

    }
    public TokenDTO(String username,
                    Boolean authenticated,
                    Date created,
                    Date expiration,
                    String accessToken,String refreshToken) {
        this.username = username;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
        this.created = created;
        this.authenticated = authenticated;
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenDTO tokenDTO = (TokenDTO) o;
        return Objects.equals(username, tokenDTO.username) && Objects.equals(authenticated, tokenDTO.authenticated) && Objects.equals(created, tokenDTO.created) && Objects.equals(expiration, tokenDTO.expiration) && Objects.equals(accessToken, tokenDTO.accessToken) && Objects.equals(refreshToken, tokenDTO.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, authenticated, created, expiration, accessToken, refreshToken);
    }
}
