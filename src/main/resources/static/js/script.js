document.addEventListener("DOMContentLoaded", function () {

    const searchInput = document.getElementById("productSearch");
    const productCards = document.querySelectorAll(".product-card");
    const categoryCards = document.querySelectorAll(".category-card");

    let selectedCategory = "";


    // ================= FILTER PRODUCTS =================

    function filterProducts() {

        const searchText = searchInput
            ? searchInput.value.toLowerCase().trim()
            : "";

        productCards.forEach(function (product) {

            const productName =
                product.querySelector("h3")
                    .textContent
                    .toLowerCase()
                    .trim();

            const productCategory =
                product.getAttribute("data-category")
                    .toLowerCase()
                    .trim();


            const matchesSearch =
                productName.includes(searchText);

            const matchesCategory =
                selectedCategory === "" ||
                productCategory === selectedCategory;


            if (matchesSearch && matchesCategory) {
                product.style.display = "";
            } else {
                product.style.display = "none";
            }

        });
    }


    // ================= SEARCH =================

    if (searchInput) {

        searchInput.addEventListener("input", function () {
            filterProducts();
        });

    }


    // ================= CATEGORY =================

    categoryCards.forEach(function (categoryCard) {

        categoryCard.addEventListener("click", function () {

            selectedCategory =
                categoryCard
                    .getAttribute("data-category")
                    .toLowerCase()
                    .trim();


            filterProducts();


            document
                .getElementById("products")
                .scrollIntoView({
                    behavior: "smooth"
                });

        });

    });

});
// ================= ADD TO CART =================

const cartForms = document.querySelectorAll(".product-actions form");

cartForms.forEach(function (form) {

    form.addEventListener("submit", function () {

        const button = form.querySelector(".cart-btn");

        if (button) {

            button.textContent = "Added ✓";

            button.disabled = true;

            setTimeout(function () {
                button.textContent = "Add to Cart";
                button.disabled = false;
            }, 2500);

        }

    });

});
// ================= CART QUANTITY BUTTON =================

const quantityButtons =
    document.querySelectorAll(".quantity-btn");

quantityButtons.forEach(function (button) {

    button.addEventListener("click", function () {

        button.disabled = true;

        setTimeout(function () {
            button.disabled = false;
        }, 500);

    });

});
// ================= LOGIN VALIDATION =================

const loginForm = document.getElementById("loginForm");

if (loginForm) {

    loginForm.addEventListener("submit", function (event) {

        const email =
            document.getElementById("email").value.trim();

        const password =
            document.getElementById("password").value.trim();


        if (email === "" || password === "") {

            event.preventDefault();

            alert("Please fill in all fields.");

            return;
        }


        if (!email.includes("@")) {

            event.preventDefault();

            alert("Please enter a valid email address.");

            return;
        }


        if (password.length < 5) {

            event.preventDefault();

            alert("Password must be at least 6 characters.");

            return;
        }

    });

}

// ================= REGISTER VALIDATION =================

const registerForm = document.getElementById("registerForm");

if (registerForm) {

    registerForm.addEventListener("submit", function (event) {

        const name =
            document.getElementById("name").value.trim();

        const email =
            document.getElementById("email").value.trim();

        const password =
            document.getElementById("password").value.trim();


        if (name === "" || email === "" || password === "") {

            event.preventDefault();

            alert("Please fill in all fields.");

            return;
        }


        if (name.length < 2) {

            event.preventDefault();

            alert("Please enter a valid name.");

            return;
        }


        if (!email.includes("@")) {

            event.preventDefault();

            alert("Please enter a valid email address.");

            return;
        }


        if (password.length < 5) {

            event.preventDefault();

            alert("Password must be at least 6 characters.");

            return;
        }

    });

}
const passwordInput = document.getElementById("password");
const togglePassword = document.getElementById("togglePassword");

if (passwordInput && togglePassword) {

    togglePassword.addEventListener("click", function () {

        if (passwordInput.type === "password") {

            passwordInput.type = "text";
            togglePassword.textContent = "Hide";

        } else {

            passwordInput.type = "password";
            togglePassword.textContent = "Show";

        }

    });

}
// ================= PRODUCT IMAGE PREVIEW =================

const imageName = document.getElementById("imageName");
const previewImageBtn = document.getElementById("previewImageBtn");
const imagePreview = document.getElementById("imagePreview");

if (imageName && previewImageBtn && imagePreview) {

    previewImageBtn.addEventListener("click", function () {

        const image = imageName.value.trim();

        if (image === "") {

            alert("Please enter an image name.");

            imagePreview.style.display = "none";

            return;
        }

        imagePreview.src = "/images/products/" + image;

        imagePreview.style.display = "block";

        imagePreview.onerror = function () {

            alert("Image not found!");

            imagePreview.style.display = "none";

        };

    });

}
// ================= CHECKOUT CONFIRMATION =================

const checkoutForm = document.getElementById("checkoutForm");

if (checkoutForm) {

    checkoutForm.addEventListener("submit", function (event) {

        const confirmOrder = confirm(
            "Are you sure you want to place this order?"
        );

        if (!confirmOrder) {
            event.preventDefault();
        }

    });

}