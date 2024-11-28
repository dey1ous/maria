// Function to open the modal
function openModal(errorMessage) {
    document.getElementById("error-message").textContent = errorMessage;
    document.getElementById("error-modal").style.display = "flex";
    document.body.classList.add('modal-open');
}

// Function to close the modal
function closeModal() {
    document.getElementById("error-modal").style.display = "none";
    document.body.classList.remove('modal-open');
}

// Example: Open the modal for error when page loads (you can replace this with dynamic messages)
window.onload = function() {
    // Example error message
    openModal("An error occurred while saving your information.");
};
