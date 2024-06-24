package com.buoi1.webbanhang.controller;

import com.buoi1.webbanhang.model.Product;
import com.buoi1.webbanhang.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    /**
     * Chuyển đến trang danh sách sản phẩm khi truy cập vào trang chủ.
     *
     * @param model   Đối tượng Model để chuyển dữ liệu sang view.
     * @param keyword Từ khóa tìm kiếm (nếu có).
     * @return Tên view (trang) để hiển thị.
     */
    @GetMapping("/")
    public String home(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        List<Product> products;
        if (keyword != null && !keyword.isEmpty()) {
            products = productService.searchProductsByName(keyword);
        } else {
            products = productService.getAllProducts();
        }
        model.addAttribute("products", products);
        return "products/product-list";  // Đảm bảo rằng tên view (trang) này tồn tại và đúng.
    }

    /**
     * Kiểm tra xem người dùng đã đăng nhập và có quyền ADMIN hay USER không.
     *
     * @return true nếu người dùng đã đăng nhập và có quyền ADMIN hoặc USER, ngược lại false.
     */
    public boolean isLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !authentication.getAuthorities().isEmpty();
    }
}
