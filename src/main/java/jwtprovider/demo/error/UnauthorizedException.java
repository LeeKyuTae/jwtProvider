package jwtprovider.demo.error;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException  extends RuntimeException{
    private static final long serialVersionUID = -2238030302650813813L;

    public UnauthorizedException() {
        super("계정 권한이 유효하지 않습니다.\n다시 로그인을 해주세요.");
    }



    public UnauthorizedException(String msg) {
        super("계정 권한이 유효하지 않습니다.다시 로그인을 해주세요.\n" + "받은 요청: " + msg + ".");
    }
}