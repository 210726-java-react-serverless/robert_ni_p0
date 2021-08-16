package com.revature.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.datasource.models.AppUser;
import com.revature.services.UserService;
import com.revature.utils.exceptions.AuthenticationException;
import com.revature.web.dtos.Credentials;
import com.revature.web.dtos.ErrorResponse;
import com.revature.web.dtos.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthServlet extends HttpServlet {

    private final UserService userService;
    private final ObjectMapper mapper;

    public AuthServlet(UserService userService, ObjectMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");

        try {
            Credentials credential = mapper.readValue(req.getInputStream(), Credentials.class);
            AppUser authUser = userService.login(credential.getUsername(), credential.getPassword());
            Principal principal = new Principal(authUser);

            String payload = mapper.writeValueAsString(principal);
            respWriter.write(payload);

        } catch (AuthenticationException e) {
            resp.setStatus(401); // 401 - Unauthorized
            ErrorResponse errResp = new ErrorResponse(401, e.getMessage());
            respWriter.write(mapper.writeValueAsString(errResp));

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500); // 500 - Internal Server Error
        }
    }
}
