package com.parking.parkinglot;

import com.parking.parkinglot.common.CarDto;
import com.parking.parkinglot.common.UserDto;
import com.parking.parkinglot.ejb.CarsBean;
import com.parking.parkinglot.ejb.UsersBean;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "EditCar", value = "/EditCar")
public class EditCar extends HttpServlet {
    @Inject
    UsersBean usersBean;
    @Inject
    CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        List<UserDto> user=usersBean.findAllUsers();
        request.setAttribute("user", user);

        Long carId=Long.parseLong(request.getParameter("id"));
        CarDto car=carsBean.findAllCars(carId);
        request.setAttribute("car", car);

        request.getRequestDispatcher("WEB-INF/pages/editCar.jsp").forward(request,response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        String licensePlate=request.getParameter("licensePlate");
        String parkingSpot=request.getParameter("parkingSpot");
        Long userId=Long.parseLong(request.getParameter("ownerId"));
        Long carId=Long.parseLong(request.getParameter("carId"));

        carsBean.updateCar(carId, licensePlate,parkingSpot,userId);

        response.sendRedirect(request.getContextPath() + "/Cars");
    }
}
