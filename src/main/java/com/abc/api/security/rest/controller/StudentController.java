package com.abc.api.security.rest.controller;

import com.abc.api.security.exception.StudentException;
import com.abc.api.security.rest.response.StudentAppUserResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletResponse;

public interface StudentController {

    @ApiOperation(value = "find student by id")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_ACCEPTED, message = "Success", response = StudentAppUserResponse.class),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request", response = StudentException.class)
    })
    StudentAppUserResponse findById(@PathVariable String id);

    @ApiOperation(value = "find student by username")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_ACCEPTED, message = "Success", response = StudentAppUserResponse.class),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request", response = StudentException.class)
    })
    StudentAppUserResponse findByUsername(@PathVariable String username);

    @ApiOperation(value = "update student password by username")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_ACCEPTED, message = "Success", response = StudentAppUserResponse.class),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request", response = StudentException.class)
    })
    StudentAppUserResponse updateStudentAppUserPasswordByUsername(@PathVariable String username, @PathVariable String password);
}
