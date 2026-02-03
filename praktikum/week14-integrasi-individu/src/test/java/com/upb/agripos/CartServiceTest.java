package com.upb.agripos;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CartServiceTest {
    private CartService cartService;
    private Product product1, product2;

    @BeforeEach
    public void setUp() {
        cartService = new CartService();
        product1 = new Product("P001", "Benih Padi", 25000, 100);
        product2 = new Product("P002", "Pupuk Urea", 35000, 50);
    }

    @Test
    public void testAddToCart_Success() {
        cartService.addToCart(product1, 5);
        assertEquals(1, cartService.getTotalItems());
        assertEquals(125000, cartService.calculateTotal(), 0.01);
    }

    @Test
    public void testAddToCart_MultipleProducts() {
        cartService.addToCart(product1, 3);
        cartService.addToCart(product2, 2);
        assertEquals(2, cartService.getTotalItems());
        assertEquals(145000, cartService.calculateTotal(), 0.01);
    }

    @Test
    public void testAddToCart_InvalidQuantity() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            cartService.addToCart(product1, 0);
        });
        assertEquals("Quantity harus lebih dari 0", ex.getMessage());
    }

    @Test
    public void testAddToCart_ExceedStock() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            cartService.addToCart(product1, 150);
        });
        assertEquals("Stok tidak mencukupi", ex.getMessage());
    }

    @Test
    public void testAddToCart_NullProduct() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            cartService.addToCart(null, 5);
        });
        assertEquals("Produk tidak boleh null", ex.getMessage());
    }

    @Test
    public void testClearCart() {
        cartService.addToCart(product1, 5);
        cartService.clearCart();
        assertEquals(0, cartService.getTotalItems());
        assertTrue(cartService.isCartEmpty());
    }
}
