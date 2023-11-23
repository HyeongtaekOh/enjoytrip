package com.ssafy.enjoytrip.member.model.service;

import java.util.HashMap;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class HashMapRefreshTokenService implements RefreshTokenService {
	
	HashMap<String, Object> store = new HashMap<String, Object>();

	@Override
	public void setData(String key, Object value) {
		// TODO Auto-generated method stub
		store.put(key, value);
	}

	@Override
	public String getData(String key) {
		// TODO Auto-generated method stub
		return (String) store.get(key);
	}

	@Override
	public void deleteData(String key) {
		// TODO Auto-generated method stub
		store.remove(key);
	}

}
