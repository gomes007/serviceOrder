package com.softwarehouse.serviceorder.security.service;

import com.softwarehouse.serviceorder.domain.Employee;
import com.softwarehouse.serviceorder.exceptions.impl.UnauthorizedException;
import com.softwarehouse.serviceorder.model.UserRegistration;
import com.softwarehouse.serviceorder.security.domain.User;
import com.softwarehouse.serviceorder.security.domain.UserRoles;
import com.softwarehouse.serviceorder.security.repository.UserRepository;
import com.softwarehouse.serviceorder.security.utils.RandomPassword;
import com.softwarehouse.serviceorder.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    private final EmailService emailService;

    private final TokenService tokenService;

    public UserService(final UserRepository repository,
                       final PasswordEncoder passwordEncoder,
                       final RoleService roleService,
                       final EmailService emailService,
                       final TokenService tokenService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.emailService = emailService;
        this.tokenService = tokenService;
    }

    public User findUserByLogin(final String login) {
        return this.repository.findById(login).orElseThrow(() -> new UnauthorizedException("login or password not matches"));
    }

    public boolean userExists(final String login) {
        return this.findUserByLogin(login) != null;
    }

    public void passwordMatches(final String rawPassword, final User loggingUser) {
        if (!this.passwordEncoder.matches(rawPassword, loggingUser.getPassword()))
            throw new UnauthorizedException("login or password not matches");
    }

    public void createDefaultUser() {
        if (this.repository.count() <= 0) {
            final User user = new User();

            user.setLogin("admin");
            user.setPassword(this.passwordEncoder.encode("admin"));
            final List<UserRoles> userRoles = Stream
                    .of(this.roleService.getAdminRole())
                    .map(userRole -> new UserRoles(user.getLogin(), userRole))
                    .toList();
            user.setRoles(userRoles);
            user.setExternalReference(-1L);

            this.repository.save(user);
        }
    }

    @Async
    public void createSystemUser(final String email, final Long externalId) {
        final User user = new User();
        final String password = RandomPassword.generate();

        user.setLogin(email);
        user.setPassword(this.passwordEncoder.encode(password));
        final List<UserRoles> userRoles = Stream
                .of(this.roleService.getSystemRole())
                .map(userRole -> new UserRoles(user.getLogin(), userRole))
                .toList();
        user.setRoles(userRoles);
        user.setExternalReference(externalId);

        final User created = this.repository.save(user);

        final String registrationToken = this.tokenService.generateFirstAccessToken(user);

        final String url = "http://localhost:8080/users/registration?tk=%s".formatted(registrationToken);

        final UserRegistration payload = UserRegistration
                .builder()
                .email(created.getLogin())
                .password(password)
                .url(url)
                .build();

        final String message = "Complete aqui o seu registro: %s".formatted(payload.getUrl());

        log.info("trying to send registration e-mail to: {}, body: {}", payload.getEmail(), payload.getUrl());
        this.emailService.sendEmail(email, "Complete seu Registro", message);
    }
}
