package com.example.auth_module.service.auth;

import com.example.auth_module.entity.User;
import com.example.auth_module.repository.UserRepository;
import com.example.auth_module.service.auth.dto.SignupRequest;
import com.example.auth_module.service.kafka.KafkaProducerService;
import com.example.auth_module.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final KafkaProducerService kafkaProducerService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public String login(String username, String password) {
        // 1. 원래는 여기서 DB 조회를 하고 비번을 체크해야 함
        log.info("로그인 시도 중: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));


        // 2. 로그인 성공했다고 가정 (간단하게 구현)
        if (passwordEncoder.matches(password, user.getPassword())) {

            // 3. 카프카로 로그인 성공 이벤트 발행 (비동기 처리)
            // 로그인 로직이 알림 발송 때문에 지연되지 않도록 함
            kafkaProducerService.sendMessage("login-events", username);

            return tokenProvider.createToken(user.getUsername());
        } else {
            throw new RuntimeException("아이디 또는 비밀번호가 틀렸습니다.");
        }
    }

    public void register(SignupRequest request) {
        // 1. 아이디 중복 체크
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }

        // 2. 유저 엔티티 생성 및 데이터 세팅
        User user = new User();
        user.setUsername(request.getUsername());

        // 중요: 비밀번호는 반드시 암호화해서 저장!
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setFullName(request.getFullName());
        user.setDepartment(request.getDepartment());

        // 3. DB 저장
        userRepository.save(user);
    }

}
