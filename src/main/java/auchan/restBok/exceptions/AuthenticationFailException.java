package auchan.restBok.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class AuthenticationFailException extends AuthenticationException {
    public AuthenticationFailException(String msg) {super(msg);}

}
