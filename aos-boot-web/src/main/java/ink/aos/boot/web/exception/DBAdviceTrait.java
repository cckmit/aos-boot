package ink.aos.boot.web.exception;

import org.apiguardian.api.API;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.AdviceTrait;

import static org.apiguardian.api.API.Status.INTERNAL;
import static org.apiguardian.api.API.Status.STABLE;

/**
 * All rights Reserved, Designed By aos.ink
 *
 * @version V1.0
 * @author: lichaohn@163.com
 * @date: 2020-09-16
 * @Copyright: 2019 www.aos.ink All rights reserved.
 */
@API(status = STABLE)
public interface DBAdviceTrait extends AdviceTrait {

    @API(status = INTERNAL)
    @ExceptionHandler
    default ResponseEntity<Problem> handleAuthentication(final DataIntegrityViolationException e,
                                                         final NativeWebRequest request) {
        Problem problem = Problem.builder()
                .withStatus(Status.INTERNAL_SERVER_ERROR)
                .withTitle("数据完整性违规异常")
                .build();
        return create(e, problem, request);
    }
}
