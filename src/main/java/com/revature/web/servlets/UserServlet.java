package com.revature.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.datasource.models.AppUser;
import com.revature.services.UserService;
import com.revature.utils.exceptions.InvalidRequestException;
import com.revature.utils.exceptions.ResourcePersistenceException;
import com.revature.web.dtos.ErrorResponse;
import com.revature.web.dtos.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserServlet extends HttpServlet {

    private final UserService userService;
    private final ObjectMapper mapper;

    public UserServlet(UserService userService, ObjectMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("<h1>/users works!</h1>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getAttribute("filtered"));
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");

        try {
            AppUser newUser = mapper.readValue(req.getInputStream(), AppUser.class);
            Principal principal = new Principal(userService.register(newUser));
            String payload = mapper.writeValueAsString(principal);
            respWriter.write(payload);
            resp.setStatus(201); // 201 - Created

        } catch (InvalidRequestException | MismatchedInputException e) {
            e.printStackTrace();
            resp.setStatus(400); // 400 - Bad Request
            ErrorResponse errResp = new ErrorResponse(400, e.getMessage());
            respWriter.write(mapper.writeValueAsString(errResp));

        } catch (ResourcePersistenceException e) {
            resp.setStatus(409); // 409 - Conflict
            ErrorResponse errResp = new ErrorResponse(409, e.getMessage());
            respWriter.write(mapper.writeValueAsString(errResp));

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500); // 500 - Internal Server Error
        }
    }
}
