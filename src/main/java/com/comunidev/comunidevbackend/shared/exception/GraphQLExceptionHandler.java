package com.comunidev.comunidevbackend.shared.exception;

import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Maps business exceptions to GraphQL errors so that resolvers can throw
 * instead of returning null or swallowing the cause.
 *
 * Must be a {@code @ControllerAdvice} (not a plain {@code @Component}) or
 * Spring GraphQL will not pick up the {@code @GraphQlExceptionHandler} methods.
 */
@ControllerAdvice
public class GraphQLExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GraphQLExceptionHandler.class);

    @GraphQlExceptionHandler
    public GraphQLError handleBusiness(BusinessException ex, DataFetchingEnvironment env) {
        log.warn("Business exception on {}: {}", fieldOf(env), ex.getMessage());
        return GraphQLError.newError()
                .message(ex.getMessage())
                .location(env.getField().getSourceLocation())
                .path(env.getExecutionStepInfo().getPath())
                .errorType(ErrorType.BAD_REQUEST)
                .extensions(extensions("BAD_REQUEST", ex.getMessage()))
                .build();
    }

    @GraphQlExceptionHandler
    public GraphQLError handleNotFound(ResourceNotFoundException ex, DataFetchingEnvironment env) {
        log.warn("Not found on {}: {}", fieldOf(env), ex.getMessage());
        return GraphQLError.newError()
                .message(ex.getMessage())
                .location(env.getField().getSourceLocation())
                .path(env.getExecutionStepInfo().getPath())
                .errorType(ErrorType.NOT_FOUND)
                .extensions(extensions("NOT_FOUND", ex.getMessage()))
                .build();
    }

    @GraphQlExceptionHandler
    public GraphQLError handleUnexpected(Exception ex, DataFetchingEnvironment env) {
        // Full stack trace only in the server log, never in the response.
        log.error("Unexpected error on {}", fieldOf(env), ex);
        String publicMessage = "Error interno del servidor";
        return GraphQLError.newError()
                .message(publicMessage)
                .location(env.getField().getSourceLocation())
                .path(env.getExecutionStepInfo().getPath())
                .errorType(ErrorType.INTERNAL_ERROR)
                .extensions(extensions("INTERNAL_ERROR", publicMessage))
                .build();
    }

    private Map<String, Object> extensions(String code, String message) {
        Map<String, Object> extensions = new LinkedHashMap<>();
        extensions.put("code", code);
        extensions.put("message", message);
        return extensions;
    }

    private String fieldOf(DataFetchingEnvironment env) {
        return env.getField() != null ? env.getField().getName() : "unknown";
    }
}
