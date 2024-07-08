Spring Boot uses the Bean Validation API (JSR 380) and Hibernate Validator as the default implementation to handle validation. Here are some commonly used validation annotations provided by the Bean Validation API:

## Common Validation Annotations

**@NotNull** - Ensures that the annotated field is not null.
```
@NotNull(message = "Field cannot be null")
private String field;
```

**@NotEmpty** - Ensures that the annotated collection, map, or string is not null and not empty.
```
@NotEmpty(message = "Field cannot be empty")
private List<String> list;
```

**@NotBlank** - Ensures that the annotated string is not null and the trimmed length is greater than 0.
```
@NotBlank(message = "Field cannot be blank")
private String field;
```

**@Size** - Validates that the annotated collection, map, array, or string has a size within the specified bounds.
```
@Size(min = 2, max = 30, message = "Field must be between 2 and 30 characters")
private String field;
```

**@Min** - Validates that the annotated element is a number whose value must be higher or equal to the specified minimum.
```
@Min(value = 10, message = "Field must be at least 10")
private int field;
```

**@Max** - Validates that the annotated element is a number whose value must be lower or equal to the specified maximum.
```
@Max(value = 100, message = "Field must be at most 100")
private int field;
```

**@Email** - Validates that the annotated string is a valid email address.
```
@Email(message = "Email should be valid")
private String email;
```

**@Pattern** - Validates that the annotated string matches the specified regular expression.
```
@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Field must be alphanumeric")
private String field;
```

**@Past** - Validates that the annotated date or calendar is in the past.
```
@Past(message = "Date must be in the past")
private LocalDate date;
```

**@Future** - Validates that the annotated date or calendar is in the future.
```
@Future(message = "Date must be in the future")
private LocalDate date;
```

**@PastOrPresent** - Validates that the annotated date or calendar is in the past or present.
```
@PastOrPresent(message = "Date must be in the past or present")
private LocalDate date;
```

**@FutureOrPresent** - Validates that the annotated date or calendar is in the future or present.
```
@FutureOrPresent(message = "Date must be in the future or present")
private LocalDate date;
```

**@DecimalMin** - Validates that the annotated element is a number whose value must be higher or equal to the specified minimum.
```
@DecimalMin(value = "0.0", inclusive = false, message = "Field must be greater than 0")
private BigDecimal field;
```

**@DecimalMax** - Validates that the annotated element is a number whose value must be lower or equal to the specified maximum.
```
@DecimalMax(value = "100.0", inclusive = false, message = "Field must be less than 100")
private BigDecimal field;
```

**@Digits** - Validates that the annotated element is a number with a specified number of integer and fraction digits.
```
@Digits(integer = 3, fraction = 2, message = "Field must have up to 3 integer digits and 2 fraction digits")
private BigDecimal field;
```

**@Positive** - Validates that the annotated element is a positive number.
```
@Positive(message = "Field must be positive")
private int field;
```

**@PositiveOrZero** - Validates that the annotated element is a positive number or zero.
```
@PositiveOrZero(message = "Field must be positive or zero")
private int field;
```

**@Negative** - Validates that the annotated element is a negative number.
```
@Negative(message = "Field must be negative")
private int field;
```

**@NegativeOrZero** - Validates that the annotated element is a negative number or zero.
```
@NegativeOrZero(message = "Field must be negative or zero")
private int field;
```

**@AssertTrue** - Validates that the annotated boolean element is true.
```
@AssertTrue(message = "Field must be true")
private boolean field;
```

**@AssertFalse** - Validates that the annotated boolean element is false.
```
@AssertFalse(message = "Field must be false")
private boolean field;
```


## Regular Expression For Email 
### Validation -1
```
^(?=.{1,256})(?=.{1,64}@.{1,255})[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$
```
**This regex can be broken down as follows:**

* **^** - Asserts the start of the string.
* **(?=.{1,256})** - Ensures the total length of the email address does not exceed 256 characters.
* **(?=.{1,64}@.{1,255})** - Ensures the local part (before the @) does not exceed 64 characters and the domain part (after the @) does not exceed 255 characters.
* **[A-Za-z0-9._%+-]+** - Matches one or more characters that are alphanumeric or any of the characters ._%+- in the local part.
* **@** - Matches the literal @ character.
* **[A-Za-z0-9.-]+** - Matches one or more characters that are alphanumeric or any of the characters .- in the domain part.
* **\.** - Matches the literal . character separating the domain labels.
* **[A-Za-z]{2,}** - Matches the top-level domain (TLD) which must be at least two alphabetic characters.
* **$** - Asserts the end of the string.

### Example of Matching Email Addresses or non-matching email
* **example@example.com**: Matches because it fits the pattern of a valid email address.

* **user.name+alias@sub.example.co.uk**: Matches because it fits the pattern.
* **Example@Example.com**: Matches because the regex is case-insensitive.
* **user@.com**: Does not match because the domain part must have at least one character before the dot.
* **a@b.c**: Matches because it's within the length constraints, though unusual.

### Validation -2
```
"^[a-z0-9][-a-z0-9.-]+@([-a-z0-9]+\.)+[a-z]{2,5}$"
```
* **^**: Asserts the start of the string.

* **[a-z0-9]**: Matches a single lowercase letter (a-z) or a digit (0-9). This ensures the email starts with an alphanumeric character.

* **[-a-z0-9.-]+**: Matches one or more characters that can be a lowercase letter (a-z), a digit (0-9), a hyphen (-), a dot (.), or a dash. This matches the rest of the local part of the email address.

* **@**: Matches the literal @ character, which separates the local part and the domain part of the email.

* **([-a-z0-9]+\\.)+**: Matches one or more groups of characters that are a combination of one or more lowercase letters (a-z), digits (0-9), or hyphens (-), followed by a dot (.). This matches the domain part of the email. The double backslash (\\.) is used to escape the dot character in the regex.

* **[a-z]{2,5}**: Matches between 2 and 5 lowercase letters (a-z). This typically matches the top-level domain (TLD), such as "com", "net", "org", "info", etc.

### Example of Matching Email Addresses or non-matching email
* **example@example.com**: Matches because it fits the pattern of a valid email address.

* **user.name@domain.co**: Matches because it also fits the pattern.
 
* **Example@Example.com**: Does not match because the regex is case-sensitive and expects lowercase letters only.
 
* **user@.com**: Does not match because the domain part must have at least one character before the dot.