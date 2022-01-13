package com.abc.api.security.rest.controller;

import com.abc.api.security.entity.StudentAppUser;
import com.abc.api.security.exception.StudentException;
import com.abc.api.security.rest.response.StudentAppUserResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface StudentManagementController {

    @ApiOperation(value = "list all students")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_ACCEPTED, message = "Success", response = List.class),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request", response = StudentException.class)
    })
    List<StudentAppUserResponse> findAllStudents(@PathVariable String role);

    @ApiOperation(value = "find student by username")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_ACCEPTED, message = "Success", response = StudentAppUserResponse.class),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request", response = StudentException.class)
    })
    StudentAppUserResponse findByUsername(@PathVariable String username);

    @ApiOperation(value = "register a student")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_ACCEPTED, message = "Success", response = String.class),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request", response = StudentException.class)
    })
    String registerStudent(@RequestBody StudentAppUser studentAppUser);

    @ApiOperation(value = "update a student")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_ACCEPTED, message = "Success", response = StudentAppUserResponse.class),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request", response = StudentException.class)
    })
    StudentAppUserResponse updateStudent(@PathVariable String username, @PathVariable String password);

    @ApiOperation(value = "delete a student")
    @ApiResponses(value = {
            @ApiResponse(code = HttpServletResponse.SC_ACCEPTED, message = "Success"),
            @ApiResponse(code = HttpServletResponse.SC_BAD_REQUEST, message = "Bad request", response = StudentException.class)
    })
    void deleteStudent(@PathVariable String username);
}
