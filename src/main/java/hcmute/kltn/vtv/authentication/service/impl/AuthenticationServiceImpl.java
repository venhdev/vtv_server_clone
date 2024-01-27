package hcmute.kltn.vtv.authentication.service.impl;

import hcmute.kltn.vtv.authentication.request.LoginRequest;
import hcmute.kltn.vtv.authentication.request.RegisterRequest;
import hcmute.kltn.vtv.authentication.response.LoginResponse;
import hcmute.kltn.vtv.authentication.response.LogoutResponse;
import hcmute.kltn.vtv.authentication.response.RefreshTokenResponse;
import hcmute.kltn.vtv.authentication.response.RegisterResponse;
import hcmute.kltn.vtv.authentication.service.IAuthenticationService;
import hcmute.kltn.vtv.authentication.service.IJwtService;
import hcmute.kltn.vtv.model.dto.user.CustomerDTO;
import hcmute.kltn.vtv.model.entity.user.Customer;
import hcmute.kltn.vtv.model.entity.user.Token;
import hcmute.kltn.vtv.model.extra.Role;
import hcmute.kltn.vtv.model.extra.Status;
import hcmute.kltn.vtv.model.extra.TokenType;
import hcmute.kltn.vtv.repository.user.CustomerRepository;
import hcmute.kltn.vtv.repository.user.TokenRepository;
import hcmute.kltn.vtv.util.exception.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final IJwtService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final TokenRepository tokenRepository;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private int refreshExpiration;

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest customerRequest) {
        customerRequest.validate();

        Optional<Customer> existingCustomer = customerRepository.findByUsername(customerRequest.getUsername());
        if (existingCustomer.isPresent()) {
            throw new DuplicateEntryException("Tài khoản đã tồn tại.");
        }

        existingCustomer = customerRepository.findByEmail(customerRequest.getEmail());
        if (existingCustomer.isPresent()) {
            throw new DuplicateEntryException("Email đã được đăng ký.");
        }

        Customer customer = modelMapper.map(customerRequest, Customer.class);
        Set<Role> roles = new HashSet<>();
        roles.add(Role.CUSTOMER); // Every customer has a CUSTOMER role
        customer.setRoles(roles);
        customer.setPassword(passwordEncoder.encode(customerRequest.getPassword()));
        customer.setCreateAt(LocalDateTime.now());
        customer.setUpdateAt(LocalDateTime.now());
        customer.setStatus(Status.ACTIVE);

        try {
            var saveCustomer = customerRepository.save(customer);
            RegisterResponse registerResponse = modelMapper.map(saveCustomer, RegisterResponse.class);
            registerResponse.setStatus("success");
            registerResponse.setMessage("Đăng ký tài khoản khách hàng thành công");

            return registerResponse;
        } catch (Exception e) {
            throw new InternalServerErrorException("Đăng ký tài khoản khách hàng thất bại");
        }

    }

    @Override
    public Customer addNewCustomer(RegisterRequest customerRequest) {
        Optional<Customer> existingCustomer = customerRepository.findByUsername(customerRequest.getUsername());
        if (existingCustomer.isPresent()) {
            throw new DuplicateEntryException("Tài khoản đã tồn tại.");
        }
        existingCustomer = customerRepository.findByEmail(customerRequest.getEmail());
        if (existingCustomer.isPresent()) {
            throw new DuplicateEntryException("Email đã được đăng ký.");
        }

        Customer customer = modelMapper.map(customerRequest, Customer.class);
        Set<Role> roles = new HashSet<>();
        roles.add(Role.CUSTOMER); // Every customer has a CUSTOMER role
        customer.setRoles(roles);
        customer.setPassword(passwordEncoder.encode(customerRequest.getPassword()));
        customer.setCreateAt(LocalDateTime.now());
        customer.setUpdateAt(LocalDateTime.now());
        customer.setStatus(Status.ACTIVE);

        try {
            customerRepository.save(customer);
            return customer;
        } catch (Exception e) {
            throw new InternalServerErrorException("Đăng ký tài khoản khách hàng thất bại");
        }
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest, HttpServletResponse response) {
        loginRequest.validate();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        Customer customer = customerRepository.findByUsernameAndStatus(loginRequest.getUsername(), Status.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Tài khoản không tồn tại."));

        var jwtToken = jwtService.generateToken(customer);
        var refreshToken = jwtService.generateRefreshToken(customer);
        saveCustomerToken(customer, refreshToken);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setCustomerDTO(modelMapper.map(customer, CustomerDTO.class));
        loginResponse.setStatus("OK");
        loginResponse.setMessage("Đăng nhập thành công");
        loginResponse.setCode(200);
        loginResponse.setAccessToken(jwtToken);
        loginResponse.setRefreshToken(refreshToken);

        // Lưu refreshToken vào cookie
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/"); // Đặt đúng path mà bạn muốn
        cookie.setMaxAge(refreshExpiration); // Set thời gian sống của cookie (ví dụ: 30 ngày)
        response.addCookie(cookie); // Thêm cookie vào response

        return loginResponse;
    }

    @Override
    @Transactional
    public LogoutResponse logout(String refreshToken, HttpServletResponse response) {

        if (refreshToken == null) {
            throw new BadRequestException("Token không được null. Đăng xuất thất bại.");
        }

        var storedToken = tokenRepository.findByToken(refreshToken);
        if (storedToken.isEmpty()) {
            throw new NotFoundException("Token không tồn tại. Đăng xuất thất bại.");
        }

        var token = storedToken.get();
        if (token.isExpired() || token.isRevoked()) {
            throw new UnauthorizedAccessException("Token đã hết hạn. Đăng xuất thất bại.");
        }

        token.setExpired(true);
        token.setRevoked(true);
        try {
            tokenRepository.save(token);
            SecurityContextHolder.clearContext();

            // Xóa refreshToken trong cookie
            Cookie cookie = new Cookie("refreshToken", null);
            cookie.setHttpOnly(true);
            cookie.setPath("/"); // Đặt đúng path mà bạn muốn
            cookie.setMaxAge(0); // Set thời gian sống của cookie (ví dụ: 30 ngày)
            response.addCookie(cookie); // Thêm cookie vào response

            return new LogoutResponse("Success", "Đăng xuất thành công", 200);
        } catch (Exception e) {
            throw new InternalServerErrorException("Đăng xuất thất bại");

        }

    }

    public void saveCustomerToken(Customer customer, String jwtToken) {
        var token = Token.builder()
                .customer(customer)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public void revokeAllCustomerTokens(Customer customer) {
        var validUserTokens = tokenRepository.findAllValidTokenByCustomer(customer.getCustomerId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public RefreshTokenResponse refreshToken(String refreshToken, HttpServletRequest request,
                                             HttpServletResponse response)
            throws IOException {
        if (refreshToken == null) {
            throw new BadRequestException("Refresh token không tồn tại.");
        }

        if (jwtService.isTokenExpired(refreshToken)) {
            throw new BadRequestException("Refresh token đã hết hạn.");
        }

        // Validate and extract username from refreshToken
        String username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            Optional<Customer> optionalCustomer = customerRepository.findByUsername(username);

            if (optionalCustomer.isPresent()) {
                Customer customer = optionalCustomer.get();

                if (jwtService.isTokenValid(refreshToken, customer)) {
                    String accessToken = jwtService.generateToken(customer);
                    revokeAllCustomerTokens(customer);
                    saveCustomerToken(customer, accessToken);

                    RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse();
                    refreshTokenResponse.setAccessToken(accessToken);
                    refreshTokenResponse.setStatus("Success");
                    refreshTokenResponse.setMessage("Refresh token thành công");
                    refreshTokenResponse.setCode(200);
                    return refreshTokenResponse;
                }
            } else {
                throw new NotFoundException("Tài khoản không tồn tại.");
            }
        } else {
            throw new BadRequestException("Lỗi xác thực token.");
        }
        return null;
    }

}
