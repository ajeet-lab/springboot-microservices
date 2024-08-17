package com.ajeet.electronic.store.controllers;


import com.ajeet.electronic.store.dtos.CartDto;
import com.ajeet.electronic.store.helpers.AddToCartRequest;
import com.ajeet.electronic.store.helpers.ApiResponse;
import com.ajeet.electronic.store.helpers.AppConstents;
import com.ajeet.electronic.store.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @PreAuthorize("hasAnyRole('"+AppConstents.ROLE_NORMAL+"','"+AppConstents.ROLE_ADMIN+"')")
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable("userId") String userId, @RequestBody AddToCartRequest request){
        CartDto cartDto = this.cartService.addItemToCart(userId, request);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('"+AppConstents.ROLE_NORMAL+"','"+AppConstents.ROLE_ADMIN+"')")
    @DeleteMapping("/{userId}/items/{cartItemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable("userId") String userId, @PathVariable("cartItemId") int cartItemId){
        this.cartService.removeItemFromCart(userId, cartItemId);
        ApiResponse apiResponse = ApiResponse.builder().status(HttpStatus.OK).isSuccess(true).message(AppConstents.CART_ITEM_REMOVED).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('"+AppConstents.ROLE_NORMAL+"','"+AppConstents.ROLE_ADMIN+"')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> clearItemFromCart(@PathVariable("userId") String userId){
        this.cartService.clearItemFromCart(userId);
        ApiResponse apiResponse = ApiResponse.builder().status(HttpStatus.OK).isSuccess(true).message(AppConstents.CART_ITEM_CLEARED).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('"+AppConstents.ROLE_NORMAL+"','"+AppConstents.ROLE_ADMIN+"')")
    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCartByUser(@PathVariable("userId") String userId){
      CartDto cartDto = this.cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }
}
