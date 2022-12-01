package com.noithat.controller.admin;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.noithat.entity.Account;
import com.noithat.entity.Category;
import com.noithat.service.AccountService;
import com.noithat.service.CategoryService;

@Controller
public class CategoryManagementController {
	@Autowired
	CategoryService categoryService;
	@Autowired
	AccountService accountService;
	RestTemplate restTemplate = new RestTemplate();
	
	@RequestMapping("/category")
		public String getCategory(Model model, @ModelAttribute("categoryForm") Category category,Authentication auth) {
			category = new Category();
			category.setId(null);
			category.setIcon("default-category.png");
			model.addAttribute("categoryForm",category);
			Account account=accountService.findByUsername(auth.getName());
			model.addAttribute("user",account);
			return "admin/category.html";
		}
	
	@RequestMapping("/category/{id}")
	public String editCategory(Model model, @PathVariable("id") String id,Authentication auth) {
		Category category = categoryService.findById(id);
		model.addAttribute("categoryForm",category);
		Account account=accountService.findByUsername(auth.getName());
		model.addAttribute("user",account);
		return "admin/category.html";
	}
	
	@RequestMapping("/category/submit")
	public String submitCategory(RedirectAttributes redirectAttributes, Model model,
			@Validated @ModelAttribute("categoryForm") Category category,Errors errors,
			@RequestParam("imageIcon") Optional<MultipartFile> imageIcon,Authentication auth) {
		if(errors.hasErrors()) {
			category.setIcon(null);
			model.addAttribute("errorMessage","Thêm danh mục thất bại!");
			Account account=accountService.findByUsername(auth.getName());
			model.addAttribute("user",account);
			return "admin/category";
		}
		
		String categoryId = category.getId();
		String categoryIdRemoveSpace = categoryId.replace(" ", "");
		String categoryRemoveSpecialCharater = categoryIdRemoveSpace.replaceAll("[^a-zA-Z0-9]", "");
		category.setId(categoryRemoveSpecialCharater);
		setImageIcon(category, 1, imageIcon);
		try {
			Category existCategory = categoryService.findById(categoryRemoveSpecialCharater);
			if (existCategory != null) {
				redirectAttributes.addFlashAttribute("message", "Thêm danh mục thất bại ! Mã danh mục đã tồn tại!");
				redirectAttributes.addFlashAttribute("categoryForm", category);
			}
		} catch (NoSuchElementException e) {
			String url = "http://localhost:8080/api/categories/";
			HttpEntity<Category> httpEntity = new HttpEntity<>(category);
			ResponseEntity<Category> categoryEntity = restTemplate.postForEntity(url, httpEntity, Category.class);
			category = categoryEntity.getBody();
			redirectAttributes.addFlashAttribute("message", "Thêm danh mục thành công!");
			redirectAttributes.addFlashAttribute("categoryForm", category);
		}
		
		return "redirect:/category";
	}
	
	@RequestMapping("/category/update/{id}")
	public String updateCategory(RedirectAttributes redirectAttributes, Model model,
			@Validated @ModelAttribute("categoryForm") Category category, Errors errors,
			@RequestParam("imageIcon") Optional<MultipartFile> imageIcon,Authentication auth) {
		if (errors.hasErrors()) {
			model.addAttribute("message", "Cập nhật danh mục thất bại!");
			Account account=accountService.findByUsername(auth.getName());
			model.addAttribute("user",account);
			return "admin/category";
		}
		setImageIcon(category, 1, imageIcon);
		String url = "http://localhost:8080/api/categories/" + category.getId();
		HttpEntity<Category> httpEntity = new HttpEntity<Category>(category);
		restTemplate.put(url, httpEntity);
		redirectAttributes.addFlashAttribute("message","Cập nhật danh mục thành công!");
		return "redirect:/category/" + category.getId();
	}
	
	@RequestMapping("/category/delete/{id}")
	public String deleteCategory(RedirectAttributes redirectAttributes, @PathVariable("id") String id) {
		String url = "http://localhost:8080/api/categories/" + id;
		restTemplate.delete(url);
		redirectAttributes.addFlashAttribute("message", "Xóa danh mục thành công!");
		return "redirect:/category";
	}
	
	private void setImageIcon(Category category, Integer iconNumber, Optional<MultipartFile> multipartFile) {
		String fileName = "default-category.png";
		if (!multipartFile.get().isEmpty()) {
			fileName = multipartFile.get().getOriginalFilename();
		}
		switch (iconNumber) {
		case 1:
			if(category.getIcon() == null || category.getIcon().equals("default-category.png")) {
				category.setIcon(fileName);
			} else {
				category.setIcon(category.getIcon());
			}
			break;

		default:
			throw new IllegalArgumentException(iconNumber + "invalid");
		}
	}
}

