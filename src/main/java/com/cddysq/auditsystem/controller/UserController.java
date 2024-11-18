package com.cddysq.auditsystem.controller;

import com.cddysq.auditsystem.dao.User;
import com.cddysq.auditsystem.service.UserService;
import com.cddysq.auditsystem.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author <a href="mailto:tanghaotian@alu.uestc.edu.cn" rel="nofollow">cddysq</a>
 * @version 1.0.0
 * @date 2024/11/12 17:04
 * @since 1.0.0
 **/
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public String home(HttpSession session) {
        UserVo user = (UserVo) session.getAttribute("currentUser");
        if (user != null) {
            return "redirect:/upload";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        UserVo vo = userService.login(username, password);
        if (vo != null) {
            session.setAttribute("currentUser", vo);
            return "redirect:/files";
        } else {
            // 如果用户名或密码错误，设置错误信息
            model.addAttribute("errorMessage", "用户名或密码错误，请重试！");
            return "login";
        }
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {

        String message = userService.register(User.builder().username(username).password(password).build());
        if (message != null) {
            model.addAttribute("errorMessage", message);
            return "register";
        }

        // 注册成功后跳转到注册成功页面
        return "register-success";
    }

    /**
     * @return 跳转显示注册成功页面
     */
    @GetMapping("/register-success")
    public String showRegisterSuccessPage() {
        return "register-success";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 清除会话中的用户信息
        session.invalidate();
        // 重定向到登录页面
        return "redirect:/login";
    }
}
