package com.ajeet.electronic.store.helpers;

public class AppConstents {

    // START CONFIGURE FOR ROLES AND ROUTES IN SPRING SECURITY
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_NORMAL = "NORMAL";
    public static final String USER_ROUTE = "/api/v1/users/**";
    public static final String PRODUCT_ROUTE = "/api/v1/products/**";
    public static final String CATEGORY_ROUTE = "/api/v1/category/**";
    // END CONFIGURE FOR ROLES AND ROUTES IN SPRING SECURITY


    // START CONFIGURE MESSAGES FOR NOT FOUND RESOURCES
    public static final String USER_NOT_FOUND_BY_ID = "User not found with given id : ";
    public static final String USER_NOT_FOUND_BY_EMAIL_ID = "User not found with given email id : ";
    public static final String PRODUCT_NOT_FOUND_BY_ID = "Product not found with given id : ";
    public static final String CART_NOT_FOUND_BY_USER = "Cart of given user not found !! ";
    public static final String CATEGORY_NOT_FOUND_BY_ID = "Category not found with given category id : ";
    public static final String CART_NOT_FOUND_BY_ID = "Cart is not found with given id : ";
    public static final String ORDER_NOT_FOUND_BY_ID = "Order is not found with given id : ";
    public static final String USER_DELETED_BY_ID = "User deleted successfully with given user id : ";
    public static final String PRODUCT_DELETED_BY_ID ="Product deleted with given id :";
    public static final String CATEGORY_DELETED_BY_ID ="Category delete successfully with given categoryId ";
    public static final String CART_ITEM_REMOVED = "Cart Item is removed successfully !!";
    public static final String CART_ITEM_CLEARED = "Cart Item is cleared successfully !!";
    public static final String FILE_UPLOADED = "File uploaded successfully with Image name : ";
    // END CONFIGURE MESSAGES FOR NOT FOUND RESOURCES


    // START EXCEPTIONS MESSAGES
    public static final String INVALID_USERNAME_OR_PASSWORD = "Invalid username or password !!";

    // START SWAGGER CONFIG
    public static final String [] PUBLIC_URLS = {"/swagger-ui/**", "/webjars/**", "/swagger-resources"};


     AppConstents(){
        super();
    }
}
