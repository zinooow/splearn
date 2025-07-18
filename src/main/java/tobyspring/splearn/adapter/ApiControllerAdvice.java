package tobyspring.splearn.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tobyspring.splearn.domain.member.DuplicateEmailException;
import tobyspring.splearn.domain.member.DuplicateProfileException;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception exception) {
        return getProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    @ExceptionHandler({DuplicateEmailException.class, DuplicateProfileException.class})
    public ProblemDetail handleDuplicateEmailException(Exception exception) {
        return getProblemDetail(HttpStatus.CONFLICT, exception);
    }

    private static ProblemDetail getProblemDetail(HttpStatus status, Exception exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, exception.getMessage());

        problemDetail.setProperty("timestamp", LocalDateTime.now());
        problemDetail.setProperty("exception", exception.getClass().getSimpleName());

        return problemDetail;
    }
}
