package com.ssafy.enjoytrip.member.model.service;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.exception.DuplicatedMemberException;
import com.ssafy.enjoytrip.exception.MemberException;
import com.ssafy.enjoytrip.member.model.dto.LoginVo;
import com.ssafy.enjoytrip.member.model.dto.MemberDto;
import com.ssafy.enjoytrip.member.model.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final PasswordEncoder encoder;
	private final MemberMapper memberMapper;

	@Override
	public boolean duplicateCheck(String username) {
		try {
			int count = memberMapper.duplicateUsernameCheck(username);
			log.info("duplicate count : {}", count);
			return count > 0;
		} catch (SQLException e) {
			throw new MemberException("중복 체크 중 에러 발생.");
		}
	}

	@Override
	public Optional<MemberDto> getMemberById(Integer userId) {
		try {
			return memberMapper.findById(userId);
		} catch (SQLException e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
	}

	@Override
	public Optional<MemberDto> getMemberByUsername(String username) {
		try {
			return memberMapper.findByUsername(username);
		} catch (SQLException e) {
			throw new UsernameNotFoundException("존재하지 않는 사용자입니다.");
		}
	}

	@Override
	public void registMember(MemberDto memberDto) {

		log.info("regist member : {}", memberDto);
		try {
			Optional<MemberDto> m = getMemberByUsername(memberDto.getUsername());

			if (m.isPresent()) {
				throw new DuplicatedMemberException("이미 존재하는 사용자입니다.");
			}

			memberDto.setPassword(encoder.encode(memberDto.getPassword()));
			memberMapper.insertMember(memberDto);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public MemberDto loginMember(LoginVo loginVo) {

		log.info("login vo : {}", loginVo);
		Optional<MemberDto> m = getMemberByUsername(loginVo.getUsername());
		log.info("Optional member : {}", m);
		if (!m.isPresent()) {
			throw new UsernameNotFoundException("존재하지 않는 사용자입니다.");
		}

		MemberDto member = m.get();

		if (encoder.matches(loginVo.getPassword(), member.getPassword())) {
			return member;
		} else {
			throw new BadCredentialsException("인증 정보가 잘못되었습니다.");
		}
	}

	@Override
	public void updateMember(MemberDto memberDto) {
		log.info("update member : {}", memberDto);
		try {
			if (validateUpdateDto(memberDto)) {
				if (memberDto.getPassword() != null) {
					String encodedPassword = encoder.encode(memberDto.getPassword());
					memberDto.setPassword(encodedPassword);
				}
				memberMapper.updateMember(memberDto);
			} else {
				throw new MemberException("기존 정보와 동일한 정보입니다.");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void deleteMember(Integer userId) {
		log.info("delete member : {}", userId);
		try {
			memberMapper.deleteMember(userId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean validateUpdateDto(MemberDto updateDto) {

		Optional<MemberDto> m = getMemberById(updateDto.getUserId());

		if (!m.isPresent()) {
			throw new MemberException("존재하지 않는 사용자 고유번호입니다.");
		}

		MemberDto member = m.get();
		if (updateDto.getPassword() != null && encoder.matches(updateDto.getPassword(), member.getPassword())) {
			return false;
		}

		if (updateDto.getEmail() != null && updateDto.getEmail().equals(member.getEmail())) {
			return false;
		}

		return true;
	}
}
