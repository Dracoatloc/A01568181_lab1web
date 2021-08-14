/**
 * 
 */
package mx.tec.web.lab.controller;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import mx.tec.web.lab.manager.ProductManager;
import mx.tec.web.lab.vo.Product;

/**
 * @author mtorr
 *
 */
@RestController
public class ProductController {
	@Resource
	private ProductManager productManager;
	
	/**
	 * @return
	 */
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getProducts() {
		List<Product> products = productManager.getProducts();
		
		ResponseEntity<List<Product>> responseEntity = new ResponseEntity<>(products, HttpStatus.OK);
		return responseEntity;
	}
	
	/**
	 * @param id
	 * @return
	 */
	@GetMapping("/products/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable(value = "id") String id) {
		ResponseEntity<Product> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		Optional<Product> product = productManager.getProduct(id);
		
		if (product.isPresent()) {
			responseEntity = new ResponseEntity<>(product.get(), HttpStatus.OK);
		}
		
		return responseEntity;
	}
	
	/**
	 * @param newProduct
	 * @return
	 */
	@PostMapping("/products")
	public ResponseEntity<Product> addProduct(@RequestBody Product newProduct) {
		ResponseEntity<Product> responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		System.out.println("newProduct.id " + newProduct.getId());
		
		Optional<Product> product = productManager.addProduct(newProduct);
		
		if (product.isPresent()) {
			responseEntity = new ResponseEntity<>(product.get(), HttpStatus.CREATED);
		}
		
		return responseEntity;
	}

	/**
	 * @param changedProduct
	 * @return
	 */
	@PutMapping("/products/put")
	public ResponseEntity<Product> putProduct(@RequestBody Product changedProduct) {
		ResponseEntity<Product> responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		Optional<Product> product = productManager.getProduct(changedProduct.getId());
		
		if (product.isPresent()) {
			product = productManager.editProduct(changedProduct.getId(), changedProduct);
			responseEntity = new ResponseEntity<>(product.get(), HttpStatus.OK);
		}
		
		return responseEntity;
	}
	
	/**
	 * @param id
	 * @return
	 */
	@DeleteMapping("/products/delete/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable(value = "id") String id) {
		ResponseEntity<Product> responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		Optional<Product> product = productManager.getProduct(id);
		
		if (product.isPresent()) {
			productManager.deleteProduct(id);
			responseEntity = new ResponseEntity<>(product.get(), HttpStatus.OK);
		}
		
		return responseEntity;
	}
}
