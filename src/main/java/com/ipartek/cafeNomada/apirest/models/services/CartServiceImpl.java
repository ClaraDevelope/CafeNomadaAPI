package com.ipartek.cafeNomada.apirest.models.services;

import com.ipartek.cafeNomada.apirest.models.entities.Cart;
import com.ipartek.cafeNomada.apirest.models.entities.CartItem;
import com.ipartek.cafeNomada.apirest.models.entities.Customer;
import com.ipartek.cafeNomada.apirest.models.entities.Product;
import com.ipartek.cafeNomada.apirest.models.dao.CartItemRepository;
import com.ipartek.cafeNomada.apirest.models.dao.CartRepository;
import com.ipartek.cafeNomada.apirest.models.dao.ProductRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @PersistenceContext
    private EntityManager em;

    public CartServiceImpl(CartRepository cartRepository,
                           CartItemRepository cartItemRepository,
                           ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Cart getOrCreateCart(Long customerId) {
        return cartRepository.findWithItemsByCustomerId(customerId)
                .orElseGet(() -> {
                    Cart c = new Cart();
                    Customer ref = em.getReference(Customer.class, customerId); // no hace SELECT
                    c.setCustomer(ref);
                    return cartRepository.save(c);
                });
    }

    @Override
    public Cart getCartWithItems(Long customerId) {
        return cartRepository.findWithItemsByCustomerId(customerId)
                .orElseGet(() -> getOrCreateCart(customerId));
    }

    @Override
    public Cart addItem(Long customerId, Long productId, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("quantity debe ser > 0");

        Cart cart = getOrCreateCart(customerId);

        var existing = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);
        if (existing.isPresent()) {
            CartItem it = existing.get();
            it.setQuantity(it.getQuantity() + quantity);
            cartItemRepository.save(it);
            return cartRepository.findWithItemsByCustomerId(customerId).orElseThrow();
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + productId));

        CartItem item = new CartItem();
        item.setCart(cart);
        item.setProduct(product);
        item.setUnitPrice(product.getPrice() != null ? product.getPrice() : BigDecimal.ZERO);
        item.setQuantity(quantity);

        cart.getItems().add(item);
        cartItemRepository.save(item);

        return cartRepository.findWithItemsByCustomerId(customerId).orElseThrow();
    }

    @Override
    public Cart updateQuantity(Long customerId, Long productId, int quantity) {
        Cart cart = getOrCreateCart(customerId);
        var itemOpt = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);
        if (itemOpt.isEmpty()) throw new IllegalArgumentException("El producto no está en el carrito");

        if (quantity <= 0) {
            cartItemRepository.deleteByCartIdAndProductId(cart.getId(), productId);
        } else {
            CartItem item = itemOpt.get();
            item.setQuantity(quantity);
            cartItemRepository.save(item);
        }
        return cartRepository.findWithItemsByCustomerId(customerId).orElseThrow();
    }

    @Override
    public Cart removeItem(Long customerId, Long productId) {
        Cart cart = getOrCreateCart(customerId);
        cartItemRepository.deleteByCartIdAndProductId(cart.getId(), productId);
        return cartRepository.findWithItemsByCustomerId(customerId).orElseThrow();
    }

    @Override
    public void clearCart(Long customerId) {
        Cart cart = getOrCreateCart(customerId);
        cartItemRepository.deleteByCartId(cart.getId());
    }
}
