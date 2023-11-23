package com.ssafy.enjoytrip.member.model.service;

public interface RefreshTokenService {

    public void setData(String key, Object value);

    public String getData(String key);

    public void deleteData(String key);
}
