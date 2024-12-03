
        // Add event listener for form submission
        document.getElementById("registerForm").addEventListener("submit", function (event) {
            event.preventDefault();  // Prevent the default form submission

            const formData = new FormData(this);  // Collect form data

            // Submit form data using Fetch API
            fetch("/api/personal-information/review", {
                method: "POST",
                body: formData
            })
                .then(response => response.json())  // Parse response as JSON
                .then(data => {
                    if (data.success) {
                        alert("Data saved successfully!");
                        window.location.href = "/dashboard#register";  // Redirect after successful submission
                    } else {
                        alert("Error saving data. Please try again.");
                    }
                })
                .catch(error => {
                    console.error("Error:", error);  // Log any errors
                    alert("Error saving data. Please try again.");
                });
        });
