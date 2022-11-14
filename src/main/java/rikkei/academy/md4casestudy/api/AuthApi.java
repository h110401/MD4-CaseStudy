package rikkei.academy.md4casestudy.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.md4casestudy.dto.request.ChangeAvatar;
import rikkei.academy.md4casestudy.dto.request.ChangePassword;
import rikkei.academy.md4casestudy.dto.request.LoginDTO;
import rikkei.academy.md4casestudy.dto.request.SignUpDTO;
import rikkei.academy.md4casestudy.dto.response.JwtResponse;
import rikkei.academy.md4casestudy.dto.response.ResponseMessage;
import rikkei.academy.md4casestudy.model.RoleName;
import rikkei.academy.md4casestudy.model.User;
import rikkei.academy.md4casestudy.model.UserFactory;
import rikkei.academy.md4casestudy.model.Video;
import rikkei.academy.md4casestudy.security.jwt.JwtProvider;
import rikkei.academy.md4casestudy.security.jwt.JwtTokenFilter;
import rikkei.academy.md4casestudy.security.userprincipal.UserPrincipal;
import rikkei.academy.md4casestudy.service.role.IRoleService;
import rikkei.academy.md4casestudy.service.user.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthApi {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserFactory userFactory;
    private final IUserService userService;
    private final IRoleService roleService;
    private final JwtTokenFilter jwtTokenFilter;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginDTO loginDTO
    ) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(
                jwtProvider.getJwtConfig().getTokenPrefix() + jwtProvider.createToken(authentication),
                principal.getName(),
                principal.getAvatar(),
                principal.getAuthorities()
        ));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(
            @Valid
            @RequestBody
            SignUpDTO signUpDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.ok(new ResponseMessage(
                    bindingResult.getFieldErrors().stream().map(
                            fieldError -> fieldError.getField() + "-invalid"
                    ).collect(Collectors.toList())
            ));
        }
        if (userService.existsByEmail(signUpDTO.getEmail())) {
            return ResponseEntity.ok(new ResponseMessage("email-existed"));
        }
        if (userService.existsByUsername(signUpDTO.getUsername())) {
            return ResponseEntity.ok(new ResponseMessage("username-existed"));
        }
        userService.save(userFactory.build(
                null,
                signUpDTO.getName(),
                signUpDTO.getUsername(),
                signUpDTO.getEmail(),
                signUpDTO.getPassword(),
                Collections.singleton(roleService.findByName(RoleName.USER))
        ));
        return ResponseEntity.ok(new ResponseMessage("signup-success"));
    }
    @PutMapping("/change_password")
    public ResponseEntity<?> changePassword(HttpServletRequest request,
                                            @Valid @RequestBody ChangePassword changePassword){
        String jwt = jwtTokenFilter.getToken(request);
        String username = jwtProvider.getUsernameFromToken(jwt);
        User user;
        try {
            user = userService.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User Not Found wiht -> username"+username));
            boolean matches = passwordEncoder.matches(changePassword.getCurrentPassword(), user.getPassword());
            if(changePassword.getNewPassword() != null){
                if(matches){
                    user.setPassword(changePassword.getNewPassword());
                    userService.save(user);
                } else {
                    return new ResponseEntity<>(new ResponseMessage("no"), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(new ResponseMessage("yes"), HttpStatus.OK);
        } catch (UsernameNotFoundException exception){
            return new ResponseEntity<>(new ResponseMessage(exception.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/change_avatar")
    public ResponseEntity<?>changeAvatar(HttpServletRequest httpServletRequest, @Valid @RequestBody ChangeAvatar changeAvatar){
        String jwt = jwtTokenFilter.getToken(httpServletRequest);
        String username = jwtProvider.getUsernameFromToken(jwt);
        User user;
        try{
            if (changeAvatar.getAvatar()==null){
                return new ResponseEntity<>(new ResponseMessage("no"),HttpStatus.OK);
            }
            else {
                user = userService.findByUsernameOrEmail(username);
                user.setAvatar(changeAvatar.getAvatar());
                userService.save(user);
            }
            return new ResponseEntity<>(new ResponseMessage("yes"),HttpStatus.OK);

        }catch (UsernameNotFoundException e){
            return new ResponseEntity<>(new ResponseMessage(e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public ResponseEntity<?>showlistUsers(){
        Iterable<User>list = userService.findAll();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteUser(@PathVariable Long id){
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(new ResponseMessage("user does not exist!"), HttpStatus.OK);
        }
        userService.deleteById(id);
        return new ResponseEntity<>(new ResponseMessage("delete_success!"), HttpStatus.OK);
    };
    @GetMapping("/{id}")
    public ResponseEntity<?>GetUserById(@PathVariable Long id){
        return new ResponseEntity<>(userService.findById(id),HttpStatus.OK);
    }
}
