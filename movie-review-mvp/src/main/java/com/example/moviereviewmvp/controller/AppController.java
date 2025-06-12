// src/main/java/com/example/moviereviewmvp/controller/AppController.java
package com.example.moviereviewmvp.controller;

import com.example.moviereviewmvp.dto.UserRegistrationDto;
import com.example.moviereviewmvp.service.UserService;
import jakarta.validation.Valid; //
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; //
import org.springframework.validation.BindingResult; //
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute; //
import org.springframework.web.bind.annotation.PostMapping; //
import org.springframework.web.servlet.mvc.support.RedirectAttributes; //
import jakarta.servlet.http.HttpServletRequest; // Thêm import này

@Controller
public class AppController {

    private final UserService userService; //

    @Autowired //
    public AppController(UserService userService) { //
        this.userService = userService; //
    }

    @GetMapping("/")
    public String homePage(HttpServletRequest request, Model model) {
        String requestURI = request.getRequestURI();
        if (request.getQueryString() != null) {
            requestURI += "?" + request.getQueryString();
        }
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("currentUrl", requestURI);
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request, Model model) {
        String requestURI = request.getRequestURI();
        if (request.getQueryString() != null) {
            requestURI += "?" + request.getQueryString();
        }
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("currentUrl", requestURI);
        return "login";
    }

    @GetMapping("/register") //
    public String showRegistrationForm(HttpServletRequest request, Model model) {
        String requestURI = request.getRequestURI();
        if (request.getQueryString() != null) {
            requestURI += "?" + request.getQueryString();
        }
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("currentUrl", requestURI);
        // Tạo một đối tượng DTO rỗng để Thymeleaf form có thể bind dữ liệu vào
        model.addAttribute("userDto", new UserRegistrationDto()); //
        return "register"; // Trả về templates/register.html
    }

    @PostMapping("/register") //
    public String processRegistration(@Valid @ModelAttribute("userDto") UserRegistrationDto userDto, //
                                      BindingResult bindingResult, //
                                      Model model, //
                                      RedirectAttributes redirectAttributes,
                                      HttpServletRequest request) { // Thêm HttpServletRequest vào đây
        String requestURI = request.getRequestURI();
        if (request.getQueryString() != null) {
            requestURI += "?" + request.getQueryString();
        }
        model.addAttribute("currentUri", request.getRequestURI());
        model.addAttribute("currentUrl", requestURI);
        // Kiểm tra lỗi validation từ DTO
        if (bindingResult.hasErrors()) {
            // model.addAttribute("userDto", userDto); // Không cần thiết vì @ModelAttribute đã làm
            return "register"; // Quay lại trang đăng ký nếu có lỗi
        }

        // Kiểm tra mật khẩu khớp nhau
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.userDto", "Mật khẩu xác nhận không khớp.");
            // model.addAttribute("userDto", userDto);
            return "register";
        }

        try {
            userService.registerNewUser(userDto); //
            redirectAttributes.addFlashAttribute("successMessage", "Đăng ký thành công! Vui lòng đăng nhập."); //
            return "redirect:/login"; // Chuyển hướng đến trang đăng nhập sau khi đăng ký thành công
        } catch (Exception e) {
            // model.addAttribute("userDto", userDto); // Giữ lại dữ liệu đã nhập
            // Thêm lỗi vào bindingResult để hiển thị trên form
            // Kiểm tra xem lỗi có phải từ username hay email đã tồn tại không
            if (e.getMessage().contains("Tên đăng nhập đã tồn tại")) {
                bindingResult.rejectValue("username", "error.userDto", e.getMessage());
            } else if (e.getMessage().contains("Email đã được sử dụng")) {
                bindingResult.rejectValue("email", "error.userDto", e.getMessage());
            } else {
                // Lỗi chung khác
                model.addAttribute("registrationError", "Lỗi đăng ký: " + e.getMessage());
            }
            return "register"; // Quay lại trang đăng ký nếu có lỗi từ service
        }
    }

//    @GetMapping("/admin/dashboard")
//    public String adminDashboard() {
//        return "admin/dashboard";
//    }
}