package rikkei.academy.md4casestudy.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.md4casestudy.dto.request.LoginDTO;
import rikkei.academy.md4casestudy.dto.request.SignUpDTO;
import rikkei.academy.md4casestudy.dto.response.JwtResponse;
import rikkei.academy.md4casestudy.dto.response.ResponseMessage;
import rikkei.academy.md4casestudy.model.RoleName;
import rikkei.academy.md4casestudy.model.UserFactory;
import rikkei.academy.md4casestudy.security.jwt.JwtProvider;
import rikkei.academy.md4casestudy.security.userprincipal.UserPrincipal;
import rikkei.academy.md4casestudy.service.role.IRoleService;
import rikkei.academy.md4casestudy.service.user.IUserService;

import javax.validation.Valid;
import java.util.Collections;
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
                jwtProvider.createToken(authentication),
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
        if(userService.existsByEmail(signUpDTO.getEmail())) {
            return ResponseEntity.ok(new ResponseMessage("email-existed"));
        }
        if(userService.existsByUsername(signUpDTO.getUsername())) {
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
}
