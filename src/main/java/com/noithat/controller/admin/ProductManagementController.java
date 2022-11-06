package com.noithat.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.noithat.entity.Category;
import com.noithat.entity.Product;
import com.noithat.entity.Subcategory;
import com.noithat.service.CategoryService;
import com.noithat.service.ProductService;
import com.noithat.service.SubcategoryService;

@Controller
public class ProductManagementController {
	@Autowired
	ProductService productService;
	@Autowired
	SubcategoryService subcategoryService;
	@Autowired
	CategoryService categoryService;
	
	RestTemplate restTemplate = new RestTemplate();

	@RequestMapping("/admin/product/list")
	public String getAll() {
		return "admin/product-list.html";
	}
	
	@RequestMapping("/admin/product/{id}")
	public String getEdit(Model model, @PathVariable("id") Integer id) {
		Product product = productService.findById(id);
		List<Subcategory> subcategories = subcategoryService.findByCategoryId(product.getCategory().getId());
		model.addAttribute("item",product);
		model.addAttribute("sub",subcategories);
		return "admin/product-add";
	}
	
	@RequestMapping("/admin/product/add")
	public String addProduct(Model model, @ModelAttribute("item") Product product) {
		product = new Product();
		product.setId(-999);
		model.addAttribute("item", product);
		return "admin/product-add.html";
	}
	
	@RequestMapping("/admin/product/submit")
	public String submitProduct(RedirectAttributes redirectAttributes, Model model,
			@ModelAttribute("item") Product product,
			@RequestParam("img1") Optional<MultipartFile> imageFile1,
			@RequestParam("img2") Optional<MultipartFile> imageFile2,
			@RequestParam("img3") Optional<MultipartFile> imageFile3,
			@RequestParam("img4") Optional<MultipartFile> imageFile4) {
		product.setId(-999);
		Category category = categoryService.findById(product.getCategory().getId());
		product.setCategory(category);
		Subcategory subcategory = subcategoryService.findById(product.getSubcategory().getId());
		product.setSubcategory(subcategory);
		
		List<Subcategory> subcategories = subcategoryService.findByCategoryId(product.getCategory().getId());
		redirectAttributes.addFlashAttribute("sub", subcategories);
		
		setImage(product, 1, imageFile1);
		
		setImage(product, 2, imageFile2);
		setImage(product, 3, imageFile3);
		setImage(product, 4, imageFile4);
		
		String url = "http://localhost:8080/api/products/";
		HttpEntity<Product> httpEntity = new HttpEntity<>(product);
		ResponseEntity<Product> productEntity = restTemplate.postForEntity(url, httpEntity, Product.class);
		product = productEntity.getBody();
		redirectAttributes.addFlashAttribute("message", "Thêm sản phẩm thành công!");
		redirectAttributes.addFlashAttribute("item", product);
		return "redirect:/product/" + product.getId();
	}
	
	@RequestMapping("/admin/product/update/{id}")
	public String updateProduct(RedirectAttributes redirectAttributes, Model model,
			@ModelAttribute("item") Product product, @PathVariable("id") Integer productId,
			@RequestParam("img1") Optional<MultipartFile> imageFile1,
			@RequestParam("img2") Optional<MultipartFile> imageFile2,
			@RequestParam("img3") Optional<MultipartFile> imageFile3,
			@RequestParam("img4") Optional<MultipartFile> imageFile4) {
		Product productImages = productService.findById(product.getId());
		product.setImage1(productImages.getImage1());
		product.setImage2(productImages.getImage2());
		product.setImage3(productImages.getImage3());
		product.setImage4(productImages.getImage4());
		Category category = categoryService.findById(product.getCategory().getId());
		product.setCategory(category);
		Subcategory subcategory = subcategoryService.findById(product.getSubcategory().getId());
		product.setSubcategory(subcategory);
		List<Subcategory> subcategories = subcategoryService.findByCategoryId(product.getCategory().getId());
		redirectAttributes.addFlashAttribute("sub", subcategories);
		setImage(product, 1, imageFile1);
		setImage(product, 2, imageFile2);
		setImage(product, 3, imageFile3);
		setImage(product, 4, imageFile4);
		String url = "http://localhost:8080/api/products/" + product.getId();
		HttpEntity<Product> httpEntity = new HttpEntity<Product>(product);
		restTemplate.put(url, httpEntity);
		redirectAttributes.addFlashAttribute("message", "Cập nhật sản phẩm thành công!");
		redirectAttributes.addFlashAttribute("item", product);
		return "redirect:/admin/product" + product.getId();
	}
	
	private void setImage(Product product, Integer imageNumber, Optional<MultipartFile> multipartFile) {
		String fileName = "default-product.png";
		if(!multipartFile.get().isEmpty()) {
			fileName = multipartFile.get().getOriginalFilename();
		}
		switch (imageNumber) {
		case 1:
			if (product.getImage1() == null || product.getImage1().equals("default-product.png")) {
				product.setImage1(fileName);
			}else {
				product.setImage1(product.getImage1());
			}
			break;
		case 2:
			if (product.getImage2() == null || product.getImage2().equals("default-product.png")) {
				product.setImage2(fileName);
			}else {
				product.setImage2(product.getImage2());
			}
		case 3:
			if (product.getImage3() == null || product.getImage3().equals("default-product.png")) {
				product.setImage3(fileName);
			}else {
				product.setImage3(product.getImage3());
			}
		case 4:
			if (product.getImage4() == null || product.getImage4().equals("default-product.png")) {
				product.setImage4(fileName);
			}else {
				product.setImage4(product.getImage4());
			}
		default:
			throw new IllegalArgumentException(imageNumber + "invalid");
		}
	}
}
